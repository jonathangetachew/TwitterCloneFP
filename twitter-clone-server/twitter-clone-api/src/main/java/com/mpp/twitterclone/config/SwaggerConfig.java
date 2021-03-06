package com.mpp.twitterclone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * Created by Jonathan on 9/12/2019.
 */

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.apiInfo(metaData());
	}

	private ApiInfo metaData() {
		Contact contact = new Contact("Jonathan Tuta, Yadir Hernandez Batista, " +
				"Edgar de Jesus Endo Junior, " +
				"Akjol Syeryekkhaan, " +
				"Davaabayar Battogtokh", "https://www.cs.mum.edu/mum/", "");

		return new ApiInfo(
				"TwitterClone",
				"Twitter Clone Platform, Implemented Using Functional Programming Paradigm, for our MPP Project 2",
				"1.0",
				"Terms of Service: Use it however you like, just don't sue us. :)",
				contact,
				"MIT License",
				"https://opensource.org/licenses/MIT",
				new ArrayList<>());

	}
}
