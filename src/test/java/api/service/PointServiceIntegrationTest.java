package api.service;

import api.domain.Point;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class PointServiceIntegrationTest {
	@Autowired
	private PointService pointService;

	// 고객별 포인트 합계 조회
	@Test
	public void getCustPointSumInfo() {
		// given
		Point param = new Point();
		param.setCustNo(1L);

		// when
		Map<String, Object> result = pointService.getCustPointSumInfo(param);

		// then
		Assertions.assertThat("OK").isEqualTo(result.get("state"));
	}

	// 고객별 포인트 적립/사용 리스트
	@Test
	public void getCustPointHstList() {
		// given
		Point param = new Point();
		param.setCustNo(1L);
		param.setPageNo(1);

		// when
		Map<String, Object> result = pointService.getCustPointHstList(param);

		// then
		Assertions.assertThat("OK").isEqualTo(result.get("state"));
	}

	// 포인트 적립 테스트
	@Test
	public void saveCustPointInfo() {
		// given
		Point param = new Point();
		param.setCustNo(1L);
		param.setGivePntAmt(100);

		// when
		Map<String, Object> result = pointService.saveCustPointInfo(param);

		// then
		Assertions.assertThat("OK").isEqualTo(result.get("state"));
	}

	// 포인트 사용 테스트
	@Test
	public void useCustPointInfo() {
		// given
		Point param = new Point();
		param.setCustNo(891202L);
		param.setUsePntAmt(3000);
		param.setOrdNo(8912021);

		// when
		Map<String, Object> result = pointService.useCustPointInfo(param);

		// then
		Assertions.assertThat("OK").isEqualTo(result.get("state"));
	}


	// 포인트 사용취소 테스트
	@Test
	public void cancelUseCustPoint() {
		// given
		Point param = new Point();
		param.setOrdNo(891202);

		// when
		Map<String, Object> result = pointService.cancelUseCustPoint(param);

		// then
		Assertions.assertThat("OK").isEqualTo(result.get("state"));
	}
}
