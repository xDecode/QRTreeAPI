package org.marv.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CustomResponseHeaderFilter> customFilter() {
        FilterRegistrationBean<CustomResponseHeaderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomResponseHeaderFilter());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
}