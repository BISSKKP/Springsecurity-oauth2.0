package com.base.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//swagger2的配置文件，在项目的启动类的同级文件建立

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * http://127.0.0.1:8061/ticket/swagger-ui.html  UI界面
 * http://127.0.0.1:8061/ticket/swagger 文档
 * http://127.0.0.1:8061/ticket/doc.html
 * @author howard
 */
@Configuration
@EnableSwagger2
//@Profile({"test","dev"})
@EnableSwaggerBootstrapUI
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2 implements WebMvcConfigurer  {
	
	
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//开发模式下能执行swagger 
		//在未使用美化插件时请打开次配置
//		registry.addResourceHandler("/ticket/swagger/**").addResourceLocations("classpath:/swagger/dist/");
//		registry.addResourceHandler("/swagger/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		//将旧版swagger 导向美化包界面
		registry.addViewController("/swagger-ui.html").setViewName("/doc.html");
		
	}
	
	
	//配置content type
		private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
				new HashSet<String>(Arrays.asList("application/x-www-form-urlencoded","application/json","application/xml","multipart/form-data"));
	
//swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
  @Bean
  public Docket createRestApi() {
	  
//	  ParameterBuilder ticketPar = new ParameterBuilder();
//      List<Parameter> pars = new ArrayList<Parameter>();
//      ticketPar.name("Token").description("登录秘钥")//Token 以及Authorization 为自定义的参数，session保存的名字是哪个就可以写成那个
//              .modelRef(new ModelRef("string")).parameterType("header")
//              .required(false).build(); //header中的ticket参数非必填，传空也可以
//      pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设
//	  
	  
//	  List<ResponseMessage> responseMessageList = new ArrayList<>();
//	  responseMessageList.add(new ResponseMessageBuilder().code(404).message("找不到资源").responseModel(new ModelRef("key:100")).build());
    
	  
	  
	  return new Docket(DocumentationType.SWAGGER_2)
//    		  .globalResponseMessage(RequestMethod.GET, responseMessageList)
//              .globalResponseMessage(RequestMethod.POST, responseMessageList)
    		  
              .apiInfo(apiInfo())
              .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
              .select()  
              //为当前包路径
              .apis(RequestHandlerSelectors.basePackage("com.base"))
              .paths(PathSelectors.any())
              .build()
//              .globalOperationParameters(pars) //可以在每一个api接口 上拼接要求输入token
              .securitySchemes(unifiedAuth())
              .securityContexts(securityContexts());// 升级成为2.9.2时需要此配置
      
     
  }
  
  private static List<ApiKey> unifiedAuth() {
      List<ApiKey> arrayList = new ArrayList<>();
      arrayList.add(new ApiKey("Authorization", "Authorization", "header"));
     // arrayList.add(new ApiKey("app-token", "app-token", "header"));
      return arrayList;
  }
  
  
  //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
  private ApiInfo apiInfo() {
      return new ApiInfoBuilder()
              //页面标题
              .title("资源服务器2  API")
              //创建人
              .contact(new Contact("liuhz", "http://www.vsupa.com", ""))
              //版本号
              .version("1.0")
              //描述
              .description("API 描述---O(∩_∩)O~")
              .build();
  }
  
  private List<SecurityContext> securityContexts() {
      List<SecurityContext> securityContexts = new ArrayList<>();
      securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth())
              //过滤要验证的路径
              .forPaths(PathSelectors.regex("^(?!auth).*$"))
              .build());
      return securityContexts;
  }
  
//增加全局认证
  List<SecurityReference> defaultAuth() {
      AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
      AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
      authorizationScopes[0] = authorizationScope;
      List<SecurityReference> securityReferences = new ArrayList<>();
      //验证增加（有许多教程说明中这个地方是Authorization,导致不能带入全局token，因为securitySchemes()方法中header写入token，所以这个地方我改为token就可以了）
      securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
      return securityReferences;
  }



}