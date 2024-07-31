package org.amp.springcloud.msvc.cursos.infrastructure.clients;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @SuppressWarnings("deprecation")
    @Bean
    public Request.Options options() {
        return new Request.Options(10000, 60000);
    }
}
