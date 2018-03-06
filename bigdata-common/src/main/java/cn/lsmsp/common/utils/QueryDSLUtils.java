package cn.lsmsp.common.utils;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanOperation;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.util.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * @date 2018/3/6
 * Created by WangCongJun on 2018/3/6.
 */
@Slf4j
public class QueryDSLUtils {
    /**
     * 传入的对象，其中包含了此次查询的条件，将其装换成BooleanExpression
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static BooleanExpression[] transObj2Expression(Object obj) throws Exception {
        //获取对象的属性map
        PropertyDescriptor[] propertyDescriptors = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptors(obj);
        String className = obj.getClass().getSimpleName();
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            Class<?> propertyType = descriptor.getPropertyType();
            String name = descriptor.getName();
            Object invoke = descriptor.getReadMethod().invoke(obj);
            System.out.println(invoke);
            if (invoke != null && ObjectUtils.isEmpty(invoke)&&!"class".equals(name)) {
                StringPath ep1 = Expressions.stringPath(lowerCaseClassName(className) + "." + name);
                Expression<?> ep2 = ExpressionUtils.toExpression(invoke);
                BooleanOperation booleanOperation = Expressions.booleanOperation(Ops.EQ, ep1, ep2);
                booleanExpressions.add(booleanOperation);
            }
        }


        int size = booleanExpressions.size();
        BooleanExpression[] expressions = new BooleanExpression[size];
        for (int i = 0; i < size; i++) {
            expressions[i] = booleanExpressions.get(i);
        }
        return expressions;
    }

    private static String lowerCaseClassName(String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

}
