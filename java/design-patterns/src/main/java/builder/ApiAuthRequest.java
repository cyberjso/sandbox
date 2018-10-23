package builder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ApiAuthRequest {
	private ApiTokenAuthentication apiTokenAuthentication;
	private Oauth2Authentication oauth2Authentication;
	private int clientTimeout;
	private TimeUnit timeoutUnit;
	private int retries;
	private Date sessionTime;
	private String requestOrigin;

	public ApiTokenAuthentication getApiTokenAuthentication() {
		return apiTokenAuthentication;
	}

	public void setApiTokenAuthentication(ApiTokenAuthentication apiTokenAuthentication) {
		this.apiTokenAuthentication = apiTokenAuthentication;
	}

	public Oauth2Authentication getOauth2Authentication() {
		return oauth2Authentication;
	}

	public void setOauth2Authentication(Oauth2Authentication oauth2Authentication) {
		this.oauth2Authentication = oauth2Authentication;
	}

	public int getClientTimeout() {
		return clientTimeout;
	}

	public void setClientTimeout(int clientTimeout) {
		this.clientTimeout = clientTimeout;
	}

	public void setTimeoutUnit(TimeUnit timeoutUnit) {
		this.timeoutUnit = timeoutUnit;
	}

	public TimeUnit getTimeoutUnit() {
		return timeoutUnit;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public void setSessionTime(Date sessionTime) {
		this.sessionTime = sessionTime;
	}

	public Date getSessionTime() {
		return sessionTime;
	}

	public void setRequestOrigin(String requestOrigin) {
		this.requestOrigin = requestOrigin;
	}

	public String getRequestOrigin() {
		return requestOrigin;
	}
}
