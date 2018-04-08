import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author WangCongJun
 * @date 2018/3/21
 * Created by WangCongJun on 2018/3/21.
 */
public class test {

    public void get(@TestA("helloworld") String value){

    }

    public static void main(String[] args) throws Exception{

        Method get = test.class.getMethod("get",String.class);
        Annotation[][] parameterAnnotations = get.getParameterAnnotations();
        for (Annotation[] parameterAnnotation:parameterAnnotations){
            for (Annotation a:parameterAnnotation){
                TestA a1 = (TestA) a;
                System.out.println(a1.value());
            }
        }
    }
}
