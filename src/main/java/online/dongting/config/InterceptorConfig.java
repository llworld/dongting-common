package online.dongting.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author：ll
 * @description: 注册拦截器，配置拦截路径.
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

  @Bean
  public HandlerInterceptor getInterceptor() {
    return new WebInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry interceptorRegistry) {
    // 注册拦截器
    InterceptorRegistration interceptorRegistration = interceptorRegistry
        .addInterceptor(getInterceptor());
    // 拦截请求
    interceptorRegistration.addPathPatterns("/**")
        .excludePathPatterns("/swagger-resources/**", "/webjars/**" ,"/v3/**" ,"/swagger-ui.html/**", "/doc.html/**");
  }


  @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
  private String pattern;

  /**
   * 时间处理
   * 使用此方法, 以下 spring-boot: jackson时间格式化 配置 将会失效
   * spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
   * spring.jackson.time-zone=GMT+8
   * 原因: 会覆盖 @EnableAutoConfiguration 关于 WebMvcAutoConfiguration 的配置
   * */
//  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

    //localDateTime格式化
    JavaTimeModule module = new JavaTimeModule();
    LocalDateTimeDeserializer dateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(pattern));
    LocalDateTimeSerializer dateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    module.addDeserializer(LocalDateTime.class, dateTimeDeserializer);
    module.addSerializer(LocalDateTime.class, dateTimeSerializer);
    ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().modules(module)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();

    //date时间格式化
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setDateFormat(new SimpleDateFormat(pattern));

    // 设置格式化内容
    converter.setObjectMapper(objectMapper);
    converters.add(0, converter);

  }


}
