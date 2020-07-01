package cn.devifish.cloud.common.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * RestTemplateConfiguration
 * RestTemplate配置
 *
 * @author Devifish
 * @date 2020/6/30 16:24
 */
@Slf4j
@Configuration
@ConditionalOnClass(RestTemplate.class)
public class RestTemplateConfiguration {

    @Value("${spring.rest-template.connect-timout:10}")
    private int connectTimout;
    @Value("${spring.rest-template.read-timeout:10}")
    private int readTimeout;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        log.info("Initializing Rest Template");

        return new RestTemplateBuilder()
                .requestFactory(SimpleClientHttpRequestFactory.class)
                .setConnectTimeout(Duration.ofSeconds(connectTimout))
                .setReadTimeout(Duration.ofSeconds(readTimeout))
                .build();
    }
}
