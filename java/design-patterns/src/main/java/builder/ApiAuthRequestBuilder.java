package builder;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ApiAuthRequestBuilder {
	private Optional<Oauth2Authentication> oauth2Authentication;
	private Optional<ApiTokenAuthentication> apiTokenAuthentication;
	private int clientTimeout;
	private TimeUnit timeoutUnit;
	private int retries;
	private Optional<String> requestOrigin;

	public ApiAuthRequestBuilder clientTimeout(int timeout, TimeUnit timeUnit) {
		clientTimeout =  timeout;
		timeoutUnit = timeUnit;

		return this;
	}

	public ApiAuthRequestBuilder retries(int retries) {
		this.retries = retries;
		return this;
	}

	public ApiAuthRequestBuilder oauth2Authentication(Oauth2Authentication oauth2Athentication) {
		this.oauth2Authentication =  Optional.of(oauth2Athentication);
		return this;
	}


	public ApiAuthRequestBuilder apiTokenAuthentication(ApiTokenAuthentication apiTokenAuthentication) {
		this.apiTokenAuthentication =  Optional.of(apiTokenAuthentication);
		return this;
	}

	public ApiAuthRequestBuilder requestOrigin(String origin) {
		this.requestOrigin =  Optional.of(origin);

		return this;
	}

	public ApiAuthRequest build() {
		ApiAuthRequest apiAuthRequest =  new ApiAuthRequest();
		apiAuthRequest.setClientTimeout(clientTimeout);
		apiAuthRequest.setSessionTime(new Date());
		apiAuthRequest.setRetries(retries);
		apiAuthRequest.setTimeoutUnit(timeoutUnit);
		apiAuthRequest.setRequestOrigin(requestOrigin.orElse("US"));


		oauth2Authentication.ifPresent(oauth2Athentication -> apiAuthRequest.setOauth2Authentication(oauth2Athentication));

		apiTokenAuthentication.ifPresent(apiTokenAuthentication -> apiAuthRequest.setApiTokenAuthentication(apiTokenAuthentication));

		return apiAuthRequest;
	}


}
