package io.sandbox.spring.cloud.oauth2;

import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/api/v2")
public class MyService {

	@GetMapping(path = "/myservice/{name}", produces= MediaType.APPLICATION_JSON_VALUE)
	public String helloMyService(@PathVariable(name = "name") String name)	{
		return "{ \"name\": \"" + name + "\", \"at\": \"" + new Date() + "\"}";
	}
}
