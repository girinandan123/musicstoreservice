package com.cx.uioc.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfiguration Class - Configuration for Swagger API documentations
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	/**
	 * Create the docket for our API documentations
	 */
	@Bean
	public Docket uiocApi() {
		final List<ResponseMessage> responseMessageList = new ArrayList<ResponseMessage>();
	    responseMessageList.add(new ResponseMessageBuilder().code(500).message("Internal server error.").responseModel(new ModelRef("Error")).build());
	    responseMessageList.add(new ResponseMessageBuilder().code(403).message("Forbidden.").build());

	    return new Docket(DocumentationType.SWAGGER_2)
	    		.select()
	    		.apis(RequestHandlerSelectors.basePackage("com.cx.uioc.controller"))
	    		.paths(PathSelectors.any())
	    		.build()
	    		.apiInfo(apiInfo())
	    		.useDefaultResponseMessages(false)
	    		.globalResponseMessage(RequestMethod.GET, responseMessageList);
	}
	
	private ApiInfo apiInfo() {
	    final Contact contact = new Contact("Chuan Sibelius", "", "");
	    final ApiInfo apiInfo = new ApiInfo("UIOC API version 2.0", "UIOC REST APIs for Online Order/Purchase System", "ver2.0", "", contact,
	        "", "");
	    return apiInfo;
	  }
}
