
package mx.infotec.dads.greenorion.integration.orion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "http", "attrs" })
public class Notification {

	@JsonProperty("http")
	private Http http;
	@JsonProperty("attrs")
	private List<String> attrs = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Notification() {
	}

	public Notification(List<String> attrs) {
		this.attrs = attrs;
	}

	@JsonProperty("http")
	public Http getHttp() {
		return http;
	}

	@JsonProperty("http")
	public void setHttp(Http http) {
		this.http = http;
	}

	@JsonProperty("attrs")
	public List<String> getAttrs() {
		return attrs;
	}

	@JsonProperty("attrs")
	public void setAttrs(List<String> attrs) {
		this.attrs = attrs;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
