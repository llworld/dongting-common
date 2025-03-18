package online.dongting.config;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author ll
 */ //@Profile("!prod")
@Configuration // 标明是配置类
@EnableOpenApi //开启swagger功能
@EnableKnife4j
public class SwaggerConfig {




    @Bean
    public Docket createRestApi( ) {

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo()) // 用于生成API信息
                .select() // select()函数返回一个ApiSelectorBuilder实例,用来控制接口被swagger做成文档
                .apis(RequestHandlerSelectors.basePackage("com.ll")) // 用于指定扫描哪个包下的接口
                .build();
    }

    /**
     * 用于定义API主界面的信息，比如可以声明所有的API的总标题、描述、版本
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("骑行俱乐部项目API") //  可以用来自定义API的主标题
                .description("骑行俱乐部项目SwaggerAPI管理") // 可以用来描述整体的API
//                .termsOfServiceUrl("") // 用于定义服务的域名
                .version("1.0") // 可以用来定义版本。
                .build(); //
    }
}

