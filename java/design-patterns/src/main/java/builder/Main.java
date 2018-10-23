package builder;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Main {


	public static void main(String[] args) {

		// Building auth request for basic authentication
		new ApiAuthRequestBuilder()
				.clientTimeout(3000, TimeUnit.SECONDS)
				.retries(4)
				.apiTokenAuthentication(new ApiTokenAuthentication("myUser", "myPassword"))
				.build();


		// Building auth request for oauth2 authentication
		Oauth2Authentication oauth2Athentication =  new Oauth2Authentication();
		oauth2Athentication.setClientId("myClientID");
		oauth2Athentication.setPassword("myPAss");
		oauth2Athentication.setRedirectUrl("http://www.google.com");
		oauth2Athentication.setUser("myUser");

		new ApiAuthRequestBuilder()
				.clientTimeout(5000, TimeUnit.SECONDS)
				.retries(3)
				.requestOrigin("BR")
				.oauth2Authentication(oauth2Athentication)
				.build();



	}
}
