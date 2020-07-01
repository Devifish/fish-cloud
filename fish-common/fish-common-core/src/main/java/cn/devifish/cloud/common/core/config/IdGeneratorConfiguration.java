package cn.devifish.cloud.common.core.config;

import cn.devifish.cloud.common.core.generator.IdGenerator;
import cn.devifish.cloud.common.core.generator.SnowflakeIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SnowflakeIdConfiguration
 * ID生成器配置（雪花算法）
 *
 * @author Devifish
 * @date 2020/7/1 18:01
 */
@Configuration
public class IdGeneratorConfiguration {

    private static final long DEFAULT_START_TIMESTAMP = 1577808000000L; // 2020-01-01 00:00:00:000

    @Bean
    public IdGenerator snowflakeIdGenerator() {
        return new SnowflakeIdGenerator(DEFAULT_START_TIMESTAMP, 0, 0);
    }
}
