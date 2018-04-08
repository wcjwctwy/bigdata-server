import java.lang.annotation.*;

/**
 * @author WangCongJun
 * @date 2018/3/21
 * Created by WangCongJun on 2018/3/21.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.PARAMETER)
public @interface TestA {
    String value();
}
