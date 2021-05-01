package project.watch_and_relax.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.watch_and_relax.interceptor.StatsInterceptor;

@Component
public class WebConfig implements WebMvcConfigurer {
    private StatsInterceptor statsInterceptor;

    public WebConfig(StatsInterceptor statsInterceptor) {
        this.statsInterceptor = statsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(statsInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
        registry.addResourceHandler("/videos/**").addResourceLocations("file:videos/");
    }
}
