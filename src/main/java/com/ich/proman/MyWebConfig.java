package com.ich.proman;

import com.ich.admin.interceptor.EmployeeInterceptor;
import com.ich.admin.listener.EmployeeSessionListener;
import com.ich.config.processor.IConfigProcessor;
import com.ich.config.service.IConfigService;
import com.ich.config.service.impl.ILocalConfigServiceImpl;
import com.ich.core.http.service.CallbackService;
import com.ich.core.http.service.impl.CoreServiceImpl;
import com.ich.core.listener.SystemConfigListener;
import com.ich.international.processor.ILocaleInitProcessor;
import com.ich.international.service.impl.InternationalCallbackServiceImpl;
import com.ich.log.interceptor.ILogInterceptor;
import com.ich.log.service.impl.ILogCallbackServiceImpl;
import com.ich.module.processor.ModularInitProcessor;
import com.ich.module.processor.ResourcesInitProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan({"com.ich.*.service"})
public class MyWebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public SystemConfigListener getSystemConfigListener(){
        return new SystemConfigListener();
    }

    @Bean
    public EmployeeSessionListener getEmployeeSessionListener(){
        return new EmployeeSessionListener();
    }

    @Bean
    public ILogInterceptor getILogInterceptor(){
        return new ILogInterceptor();
    }

    @Bean
    public EmployeeInterceptor getEmployeeInterceptor(){
        return new EmployeeInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(getILogInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(getEmployeeInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public IConfigProcessor getIConfigProcessor(){
        return new IConfigProcessor();
    }

    @Bean
    public ResourcesInitProcessor getResourcesInitProcessor(){
        return new ResourcesInitProcessor();
    }

    @Bean
    public ModularInitProcessor getModularInitProcessor(){
        return new ModularInitProcessor();
    }

    @Bean
    public ILocaleInitProcessor getILocaleInitProcessor(){
        return new ILocaleInitProcessor();
    }

    @Bean
    public ILogCallbackServiceImpl getILogCallbackServiceImpl(){
        return new ILogCallbackServiceImpl();
    }

    @Bean
    public InternationalCallbackServiceImpl getInternationalCallbackServiceImpl(){
        return new InternationalCallbackServiceImpl();
    }

    @Bean
    public CoreServiceImpl getCoreServiceImpl(){
        CoreServiceImpl impl = new CoreServiceImpl();
        List<CallbackService> backList = new ArrayList<>();
        backList.add(getILogCallbackServiceImpl());
        backList.add(getInternationalCallbackServiceImpl());
        impl.setBackList(backList);
        return impl;
    }

    @Bean
    public IConfigService getIConfigService(){
        return new ILocalConfigServiceImpl();
    }
}
