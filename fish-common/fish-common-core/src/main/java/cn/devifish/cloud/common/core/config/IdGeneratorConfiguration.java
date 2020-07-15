package cn.devifish.cloud.common.core.config;

import cn.devifish.cloud.common.core.generator.IdGenerator;
import cn.devifish.cloud.common.core.generator.SnowflakeIdGenerator;
import cn.devifish.cloud.common.core.util.NetworkUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * SnowflakeIdConfiguration
 * ID生成器配置（雪花算法）
 *
 * @author Devifish
 * @date 2020/7/1 18:01
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class IdGeneratorConfiguration {

    private static final long DEFAULT_START_TIMESTAMP = 1577808000000L; // 2020-01-01 00:00:00:000

    @Value("${spring.application.name}")
    private String applicationName;

    private long dataCenterId;
    private long machineId;

    /**
     * 随机初始化雪花算法基础参数
     * dataCenterId 通过服务名称Hash值取模计算得出
     * machineId 通过网卡MAC地址Hash值取模计算得出
     */
    @PostConstruct
    private void init() {
        var localMacAddress = NetworkUtil.getLocalMacAddress();
        this.dataCenterId = Math.abs(applicationName.hashCode() % SnowflakeIdGenerator.MAX_DATA_CENTER_ID);
        this.machineId = Math.abs(localMacAddress.hashCode() % SnowflakeIdGenerator.MAX_MACHINE_ID);
    }

    @Bean
    public IdGenerator snowflakeIdGenerator() {
        return new SnowflakeIdGenerator(DEFAULT_START_TIMESTAMP, dataCenterId, machineId);
    }
}
