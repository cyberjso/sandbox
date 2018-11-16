package io.sandbox.spring.cloud.zuul.hello;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringCloudApplication
@ComponentScan("io.sandbox.spring.cloud.zuul.hello")
@EnableZuulProxy
@RestController

public class App {

	public static void main(String[] args) {
		SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder(App.class)
				.profiles("default")
				.properties("management.contextPath:/management")
				.properties("management.info.git.mode:full")
				.properties("server.port:8080")
				.properties("logging.level.de.codecentric.boot.admin.services.ApplicationRegistrator:ERROR");

		applicationBuilder.run(args);
	}

}
