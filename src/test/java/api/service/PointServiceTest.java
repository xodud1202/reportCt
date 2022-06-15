package api.service;

import api.domain.Point;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PointServiceTest {
	PointService service = new PointService();

	@Test
	public void isAvailableCustNo_Success_StateOk() {
		// given
		Point param = new Point();
		param.setCustNo(1L);

		// when
		String result = service.isAvailableCustNo(param);

		// then
		// Assert.assertEquals("OK", result);
		Assertions.assertThat("OK").isEqualTo(result);
	}

	@Test
	public void isAvailableCustNo_CustNoUnderZero_ReturnErrorMsg() {
		// given
		Point param = new Point();
		param.setCustNo(-1L);

		// when
		String result = service.isAvailableCustNo(param);

		// then
		// Assert.assertEquals("OK", result);
		Assertions.assertThat("OK").isNotEqualTo(result);
	}

	@Test
	public void isAvailableCustNo_CustNoNull_ReturnErrorMsg() {
		// given
		Point param = new Point();

		// when
		String result = service.isAvailableCustNo(param);

		// then
		// Assert.assertEquals("OK", result);
		Assertions.assertThat("OK").isNotEqualTo(result);
	}

	@Test
	public void isAvailableCustNo_ParameterNull_ReturnErrorMsg() {
		// given

		// when
		String result = service.isAvailableCustNo(null);

		// then
		// Assert.assertEquals("OK", result);
		Assertions.assertThat("OK").isNotEqualTo(result);
	}

	@Test
	public void useCustPointParamValidation_Success_StateOk() {
		// given
		Point param = new Point();
		param.setCustNo(1L);
		param.setUsePntAmt(1000);
		param.setOrdNo(1);

		// when
		String result = service.useCustPointParamValidation(param);

		// then
		// Assert.assertEquals("OK", result);
		Assertions.assertThat("OK").isEqualTo(result);
	}

	@Test
	public void useCustPointParamValidation_CustNoUnderZero_ReturnErrorMsg() {
		// given
		Point param = new Point();
		param.setCustNo(-2L);
		param.setUsePntAmt(1000);
		param.setOrdNo(1);

		// when
		String result = service.useCustPointParamValidation(param);

		// then
		// Assert.assertEquals("OK", result);
		Assertions.assertThat("OK").isNotEqualTo(result);
	}

	@Test
	public void useCustPointParamValidation_UsePntAmtUnderZero_ReturnErrorMsg() {
		// given
		Point param = new Point();
		param.setCustNo(2L);
		param.setUsePntAmt(-1000);
		param.setOrdNo(1);

		// when
		String result = service.useCustPointParamValidation(param);

		// then
		// Assert.assertEquals("OK", result);
		Assertions.assertThat("OK").isNotEqualTo(result);
	}

	@Test
	public void useCustPointParamValidation_OrdNoUnderZero_ReturnErrorMsg() {
		// given
		Point param = new Point();
		param.setCustNo(2L);
		param.setUsePntAmt(1000);
		param.setOrdNo(-2);

		// when
		String result = service.useCustPointParamValidation(param);

		// then
		// Assert.assertEquals("OK", result);
		Assertions.assertThat("OK").isNotEqualTo(result);
	}

	@Test
	public void saveCustPointParamValidation_Success_StateOk() {
		// given
		Point param = new Point();
		param.setCustNo(1L);
		param.setGivePntAmt(2500);

		// when
		String result = service.saveCustPointParamValidation(param);

		// then
		// Assert.assertEquals("OK", result);
		Assertions.assertThat("OK").isEqualTo(result);
	}

	@Test
	public void saveCustPointParamValidation_CustNoUnderZero_ReturnErrorMsg() {
		// given
		Point param = new Point();
		param.setCustNo(0L);
		param.setGivePntAmt(2500);

		// when
		String result = service.saveCustPointParamValidation(param);

		// then
		// Assert.assertEquals("OK", result);
		Assertions.assertThat("OK").isNotEqualTo(result);
	}

	@Test
	public void saveCustPointParamValidation_GivePntAmtUnderZero_ReturnErrorMsg() {
		// given
		Point param = new Point();
		param.setCustNo(1L);
		param.setGivePntAmt(-2500);

		// when
		String result = service.saveCustPointParamValidation(param);

		// then
		// Assert.assertEquals("OK", result);
		Assertions.assertThat("OK").isNotEqualTo(result);
	}
}
