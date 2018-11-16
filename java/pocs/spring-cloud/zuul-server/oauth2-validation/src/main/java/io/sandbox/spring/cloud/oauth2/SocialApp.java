package io.sandbox.spring.cloud.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.security.Principal;

@SpringCloudApplication
@RestController
@ComponentScan("io.sandbox.spring.cloud.oauth2")
@EnableOAuth2Sso
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SocialApp  extends WebSecurityConfigurerAdapter {

	@Autowired
	private ResourceServerProperties resourceServerProperties;

	@Autowired
	private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;


	// TODO clientId and clientSecret to be defined at application.yml
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}

	public static void main(String[] args) {
		SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder(SocialApp.class)
				.profiles("default")
				.properties("management.contextPath:/management")
				.properties("management.info.git.mode:full")
				.properties("server.port:8080")
				.properties("logging.level.de.codecentric.boot.admin.services.ApplicationRegistrator:ERROR");

		applicationBuilder.run(args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**")
				.authorizeRequests()
					.antMatchers( "/login**", "/webjars/**", "/error**")
						.permitAll()
					.anyRequest()
						.authenticated()
				.and()
					.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
					.logout()
					.logoutSuccessUrl("/")
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).invalidateHttpSession(true)
					.deleteCookies("XSRF-TOKEN", "JSESSIONID", "auth_code")
					.clearAuthentication(true);
	}

	@Bean
	@Primary
	public UserInfoTokenServices userInfoTokenServices() {
		UserInfoTokenServices userInfoTokenServices  = new UserInfoTokenServices(resourceServerProperties.getUserInfoUri(), resourceServerProperties.getClientId());
		userInfoTokenServices.setRestTemplate(new OAuth2RestTemplate(oAuth2ProtectedResourceDetails));
		userInfoTokenServices.setTokenType(resourceServerProperties.getTokenType());

		return userInfoTokenServices;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:7000");
			}
		};
	}


}
