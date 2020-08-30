package ru.geekbrains.spring.ishop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import ru.geekbrains.spring.ishop.rest.converters.DeliveryToOutDeliveryConverter;
import ru.geekbrains.spring.ishop.rest.converters.OrderItemToOutOrderItemConverter;
import ru.geekbrains.spring.ishop.rest.converters.OrderToOutOrderConverter;
import ru.geekbrains.spring.ishop.rest.converters.ProductToOutProductConverter;

import java.util.Locale;

@Configuration
@PropertySource("classpath:private.properties")
@ComponentScan("ru.geekbrains.spring.ishop")
public class AppConfig implements WebMvcConfigurer {
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/images/**")) {
            registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
        }
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(new Locale("ru"));
        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ProductToOutProductConverter());
        registry.addConverter(new OrderToOutOrderConverter());
        registry.addConverter(new OrderItemToOutOrderItemConverter());
        registry.addConverter(new DeliveryToOutDeliveryConverter());
    }

}
