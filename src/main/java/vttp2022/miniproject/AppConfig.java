package vttp2022.miniproject;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vttp2022.miniproject.filters.VerificationFilter;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<VerificationFilter> registerFilters() {

        VerificationFilter verFilter = new VerificationFilter();

        FilterRegistrationBean<VerificationFilter> regBean = new FilterRegistrationBean<>();
        regBean.setFilter(verFilter);
        regBean.addUrlPatterns("/verified/*");
        
        return regBean;
    }
    
}
