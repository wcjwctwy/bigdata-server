package cn.lsmsp.bigdata.protal.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class GlobalExceptionResovler implements HandlerExceptionResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionResovler.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        LOGGER.info("进入异常处理器......");
        LOGGER.info("handler : "+o.getClass());
        LOGGER.error("系统出现异常",e.getMessage());
        //处理异常后返回的视图
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("e.getClass().getSimpleName():"+e.getClass().getSimpleName());
        if(e.getClass().getSimpleName().equals("UncategorizedSolrException")){
            String errorHtml = e.getMessage().split("Expected mime type application/octet-stream but got text/html.")[1];
            httpServletResponse.setContentType("text/html;charset=utf-8");
            try {
                PrintWriter out = httpServletResponse.getWriter();
                out.println(errorHtml);
            }catch (Exception e1){
                e1.printStackTrace();
            }

        }else{
            //向页面中返回参数
            modelAndView.addObject("message","网络连接失败，请稍后重试");
            //返回的异常页面

            modelAndView.setViewName("error/exception");

        }
        return modelAndView;
    }
}