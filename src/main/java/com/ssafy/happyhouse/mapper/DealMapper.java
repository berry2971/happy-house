package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.domain.dto.DealSummaryDto;
import com.ssafy.happyhouse.domain.entity.Deal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DealMapper {

    List<Deal> findAll() throws Exception;

    List<Deal> findByLegacyAddress(String addr_lv1, String addr_lv2, String addr_lv3, String bunji)  throws Exception;

    Deal findById(Long id) throws Exception;

    void save(Deal deal) throws Exception;

    DealSummaryDto findDealSummaryByLegacyAddress(String addr_lv1, String addr_lv2, String addr_lv3, String bunji) throws Exception;

}
