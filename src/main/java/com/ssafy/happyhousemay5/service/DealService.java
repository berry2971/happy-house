package com.ssafy.happyhousemay5.service;

import com.ssafy.happyhousemay5.domain.entity.Deal;
import com.ssafy.happyhousemay5.mapper.DealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealService {

    private final DealMapper dealMapper;

    @Autowired
    public DealService(DealMapper dealMapper) {
        this.dealMapper = dealMapper;
    }

    public List<Deal> getAll() throws Exception {
        return dealMapper.findAll();
    }

    public List<Deal> getDealsByLegacyAddress(
            String addr_lv1,
            String addr_lv2,
            String addr_lv3,
            String bunji_main,
            String bunji_sub
    ) throws Exception {
        return dealMapper.findByLegacyAddress(addr_lv1, addr_lv2, addr_lv3, bunji_main, bunji_sub);
    }

}
