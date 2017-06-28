
package mx.infotec.dads.greenorion.integration.orion;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "url", "headers", "qs", "method", "payload" })
public class HttpCustom {

	@JsonProperty("url")
	private String url;
	@JsonProperty("headers")
	private String headers;
	@JsonProperty("qs")
	private String qs;
	@JsonProperty("method")
	private String method;
	@JsonProperty("payload")
	private String payload;

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	@JsonProperty("url")
	public void setUrl(String url) {
		this.url = url;
	}

	@JsonProperty("headers")
	public String getHeaders() {
		return headers;
	}

	@JsonProperty("headers")
	public void setHeaders(String headers) {
		this.headers = headers;
	}

	@JsonProperty("qs")
	public String getQs() {
		return qs;
	}

	@JsonProperty("qs")
	public void setQs(String qs) {
		this.qs = qs;
	}

	@JsonProperty("method")
	public String getMethod() {
		return method;
	}

	@JsonProperty("method")
	public void setMethod(String method) {
		this.method = method;
	}

	@JsonProperty("payload")
	public String getPayload() {
		return payload;
	}

	@JsonProperty("payload")
	public void setPayload(String payload) {
		this.payload = payload;
	}
}
