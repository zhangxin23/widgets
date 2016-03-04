package net.coderland.server.api.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.tuckey.web.filters.urlrewrite.UrlRewriteWrappedResponse;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Created by zhangxin on 16/3/4.
 */
public class CustomerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(CustomerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        logger.info("###CustomerInterceptor: preHandle###");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        logger.info("###CustomerInterceptor: postHandle###");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(ex != null) {
            logger.info("###Exception occured!###");
        } else {

            logger.info("###Welcome, this is customer interceptor.###");

            logger.info("###Request: " + request.getPathInfo());
            logger.info("###Request: " + request.getQueryString());
            logger.info("###Request: " + request.getParameter("id"));


            ContentCachingResponseWrapper contentCachingResponseWrapper = (ContentCachingResponseWrapper) (((UrlRewriteWrappedResponse) response).getResponse());
            logger.info("###Response: " + new String(contentCachingResponseWrapper.getContentAsByteArray()));
            logger.info("###Response: " + contentCachingResponseWrapper.getContentType());
            logger.info("###Response: " + contentCachingResponseWrapper.getStatusCode());

            logger.info("###Bye, leave customer interceptor.###");
        }
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    }
}
