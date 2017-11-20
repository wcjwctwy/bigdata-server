package cn.lsmsp.bigdata.check.policy.utils;


import cn.lsmsp.bigdata.check.policy.pojo.TbEventRulerMain;
import cn.lsmsp.common.utils.CodeUtils;
import org.springframework.util.StringUtils;

public class TbEventRulerCodeC {
    /**
     * 编码TbEventRuler对象中的数据
     * @param tbEventRuler
     * @return
     */
    public static TbEventRulerMain encode(TbEventRulerMain tbEventRuler, String codeFormat){
        String rulerName = tbEventRuler.getRulerName();
        if(!StringUtils.isEmpty(rulerName)) {
            tbEventRuler.setRulerName(CodeUtils.rulerEncode(rulerName, codeFormat));
        }
        String regex = tbEventRuler.getRegex();
        if(!StringUtils.isEmpty(regex)) {
            tbEventRuler.setRegex(CodeUtils.rulerEncode(regex, codeFormat));
        }
        String groupName = tbEventRuler.getGroupName();
        if(!StringUtils.isEmpty(groupName)) {
            tbEventRuler.setGroupName(CodeUtils.rulerEncode(groupName, codeFormat));
        }
        String rulerContent = tbEventRuler.getRulerContent();
        if(!StringUtils.isEmpty(rulerContent)) {
            tbEventRuler.setRulerContent(CodeUtils.rulerEncodeJson(rulerContent, codeFormat));
        }
        return tbEventRuler;
    }

    /**
     * 解码TbEventRuler对象中的数据
     * @param tbEventRuler
     * @return
     */
    public static TbEventRulerMain decode(TbEventRulerMain tbEventRuler, String codeFormat){
        String rulerName = tbEventRuler.getRulerName();
        tbEventRuler.setRulerName(CodeUtils.rulerDecode(rulerName,codeFormat));
        String regex = tbEventRuler.getRegex();
        tbEventRuler.setRegex(CodeUtils.rulerDecode(regex,codeFormat));
        String groupName = tbEventRuler.getGroupName();
        tbEventRuler.setGroupName(CodeUtils.rulerDecode(groupName,codeFormat));
        String rulerContent = tbEventRuler.getRulerContent();
        tbEventRuler.setRulerContent(CodeUtils.rulerDecodeJson(rulerContent));
        return tbEventRuler;
    }


    /**
     * 编码TbEventRuler对象中的数据
     * 编码格式： UTF-8
     * @param tbEventRuler
     * @return
     */
    public static TbEventRulerMain encode(TbEventRulerMain tbEventRuler){
        return encode(tbEventRuler,"UTF-8");
    }

    /**
     * 解码TbEventRuler对象中的数据
     * 解码格式： UTF-8
     * @param tbEventRuler
     * @return
     */
    public static TbEventRulerMain decode(TbEventRulerMain tbEventRuler){
        return decode(tbEventRuler,"UTF-8");
    }

}
