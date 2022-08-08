package org.springMVC.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = {"org.springMVC.study"})
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver setResolver(){
        System.out.println("JZN");
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 本例子是 映射多个静态资源路径到某个URL path pattern,
        // 实际上也可以映射多个静态资源路径到多个 URL path pattern，
//        registry.addResourceHandler("/my/**")
//                .addResourceLocations("classpath:/MyStatic/","file:/web_starter_repo/");
//        registry.addResourceHandler("/my-static/**")
//                .addResourceLocations("classpath:/MyStatic/");
        registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/resource/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/resource/js/");
    }
}
