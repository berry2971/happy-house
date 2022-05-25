package com.ssafy.happyhouse.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressUtilTest {

    @Test
    void assembleAddrPartsToLegacyAddr() {
        String a = AddressUtil.assembleAddrPartsToLegacyAddr("서울특별시", "성동구", "금호동2가", "1", "0");
    }

    @Test
    void changeComplexAddrToLegacyAddr() {
    }

}