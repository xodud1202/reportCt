package api.dao;

import api.domain.Point;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PointDao {
	List<Point> getPointList();

	/**
	 * 고객 포인트 테이블 생성
	 */
	int insertCustPoint(Point param);

	/**
	 * 고객 포인트 적립/사용 이력 테이블 생성
	 */
	int insertCustPointHst(Point param);

	/**
	 * 고객별 포인트 합계 금액 조회
	 */
	Map<String, Object> getCustPointSumInfo(Point param);

	/**
	 * 고객별 포인트 적립/사용 내역 조회 (페이징)
	 */
	List<Map<String, Object>> getCustPointHstList(Point param);
}