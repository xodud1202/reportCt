package api.domain;

import lombok.Data;

@Data
public class Point {
	private Long   custPointIdx;
	private Long   custNo;
	private int    givePntAmt;
	private int    usePntAmt;
	private int    leftPntAmt;
	private int    pntAmt;
	private int    ordNo;
	private String delYn;
	private String gbn;
	private String hstDesc;
	private String extDt;
	private String regDt;
	private String updDt;
}