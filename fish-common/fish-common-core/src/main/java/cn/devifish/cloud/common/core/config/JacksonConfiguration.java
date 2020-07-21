package cn.devifish.cloud.common.core.config;

import cn.devifish.cloud.common.core.constant.DateTimeConstant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 * JacksonConfiguration
 * Jackson公共配置
 *
 * @author Devifish
 * @date 2020/6/30 16:27
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        log.info("Initializing Jackson JDK8 Time Module");

        return builder -> {
            builder.locale(Locale.CHINA);
            builder.timeZone(TimeZone.getTimeZone(DateTimeConstant.TIME_ZONE));
            builder.simpleDateFormat(DateTimeConstant.DATE_TIME_PATTERN);
            builder.modules(initJavaTimeModule(), smartLongModule());
        };
    }

    /**
     * JDK8 时间序列化模块配置
     *
     * @return JavaTimeModule
     */
    private JavaTimeModule initJavaTimeModule() {
        var javaTimeModule = new JavaTimeModule();
        var dateTimeFormatter = DateTimeFormatter.ofPattern(DateTimeConstant.DATE_TIME_PATTERN);
        var dateFormatter = DateTimeFormatter.ofPattern(DateTimeConstant.DATE_PATTERN);
        var timeFormatter = DateTimeFormatter.ofPattern(DateTimeConstant.TIME_PATTERN);

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        return javaTimeModule;
    }

    /**
     * 对Long类型数据智能序列化模块
     *
     * @return SimpleModule
     */
    private SimpleModule smartLongModule() {
        var module = new SimpleModule();
        var smartLongSerializer = new SmartLongSerializer();
        module.addSerializer(Long.class, smartLongSerializer);
        module.addSerializer(Long.TYPE, smartLongSerializer);
        return module;
    }

    /**
     * SmartLongSerializer
     * 智能序列化Long类型数据
     * 当数据超过Javascript Number类型最大值时转换为String
     *
     * @author Devifish
     * @date 2019/8/9 15:35
     */
    private static class SmartLongSerializer extends StdSerializer<Long> {

        private final static long JAVASCRIPT_NUMBER_BITS = 53L;
        private final static long JAVASCRIPT_NUMBER_MAX_SIZE = ~(-1L << JAVASCRIPT_NUMBER_BITS);

        protected SmartLongSerializer() {
            super(Long.class);
        }

        @Override
        public void serialize(Long value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value == null) {
                gen.writeNull();
                return;
            }

            // 当数据超过Javascript Number类型最大值时转换为String
            var temp = (long) value;
            if (temp >= JAVASCRIPT_NUMBER_MAX_SIZE) {
                gen.writeString(value.toString());
            } else {
                gen.writeNumber(temp);
            }
        }
    }

}
