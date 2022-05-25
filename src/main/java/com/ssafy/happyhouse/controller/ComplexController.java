package com.ssafy.happyhouse.controller;

import com.ssafy.happyhouse.domain.entity.Complex;
import com.ssafy.happyhouse.mapper.ComplexMapper;
import com.ssafy.happyhouse.util.AddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("complex")
public class ComplexController {

    private final ComplexMapper complexMapper;

    @Autowired
    public ComplexController(ComplexMapper complexMapper) {
        this.complexMapper = complexMapper;
    }

    @GetMapping("where")
    public Complex where(
        @RequestParam(value = "addr_lv1") String state,
        @RequestParam(value = "addr_lv2") String city,
        @RequestParam(value = "addr_lv3") String town,
        @RequestParam(value = "bunji_main") String bunji_main,
        @RequestParam(value = "bunji_sub") String bunji_sub
    ) {
        List<Complex> complexes = complexMapper.findByAddr(state, city, town);
        String targetLegacyAddr = AddressUtil.assembleAddrPartsToLegacyAddr(state, city, town, bunji_main, bunji_sub);
        log.info("TARGET LEGACY ADDR: {}", targetLegacyAddr);
        for (Complex complex : complexes) {
            String findLegacyAddr = AddressUtil.changeComplexAddrToLegacyAddr(complex.getDanji_name(), complex.getAddr_legacy());
            if (targetLegacyAddr.equals(findLegacyAddr)) {
                return complex;
            }
        }
        return null;
    }

}



/*
### sample data 1
# input
addr: "경기 수원시 팔달구 고등동 156-117"
addrLv1: "경기도"
addrLv2: "수원팔달구"
addrLv3: "고등동"
builtYear: undefined
bunjiMain: "156"
bunjiSub: "117"
deals: Array(0)
marker: v
maxPrice: 0
minPrice: 100000000000
name: "수원역푸르지오자이아파트"
numDeal: 0
roadAddr: "경기 수원시 팔달구 고등로 15"
x: "126.99903033147054"
y: "37.27498633049435"
# output
addr_lv1: 경기도
addr_lv2: 수원팔달구
addr_lv3: 세류동


sample data 2
addr: "서울 성동구 금호동2가 1"
addrLv1: "서울특별시"
addrLv2: "성동구"
addrLv3: "금호동2가"
builtYear: undefined
bunjiMain: "1"
bunjiSub: "0"
deals: Array(5)
marker: v
maxPrice: 119000
minPrice: 89500
name: "신금호파크자이아파트"
numDeal: 5
roadAddr: "서울 성동구 금호로 173"
x: "127.01899726814243"
y: "37.55540086629908"
*/