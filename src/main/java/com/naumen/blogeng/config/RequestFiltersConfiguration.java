package com.naumen.blogeng.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.util.List;

@Configuration
public class RequestFiltersConfiguration {

    /**
     * Обрабатывает скрытые HTTP-запросы со стороны клиента, читает скрытые поля, определяющие фактический метод
     * запроса, добавляет реальный метод для дальнешей обработки на сервере.
     * @return входной фильтр приложения, необходимый для чтения скрытых полей запроса;
     */
    @Bean
    public FilterRegistrationBean hiddenHttpMethodFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new HiddenHttpMethodFilter());
        filterRegistrationBean.setUrlPatterns(List.of("/*"));
        return filterRegistrationBean;
    }
}