package cn.devifish.cloud.common.mybatis.config;

import cn.devifish.cloud.common.core.generator.IdGenerator;
import cn.devifish.cloud.common.mybatis.handler.MybatisMetaObjectHandler;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MybatisConfiguration
 * Mybatis公共配置
 *
 * @author Devifish
 * @date 2020/6/30 16:27
 */
@Slf4j
@Configuration
@Import(MybatisMetaObjectHandler.class)
@RequiredArgsConstructor
@EnableTransactionManagement
@MapperScan(basePackages = "cn.devifish.cloud.**.mapper", annotationClass = Mapper.class)
public class MybatisConfiguration {

    private final IdGenerator idGenerator;

    /**
     * Mybatis 分页插件
     *
     * @return PaginationInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        log.info("Initializing Mybatis Plus Interceptor");
        var interceptor = new MybatisPlusInterceptor();

        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    @Bean
    @SuppressWarnings("deprecation")
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }

    /**
     * Mybatis ID生成器
     * 使用雪花算法实现
     *
     * @return IdentifierGenerator
     */
    @Bean
    public IdentifierGenerator identifierGenerator() {
        return entity -> idGenerator.nextId();
    }

}
