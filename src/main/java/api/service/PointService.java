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

	// 고객별 포인트 합계 금액 조회
	public Map<String, Object> getCustPointSumInfo(Point param) {
		// 유효 고객 번호 여부 조회
		Map<String, Object> result = this.isAvailableCustNo(param);
		if("OK".equals(result.get("state"))) {
			// 고객별 금액 합계 정보 조회
			result.put("data", pointDao.getCustPointSumInfo(param));
		}

		return result;
	}

	// 고객별 포인트 적립/사용 내역 조회 (페이징)
	public Map<String, Object> getCustPointHstList(Point param) {
		// 유효 고객 번호 여부 조회
		Map<String, Object> result = this.isAvailableCustNo(param);
		if("OK".equals(result.get("state"))) {
			// 페이지번호가 1보다 작을 경우 1페이지로 조회되도록
			if(param.getPageNo() < 1) param.setPageNo(1);

			// 고객 포인트 적립/사용 내역 조회
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

	// 고객 포인트 사용 처리
	@Transactional
	public Map<String, Object> useCustPointInfo(Point param){
		Map<String, Object> result = new HashMap<>();
		result.put("state", "FAIL");

		// 파라미터 유효성 체크
		if (param == null || param.getCustNo() == null) {
			result.put("msg", "고객 데이터를 전달 받지 못했습니다.");
			return result;
		} else if(param.getCustNo() < 1) {
			result.put("msg", "유효한 고객 번호를 입력해주세요.");
			return result;
		} else if (param.getUsePntAmt() == 0) {
			result.put("msg", "사용 포인트 금액을 입력해주세요.");
			return result;
		} else if (param.getUsePntAmt() < 1) {
			result.put("msg", "사용 포인트 금액을 양수로 입력해주세요.");
			return result;
		} else if (param.getOrdNo() < 1) {
			result.put("msg", "포인트 사용 주문번호를 입력해주세요.");
			return result;
		}

		// 포인트 합계 금액 조회
		Map<String, Object> pntSumInfo = (Map<String, Object>) this.getCustPointSumInfo(param).get("data");
		Long totLeftPntAmt = 0L;
		if(pntSumInfo != null && pntSumInfo.containsKey("TOT_LEFT_PNT")) {
			totLeftPntAmt = (Long) pntSumInfo.get("TOT_LEFT_PNT");		// 포인트 사용 가능 금액 합계
			if(totLeftPntAmt == null) totLeftPntAmt = 0L;
		}

		if(totLeftPntAmt < param.getUsePntAmt()) {
			result.put("msg", "잔여 포인트 금액이 부족합니다.");
			return result;
		}

		// 고객 사용가능 포인트 금액 조회 (적립순)
		List<Point> pointList = this.getUsableCustPointList(param);
		int leftUsePntAmt = param.getUsePntAmt();		// 남은 포인트 사용 금액

		// 사용 포인트 금액 전부 처리 될때까지 잔여 금액에서 제거
		for(Point left : pointList) {
			if (left.getLeftPntAmt() > 0) {
				left.setGbn("USE");
				left.setOrdNo(param.getOrdNo());
				left.setHstDesc("주문 포인트 사용 (주문번호 : " + param.getOrdNo() + ")");
				if(left.getLeftPntAmt() >= leftUsePntAmt) {
					// 포인트 남은 금액이 사용 금액보다 크면 해당 금액만큼 차감 후 종료
					left.setUsePntAmt(left.getUsePntAmt() + leftUsePntAmt);
					left.setLeftPntAmt(left.getLeftPntAmt() - leftUsePntAmt);
					this.updateCustPointInfo(left);

					left.setPntAmt(-leftUsePntAmt);
					leftUsePntAmt = 0;
					this.insertCustPointHst(left);
					break;
				} else {
					// 포인트 남은 금액이 사용 금액보다 작을 경우 해당 포인트 차감 후 loop 재계산
					int thisLeftAmt = left.getLeftPntAmt();
					leftUsePntAmt = leftUsePntAmt - left.getLeftPntAmt();
					left.setUsePntAmt(left.getUsePntAmt() + left.getLeftPntAmt());
					left.setLeftPntAmt(0);
					this.updateCustPointInfo(left);

					left.setPntAmt(-thisLeftAmt);
					this.insertCustPointHst(left);
				}
			}
		}

		if(leftUsePntAmt > 0) {
			throw new IllegalStateException("포인트 계산이 잘못되었습니다. 다시 시도해주세요.");
		} else {
			result.put("state", "OK");
			result.put("msg", param.getUsePntAmt() + " 포인트 사용했습니다.");
		}

		return result;
	}

	@Transactional
	public int updateCustPointInfo(Point param) {
		return pointDao.updateCustPointInfo(param);
	}

	// 포인트 사용 가능 금액 리스트 조회
	public List<Point> getUsableCustPointList(Point param) {
		return pointDao.getUsableCustPointList(param);
	}

	// 포인트 적립
	@Transactional
	public Map<String, Object> saveCustPointInfo(Point param) {
		Map<String, Object> result = new HashMap<>();
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

	// 고객 포인트 사용 취소 처리
	@Transactional
	public Map<String, Object> cancelUseCustPoint(Point param) {
		Map<String, Object> result = new HashMap<>();
		if(param.getOrdNo() < 1) {
			result.put("state", "FAIL");
			result.put("msg", "사용 취소할 주문번호를 정확히 입력해주세요.");
			return result;
		}

		// 고객 사용 포인트 조회
		int totUseCancelPoint = 0;
		List<Point> usePointList = pointDao.getCustPointHstListByOrdNo(param);
		if(usePointList == null || usePointList.size() < 1) {
			result.put("state", "FAIL");
			result.put("msg", "해당 주문번호에 사용한 포인트가 존재하지 않습니다.");
			return result;
		}

		for(Point usePoint : usePointList) {
			int usePnt = Math.abs(usePoint.getPntAmt());				// 사용 포인트 절대값(양수)로 변경
			totUseCancelPoint += usePnt;								// 사용 취소 포인트 총합

			usePoint.setUsePntAmt(usePoint.getUsePntAmt() - usePnt);	// 사용 금액 차감
			usePoint.setLeftPntAmt(usePoint.getLeftPntAmt() + usePnt);	// 남은 금액 사용 금액만큼 증가

			// 고객 포인트 사용 취소 처리
			this.updateCustPointInfo(usePoint);
			this.deleteCustPointHst(usePoint);
		}

		result.put("state", "OK");
		result.put("msg", totUseCancelPoint + " 포인트 사용 취소했습니다.");
		return result;
	}

	// 포인트 사용 내역 제거
	@Transactional
	public int deleteCustPointHst(Point param) {
		return pointDao.deleteCustPointHst(param);
	}
}
