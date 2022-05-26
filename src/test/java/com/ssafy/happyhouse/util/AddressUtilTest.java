package com.ssafy.happyhouse.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressUtilTest {

    @Test
    void assembleAddrPartsToLegacyAddr() {
        String assembled1 = AddressUtil.assembleAddrPartsToLegacyAddr(
                "서울특별시", "성동구", "금호동2가", "1", "0"
        );
        Assertions.assertThat(assembled1).isEqualTo("서울특별시 성동구 금호동2가 1");

        String assembled2 = AddressUtil.assembleAddrPartsToLegacyAddr(
                "서울특별시", "성동구", "금호동2가", "1", "1"
        );
        Assertions.assertThat(assembled1).isEqualTo("서울특별시 성동구 금호동2가 1-1");
    }

    @Test
    void changeComplexAddrToLegacyAddr() {
    }

}
