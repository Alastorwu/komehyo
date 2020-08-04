package com.komehyo.service;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.DocumentException;
import com.komehyo.dao.entity.International;
import com.komehyo.dao.mapper.config.InternationalMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodToPdfServiceTest {


    @Resource
    private GoodToPdfService goodToPdfService;

    @Resource
    private InternationalMapper internationalMapper;

    @Test
    public void goodToPdf() throws IOException, DocumentException {
        String s = goodToPdfService.goodToPdf(701);
        System.out.println(s);
    }

    @Test
    public void dgfdg() {
        International international = internationalMapper.selectById("00230b532991");
        System.out.println(JSON.toJSONString(international));
    }

    @Test
    public void goodToPdZip() throws Exception {
        List<Integer> ids = new ArrayList<>();
        ids.add(10);
        ids.add(17);
        ids.add(101);
        ids.add(102);
        goodToPdfService.goodToPdZip(ids);
    }


}