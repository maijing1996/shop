package com.shop.conf;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
//import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import com.shop.model.dto.RedisUser;
import com.shop.util.RedisUtil;

/**
 * 拦截器
 * 偶木
 * 2018年10月16日
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	private Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);
	
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	public MySiteSetting mySiteSetting;
	
    /**
     * 文件上传配置 
     * 
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("10240KB"); //10MB
        // 总上传数据大小
        factory.setMaxRequestSize("512000KB"); //50M
        return factory.createMultipartConfig();
    }

    /**
     *   自定义静态资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").setCachePeriod(600)
                .resourceChain(true)
                .addResolver(new VersionResourceResolver().addVersionStrategy(new MyVersionPathStrategy(), "/**"));
    }

    //拦截器实现逻辑拦截
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	//管理系统接口拦截器
    	registry.addInterceptor(new HandlerInterceptorAdapter() {
    		@Override
    		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    				throws Exception {
    			
    			//logger.info("url:" + url);
    			String key = mySiteSetting.getSessionName() + request.getSession().getId();
    			RedisUser redisUser = (RedisUser) redisUtil.get(key);
    			if(redisUser == null) {
    				String url = request.getRequestURI();
    				logger.info("用户信息过期，重新登录！url："+url);
    				request.getRequestDispatcher("/manager/login/restart").forward(request, response);
    				return false;
    			}
    			return true;
            }
    	}).addPathPatterns("/manager/**").excludePathPatterns("/manager/login/restart", "/manager/login/enter", "/manager/login/menu");
    	
    	//前段拦截器
    	/*registry.addInterceptor(new HandlerInterceptorAdapter() {
    		@Override
    		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    				throws Exception {
    			return true;
            }
    	}).addPathPatterns("/wechat/to*","/wechat/shop/*").excludePathPatterns("/");*/
    	//http://www.usambl.com/start/index.html#/distribution/bills
    }
    
    /**
     * 
     * 页面登录拦截及重定向页面
     * 
     * 实现登录操作
     */
    /*public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addViewController("/").setViewName("classpath:static/1.html");
	}*/
}