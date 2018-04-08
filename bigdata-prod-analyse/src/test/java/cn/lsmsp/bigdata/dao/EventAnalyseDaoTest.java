package cn.lsmsp.bigdata.dao;

import cn.lsmsp.bigdata.dto.ResolutionDTO;
import cn.lsmsp.bigdata.entity.EventAnalyse;
import cn.lsmsp.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author WangCongJun
 * @date 2018/3/6
 * Created by WangCongJun on 2018/3/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EventAnalyseDaoTest {

    @Autowired
    private EventAnalyseDao eventAnalyseDao;
    @Test
    public void getGroupResults() throws Exception {
        EventAnalyse eventAnalyse = new EventAnalyse();
        eventAnalyse.setEntId(1L);
        eventAnalyse.setDay(5);
        List<EventAnalyse> groupResults = eventAnalyseDao.getGroupResults(eventAnalyse, "assetId,eventCategoryTechnique");
        log.info("【EventAnalyseDaoTest】 result Size:{}" ,groupResults.size());
        Assert.assertNotEquals(0,groupResults.size());
    }

    @Test
    public void findOne(){
        EventAnalyse eventAnalyse = new EventAnalyse();
        eventAnalyse.setEntId(1L);
        EventAnalyse one = eventAnalyseDao.findOne(eventAnalyse);
        Assert.assertNotNull(one);
    }

    @Test
    public void getResolution(){
        EventAnalyse eventAnalyse = new EventAnalyse();
        eventAnalyse.setDay(5);
        List<ResolutionDTO> entId = eventAnalyseDao.getResolution(eventAnalyse, "assetId");
        Assert.assertNotNull(entId);
        log.info("【EventAnalyseDaoTest】 getResolution:{}", JsonUtils.objectToJson(entId));
    }

    @Test
    public void getSum(){
        EventAnalyse eventAnalyse = new EventAnalyse();
        List<EventAnalyse> sum = eventAnalyseDao.getSum(eventAnalyse, "entId");
        Assert.assertNotNull(sum);
        log.info("【查询总量】sum：{}",JsonUtils.objectToJson(sum));
    }

}