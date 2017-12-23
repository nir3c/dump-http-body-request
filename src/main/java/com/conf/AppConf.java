package com.conf;

import com.filter.RequestFilter;
import com.intersaptor.DumpBodyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Nir.
 */
@Configuration
public class AppConf extends WebMvcConfigurerAdapter {

    @Autowired
    private DumpBodyInterceptor interseptor;
    @Autowired
    private RequestFilter filter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interseptor).addPathPatterns("/dumpbody", "/dumpbody/");
    }

    @Bean
    public FilterRegistrationBean myFilterBean(){
        FilterRegistrationBean myfilter = new FilterRegistrationBean();
        myfilter.setFilter(filter);
        myfilter.addUrlPatterns("/*");
        myfilter.setEnabled(Boolean.TRUE);
        myfilter.setName("Meu Filter");
        myfilter.setAsyncSupported(Boolean.TRUE);
        return myfilter;
    }

}
