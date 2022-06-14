package api.service;

import api.dao.PointDao;
import api.domain.Point;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PointService {

	@Autowired
	private PointDao pointDao;

	public List<Point> getPointList() {
		return pointDao.getPointList();
	}

	// 고객별 포인트 합계 금액 조회
	public Map<String, Object> getCustPointSumInfo(Point param) {
		Map<String, Object> result = this.isAvailableCustNo(param);
		if("OK".equals(result.get("state"))) {
			result.put("data", pointDao.getCustPointSumInfo(param));
		}

		return result;
	}

	// 고객별 포인트 적립/사용 내역 조회 (페이징)
	public Map<String, Object> getCustPointHstList(Point param) {
		Map<String, Object> result = this.isAvailableCustNo(param);
		if("OK".equals(result.get("state"))) {
			if(param.getPageNo() < 1) param.setPageNo(1);
			result.put("dataList", pointDao.getCustPointHstList(param));
		}

		return result;
	}

	// 고객 정보 유효성 조회
	Map<String, Object> isAvailableCustNo(Point param) {
		Map<String, Object> result = new HashMap<>();
		if(param.getCustNo() == null || param.getCustNo() < 1) {
			result.put("state", "FAIL");
			result.put("msg", "유효한 고객 번호가 아닙니다. 다시 확인해주세요.");
		} else {
			result.put("state", "OK");
		}

		return result;
	}

	// 포인트 적립
	@Transactional
	public Map<Object, String> saveCustPointInfo(Point param) {
		Map<Object, String> result = new HashMap<>();
		result.put("state", "FAIL");

		if (param == null || param.getCustNo() == null) {
			result.put("msg", "고객 데이터를 전달 받지 못했습니다.");
			return result;
		} else if(param.getCustNo() < 1) {
			result.put("msg", "유효한 고객 번호를 입력해주세요.");
			return result;
		} else if (param.getGivePntAmt() == 0) {
			result.put("msg", "지급 포인트 금액을 입력해주세요.");
			return result;
		} else if (param.getGivePntAmt() < 1) {
			result.put("msg", "포인트 지급은 마이너스처리 할 수 없습니다.");
			return result;
		}

		// 고객 포인트 지급
		param.setLeftPntAmt(param.getGivePntAmt());
		if(this.insertCustPoint(param) > 0) {
			param.setPntAmt(param.getGivePntAmt());
			param.setHstDesc("API를 통한 포인트 적립");
			param.setGbn("SAVE");

			if(this.insertCustPointHst(param) > 0) {
				result.put("state", "OK");
				result.put("msg"  , param.getGivePntAmt() + " 포인트 적립 완료되었습니다.");
			}
		}

		if(!"OK".equals(result.get("state"))) {
			throw new IllegalStateException("데이터 등록에 실패하였습니다.");
		}

		return result;
	}

	// 고객 포인트 마스터 테이블 생성
	@Transactional
	public int insertCustPoint(Point param) {
		return pointDao.insertCustPoint(param);
	}

	// 고객 포인트 적립/사용 이력 테이블 생성
	@Transactional
	public int insertCustPointHst(Point param) {
		return pointDao.insertCustPointHst(param);
	}
}
