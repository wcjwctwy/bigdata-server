package cn.lsmsp.check.policy.utils;

import cn.lsmsp.bigdata.check.policy.pojo.TbEventRulerMain;
import cn.lsmsp.bigdata.check.policy.pojo.TranslationRuler;
import cn.lsmsp.common.exception.BigException;
import cn.lsmsp.common.utils.JsonUtils;
import org.apache.commons.beanutils.BeanUtils;
import cn.lsmsp.bigdata.check.policy.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalyseUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyseUtils.class);
    private static final String TRANSLATE = "translate";

    public static Map<String, String> analyseLog(TbEventRulerMain ruler, String rawEvent, List<TranslationRuler> translationRulers) {
        String condition = ruler.getRegex();
        Map<String, String> result = analyseLog(ruler, rawEvent, translationRulers, condition);
        return result;
    }

    public static Map<String, String> analyseLog(TbEventRulerMain ruler, String rawEvent, List<TranslationRuler> translationRulers, String condition) {
        LOGGER.debug("condition: " + condition);
        Map<String, String> content = JsonUtils.jsonToPojo(ruler.getRulerContent(), HashMap.class);
        Map<String, String> result = analyseLog(content, rawEvent, translationRulers, condition);
        return result;
    }


    public static Map<String, String> analyseLog(Map<String, String> content, String rawEvent, List<TranslationRuler> translationRulers, String condition){

        Map<String, String> result = new HashMap<>();
        LOGGER.debug("translationRulers: " + translationRulers.toString());
        LOGGER.debug("content: " + content.toString());
        LOGGER.debug("condition: " + condition);
        LOGGER.debug("rawEvent: " + rawEvent);
        Pattern pattern = Pattern.compile(condition);
        Matcher matcher = pattern.matcher(rawEvent);
        if(!matcher.matches()) {
            throw new BigException("正则表达式有误！！");
        }
        //先解析${}
        for (Map.Entry<String, String> e : content.entrySet()) {
            String k = e.getKey();
            String v = e.getValue();
            result.put(k, v);
             matcher = pattern.matcher(rawEvent);
            if (v.contains("${")) {
                String MatchValue = matcherRulerField(v, matcher);
                LOGGER.debug("k: " + k + " v: " + v + " MatchValue: " + MatchValue);
                result.put(k, MatchValue);
            }
        }
        //如果这里是以translate开头 则需要在进行转换
        for (Map.Entry<String, String> e : result.entrySet()) {
            String k = e.getKey();
            String v = e.getValue();
            LOGGER.debug("k: " + k + " v: " + v);
            if (v.startsWith(TRANSLATE)) {
                //如果这里是以translate${开头 则需要在进行转换
                result = translateHandler(k, result, translationRulers);
            }
        }
        return result;
    }

    /**
     * 将字符串中的${num}通过正则表达式将其替换
     *
     * @param v
     * @param matcher
     * @return
     */
    private static String matcherRulerField(String v, Matcher matcher) {
        String result = v;
        //获取Ruler中字段${num}中的num
        List<Integer> nums = getGroupNum(v);
        while (matcher.find()) {
            for (Integer num : nums) {
                String rexgResult = matcher.group(num);
                LOGGER.debug("rexgResult: " + rexgResult);
                //替换原字段中的${num}
                result = result.replace("${" + num + "}", rexgResult);
            }

        }
        return result;
    }

    /**
     * 获取字段中所有的${num}中的num
     *
     * @param str
     * @return
     */
    private static List<Integer> getGroupNum(String str) {
        List<Integer> nums = new ArrayList<>();
        String[] ss = str.split("\\$\\{");
        for (int i = 1; i < ss.length; i++) {
            String s = ss[i].split("}")[0];
            nums.add(Integer.valueOf(s));
        }
        return nums;
    }


    /**
     * 对有translate的数据进行深度转化
     *
     * @param k
     * @param result
     * @param translationRulers
     * @return
     */
    private static Map<String, String> translateHandler(String k, Map<String, String> result, List<TranslationRuler> translationRulers) {
        String v = result.get(k);
        v = v.replaceFirst("translate", "");
        TranslationRuler tr = null;
        for (TranslationRuler translationRuler : translationRulers) {
            if (translationRuler.getKey().equals(v.toLowerCase())) {
                tr = translationRuler;
            }
        }

        if (tr == null) {
            //如果没有查找到TranslationRuler直接返回result
            return result;
        }
        //将获取的TranslationRuler中的值赋给result
        Map<String, String> stringMap = null;
        try {
            stringMap = BeanUtils.describe(tr);
            LOGGER.debug("TranslationRuler: " + stringMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //删除key 和 value
        stringMap.remove("key");
        stringMap.remove("value");
        stringMap.remove("class");
        for (Map.Entry<String, String> e : stringMap.entrySet()) {
            String newk = StringUtils.upCaseFirst(e.getKey());
            LOGGER.debug("newk: " + newk);
            result.put(newk, e.getValue());
        }
        return result;
    }

}
