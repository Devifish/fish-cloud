package cn.devifish.cloud.common.mysql.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import lombok.extern.slf4j.Slf4j;
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
@EnableTransactionManagement
@MapperScan("cn.devifish.cloud.**.mapper")
public class MybatisConfiguration {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        log.info("Initializing Mybatis Pagination");
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
}
