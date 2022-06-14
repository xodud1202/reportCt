package api.controller;

import api.domain.Point;
import api.service.PointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ApiController {
    @Autowired
    private PointService pointService;

    /**
     * 고객 포인트 지급 > 필수 파라미터 (  custNo     : 고객번호(long)
     *                              , givePntAmt : 포인트 지급 금액(int) )
     */
    @PostMapping("/save/point")
    @ResponseBody
    public Map<String, Object> saveCustPointInfo(@RequestBody Point param) {
        return pointService.saveCustPointInfo(param);
    }

    /**
     * 고객 사용 포인트 차감 > 필수 파라미터 (   custNo    : 고객번호(long)
     *                                   , usePntAmt : 포인트 사용 금액(int)
     *                                   , ordNo     : 포인트 사용 주문번호 )
     */
    @PostMapping("/use/point")
    @ResponseBody
    public Map<String, Object> useCustPointInfo(@RequestBody Point param) {
        return pointService.useCustPointInfo(param);
    }

    /**
     * 고객별 포인트 합계 금액 조회 > 필수 파라미터 ( custNo : 고객번호(long) )
     */
    @GetMapping("/sum/cust/point/info")
    @ResponseBody
    public Map<String, Object> getCustPointSumInfo(Point param) {
        return pointService.getCustPointSumInfo(param);
    }

    /**
     * 고객별 포인트 적립/사용 내역 조회 (페이징) > 필수 파라미터 ( custNo : 고객번호(long) )
     */
    @GetMapping("/cust/point/hst/list")
    @ResponseBody
    public Map<String, Object> getCustPointHstList(Point param) {
        return pointService.getCustPointHstList(param);
    }
}
