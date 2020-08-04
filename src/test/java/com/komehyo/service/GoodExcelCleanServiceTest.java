package com.komehyo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodExcelCleanServiceTest {

    @Resource
    private  GoodExcelCleanService goodExcelCleanService;


    @Test
    public void cheanGoods() {
        goodExcelCleanService.cheanGoods();
    }
}