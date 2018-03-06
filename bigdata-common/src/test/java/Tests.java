import cn.lsmsp.common.pojo.analyse.TbEventAnalyse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.beans.PropertyDescriptor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
public class Tests {
    public static void main(String[] args) throws Exception{
        TbEventAnalyse tbEventAnalyse = new TbEventAnalyse();
        tbEventAnalyse.setYear(Short.valueOf("5"));
        tbEventAnalyse.setEventLevel("info");
        PropertyDescriptor[] propertyDescriptors = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptors(tbEventAnalyse);
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            Class<?> propertyType = descriptor.getPropertyType();
            String name = descriptor.getName();
            Object invoke = descriptor.getReadMethod().invoke(tbEventAnalyse);
            log.info("propertyType:{},name:{},invoker:{}",propertyType,name,invoke);

        }
    }
}
