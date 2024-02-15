package com.widetech.menuapp.config.swagger;

/**
 * Author: athen
 * Date: 2/13/2024
 * Description:
 */
//@Configuration
////@EnableOpenApi
//public class SwaggerConfig extends WebMvcConfigurationSupport {
//
//    //swagger 3.0.0
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("API of Menu order app")
//                        .version("1.0")
//                        .description("API for the menu app for a restaurant")
//                        .contact(new Contact().name("Dian").email("athenting@outlook.com"))
//                );
//    }

    //swagger 2.X
//    @Bean
//    public Docket createRestApi()
//    {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("")
//                .select()
//
//                // 扫描方式1.指定当前包路径
//                .apis(RequestHandlerSelectors.basePackage("com.widetech.menuapp.controller"))
//                // 扫描方式2：扫描所有有类注解的api
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                // 扫描方式3：扫描所有有方法注解的api，用这种方式更灵活
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                // 扫描方式2：扫描所有
//                //.apis(RequestHandlerSelectors.any())
//
//                .paths(PathSelectors.any())
//                .build();
//    }

    /**
     * 解决swagger-ui.html 404无法访问的问题
     */
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // 解决静态资源无法访问
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/static/");
//        // 解决swagger无法访问
//        registry.addResourceHandler("/swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        // 解决swagger的js文件无法访问
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }


//}
