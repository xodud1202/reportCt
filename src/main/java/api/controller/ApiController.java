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

    /*
    1. 화면에 helloworld가 출력됩니다.
    */
    @GetMapping("/select/test/table")
    @ResponseBody
    public List<Point> testTable() {
        return pointService.getPointList();
    }

    /**
     * 필수 파라미터 (custNo : 고객번호(long), givePntAmt : 포인트 지급 금액(int))
     * @param param
     * @return
     */
    @PostMapping("/save/point")
    @ResponseBody
    public Map<Object, String> saveCustPointInfo(@RequestBody Point param) {
        return pointService.saveCustPointInfo(param);
    }


}
