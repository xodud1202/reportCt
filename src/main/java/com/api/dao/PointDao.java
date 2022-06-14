package com.api.dao;

import com.api.domain.Point;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
}