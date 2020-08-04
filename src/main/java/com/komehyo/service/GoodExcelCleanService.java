package com.komehyo.service;

import com.komehyo.dao.entity.GoodExcelWithBLOBs;
import com.komehyo.dao.mapper.app.GoodExcelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class GoodExcelCleanService {

    @Resource
    private GoodExcelMapper goodExcelMapper;

    @Transactional(rollbackFor = Exception.class)
    public void cheanGoods(){
        List<GoodExcelWithBLOBs> goods = goodExcelMapper.selectYesterday();
        log.info("GoodExcel获取到{}条前一天数据，开始执行清除！",goods.size());
        if(!CollectionUtils.isEmpty(goods)){
            goods.forEach(g->goodExcelMapper.deleteByPrimaryKey(g.getId()));
        }
        log.info("清除完成");
    }

}
