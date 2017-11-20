package cn.lsmsp.bigdata.check.policy.utils;

import cn.lsmsp.bigdata.check.policy.pojo.Ruler;
import cn.lsmsp.bigdata.check.policy.pojo.XmlRuler;
import cn.lsmsp.bigdata.check.policy.pojo.TranslationRuler;
import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;


public class RulerUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RulerUtils.class);

    /**
     * 将文件夹下的xml规则文件加载到内存
     *
     * @param rulerPath 规则文件所在目录
     * @return
     */
    public static Map<String, XmlRuler> loadRuler(String rulerPath) {
        Map<String, XmlRuler> ruleListMap = new HashMap<>();
        File dir = new File(rulerPath);
        if (!dir.isDirectory()) {
            try {
                throw new Exception(rulerPath + "不是一个目录");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File[] fs = dir.listFiles();
        if (fs == null || fs.length == 0) {
            return ruleListMap;
        }
        List<File> files = Arrays.asList(fs);
        files.forEach(f -> {
            LOGGER.debug("fileName:  " + f.getName());
            //将一个文件转换成RulerConfig
            XmlRuler rulerConfig = transFile2RulerConfig(f);
            ruleListMap.put(f.getName(), rulerConfig);
        });
        return ruleListMap;
    }


    /**
     * 将xml文件转换成XmlRuler
     *
     * @param file xml文件
     * @return
     */
    public static XmlRuler transFile2RulerConfig(File file) {
        XmlRuler rulerConfig = new XmlRuler();
        SAXReader sax = new SAXReader();//创建一个SAXReader对象
        Document document = null;//获取document对象,如果文档无节点，则会抛出Exception提前结束
        try {
            document = sax.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();//获取根节点
        Element pluginCode = root.element("pluginCode");
        String pluginCodeTextTrim = pluginCode.getTextTrim();
        //获取pluginCode
        rulerConfig.setPluginCode(Integer.valueOf(pluginCodeTextTrim));
        //获取translation
        rulerConfig.setTranslation(getTranslationRulers(root));
        //获取rulers
        rulerConfig.setRulers(getRulers(root));
        return rulerConfig;
    }


    /**
     * 获取文件中的translation
     *
     * @param root
     * @return
     */
    public static List<TranslationRuler> getTranslationRulers(Element root) {
        Iterator<Element> translation = root.elementIterator("translation");
        List<TranslationRuler> translationRulers = new ArrayList<>();
        if (translation != null) {
            translation.forEachRemaining(e -> {

                Iterator<Element> maps = e.elementIterator();
                maps.forEachRemaining(map -> {
                    TranslationRuler translationRuler = new TranslationRuler();
                    List<Attribute> attrs = map.attributes();
                    attrs.forEach(attr -> {
                        String name = StringUtils.lowCaseFirst(attr.getName());
                        String value = attr.getValue();
                        LOGGER.debug(name + " " + value);
                        objFieldAddValue(translationRuler, name, value);
                    });
                    translationRulers.add(translationRuler);
                });

            });
        }
        return translationRulers;
    }


    /**
     * 获取文件中的ruler
     *
     * @param root
     * @return
     */
    public static List<Ruler> getRulers(Element root) {
        List<Ruler> rulers = new ArrayList<>();
        Iterator<Element> elements = root.elementIterator("rule");
        elements.forEachRemaining(e -> {
            Ruler ruler = new Ruler();
            Element condition = e.element("condition");
            ruler.setCondition(condition.getTextTrim());
            //获取rulerContent
            Iterator<Element> es = e.element("content").elementIterator();
            ruler.setContent(getRulerContents(es));
            rulers.add(ruler);
        });
        return rulers;
    }


    /**
     * 获取规则的具体内容
     *
     * @param es
     * @return
     */
    public static Map<String, String> getRulerContents(Iterator<Element> es) {
        Map<String, String> rulerContent = new HashMap<>();
        es.forEachRemaining(eContent -> {
            String name = eContent.getName();
            String textTrim = eContent.getTextTrim();
            rulerContent.put(name, textTrim);
        });
        return rulerContent;
    }

    /**
     * 给对象赋值
     *
     * @param obj        赋值对象
     * @param fieldName  属性名
     * @param fieldValue 属性值
     */
    public static void objFieldAddValue(Object obj, String fieldName, String fieldValue) {
        Class<?> objClass = obj.getClass();
        //获取objClass的方法名 属性名
        Method[] methods = objClass.getDeclaredMethods();
        //有此属性
        //获取get方法
        String methodName = StringUtils.setMethodName(fieldName);
        try {
            Method declaredMethod = objClass.getDeclaredMethod(methodName, String.class);
            declaredMethod.invoke(obj, fieldValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 以xml文件导出规则
     *
     * @param XmlRuler Xmlruler对象
     * @param filePath 文件导出路径
     * @param fileName 文件名
     */
    public static void exportRulersXmlFile(XmlRuler XmlRuler, String filePath, String fileName) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("ruleConfig");
        Element pluginCode = root.addElement("pluginCode");
        pluginCode.addText(XmlRuler.getPluginCode().toString());
        //加载translation
        List<TranslationRuler> translations = XmlRuler.getTranslation();
        addTranslation2Xml(root, translations);
        //加载rule
        List<Ruler> rulers = XmlRuler.getRulers();
        rulers.forEach(r -> {
            addRuler2Xml(root, r);
        });
        //导出数据
        saveDocumentToFile(document, filePath, fileName, false, "UTF-8");

    }

    /**
     * 添加规则信息到xml文件根节点中
     *
     * @param root  xml根节点
     * @param ruler 规则对象
     */
    public static void addRuler2Xml(Element root, Ruler ruler) {
        Element rule = root.addElement("rule");
        Element condition = rule.addElement("condition");
        condition.addText("<![CDATA[" + ruler.getCondition() + "]]>");
        Element content = rule.addElement("content");
        Map<String, String> rulerContent = ruler.getContent();
        addContent2Xml(content, rulerContent);
    }

    /**
     * 添加内容条目到content中
     *
     * @param content
     * @param rulerContent
     */
    public static void addContent2Xml(Element content, Map<String, String> rulerContent) {
        rulerContent.forEach((k, v) -> {
            if("EVENT_EXP".equals(k.toUpperCase())){
                v="<![CDATA[" + v + "]]>";
            }
            content.addElement(StringUtils.upCaseFirst(k))
                    .addText(v);
        });
    }

    /**
     * 添加translation信息到xml文件根节点中
     *
     * @param root
     * @param translations
     */
    public static void addTranslation2Xml(Element root, List<TranslationRuler> translations) {
        Element translation = root.addElement("translation");
        translations.forEach(tr -> {
            Element map = translation.addElement("map");
            Map<String, String> describe = null;
            try {
                describe = BeanUtils.describe(tr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            describe.remove("class");
            describe.forEach((k, v) -> {
                if (k.equals("value") || k.equals("key")) {
                    map.addAttribute(k, v);
                } else {
                    map.addAttribute(StringUtils.upCaseFirst(k), v);
                }

            });

        });
    }


    /**
     * 方法描述：<b>存储完整XML文件
     *
     * @param document
     * @param xmlFilePath
     * @param xmlFileName //     * @param isTrimText
     * @param xmlEncoding
     */
    public static void saveDocumentToFile(Document document,
                                          String xmlFilePath, String xmlFileName, boolean istrans,
                                          String xmlEncoding) {
        LOGGER.debug("正在保存的文件名是：" + xmlFileName);
        if (document == null || xmlFilePath == null || "".equals(xmlFileName)) {
            return;
        }
        File file = new File(xmlFilePath);
        // 判断路径是否存在，不存在创建
        if (!file.exists()) {
            file.mkdirs();
        }
        // 保存文件
        OutputFormat format = null;

//        if (isTrimText) {
        format = OutputFormat.createPrettyPrint();
//        } else {
//            format = DomXmlOutputFormat.createPrettyPrint();// 保留xml属性值中的回车换行
//        }

        if (xmlEncoding != null) {
            format.setEncoding(xmlEncoding);// GBK
        } else {
            format.setEncoding("UTF-8");// UTF-8
        }

            try {
                org.dom4j.io.XMLWriter xmlWriter = new org.dom4j.io.XMLWriter(
                        new FileOutputStream(xmlFilePath + "/" + xmlFileName), format);// FileOutputStream可以使UTF-8生效
            /*
            设置是否转义文件内容中的<、>、&等特殊字符
             */
                xmlWriter.setEscapeText(istrans);
                xmlWriter.write(document);
                xmlWriter.flush();
                xmlWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error("保存XML异常：" + e.getMessage());
            }

        // 存到文件中结束
    }

    public static void main(String[] args) {
        Map<String, XmlRuler> stringRulerConfigMap = loadRuler("/temp/ruler");
        stringRulerConfigMap.forEach((k, v) -> {
            exportRulersXmlFile(v, "/temp/rulers", k);
        });
    }

}
