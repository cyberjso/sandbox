package builder;

// That might be a good example of why we need a builder: https://nordicapis.com/3-common-methods-api-authentication-explained/
// We may need a more complex data structure to represent the idea

public class ApiTokenAuthentication {
	private String token;
	private String apiId;

	public ApiTokenAuthentication(String token, String apiId) {
		this.token = token;
		this.apiId = apiId;
	}

	public ApiTokenAuthentication() { }

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
