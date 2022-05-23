package com.ssafy.happyhouse.controller;

import com.ssafy.happyhouse.mapper.DistrictMapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("districts")
public class DistrictController {

    private final DistrictMapper districtMapper;

    @Autowired
    public DistrictController(DistrictMapper districtMapper) {
        this.districtMapper = districtMapper;
    }

    @ApiOperation(value = "모든 거래내역 조회", notes = "모든 거래내역 리스트를 반환")
    @GetMapping("")
    public List<String> getDistrictsWithPreviousLevels(
            @RequestParam String addr_lv1,
            @RequestParam(required = false, defaultValue = "") String addr_lv2
    ) throws Exception {
        if (addr_lv2.equals("")) {
            return districtMapper.findLv2(addr_lv1);
        } else {
            return districtMapper.findLv3(addr_lv1, addr_lv2);
        }
    }

}
