package com.conf;

import com.filter.RequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Nir.
 */
@Configuration
public class AppConf extends WebMvcConfigurerAdapter {

    private RequestFilter filter;

    @Autowired
    public AppConf(RequestFilter filter) {
        this.filter = filter;
    }

    @Bean
    public FilterRegistrationBean myFilterBean(){
        FilterRegistrationBean myfilter = new FilterRegistrationBean();
        myfilter.setFilter(filter);
        myfilter.addUrlPatterns("/*");
        myfilter.setEnabled(Boolean.TRUE);
        myfilter.setName("my Filter");
        myfilter.setAsyncSupported(Boolean.TRUE);
        return myfilter;
    }

}
