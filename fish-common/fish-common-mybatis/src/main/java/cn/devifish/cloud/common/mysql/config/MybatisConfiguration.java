package cn.devifish.cloud.common.mysql.config;

import cn.devifish.cloud.common.core.generator.IdGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public PaginationInterceptor paginationInterceptor() {
        log.info("Initializing Mybatis Pagination");
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
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
