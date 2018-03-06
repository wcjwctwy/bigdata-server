package cn.lsmsp.bigdata.service.impl;

import cn.lsmsp.bigdata.entity.EventAnalyse;
import cn.lsmsp.bigdata.service.EventclassService;
import cn.lsmsp.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author WangCongJun
 * @date 2018/3/5
 * Created by WangCongJun on 2018/3/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EventclassServiceImplTest {

    @Autowired
    private EventclassService eventclassService;

    @Test
    public void getStatResult() throws Exception {
        List<EventAnalyse> entId = eventclassService.getStatResult("entId,assetId");
        Assert.assertNotNull(entId);
        log.info("【EventclassService】getStatResult : {}",entId);
    }

    @Test
    public void getStatResult1() throws Exception {
    }

    @Test
    public void getStatResults() throws Exception {
        EventAnalyse eventAnalyse = new EventAnalyse();
        List<Map<String, Long>> statResults = eventclassService.getStatResults(null);
        Assert.assertNotNull(statResults);
        log.info("【EventclassService】getStatResults : {}", JsonUtils.objectToJson(statResults));
    }

    @Test
    public void getStatResults1() throws Exception {
    }

    @Test
    public void getTimeLineStatResultsByMonth() throws Exception {
    }

}