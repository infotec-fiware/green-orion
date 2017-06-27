
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
@JsonPropertyOrder({ "attrs" })
public class Condition {

	@JsonProperty("attrs")
	private List<String> attrs = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Condition() {

	}

	public Condition(List<String> attrs) {
		this.attrs = attrs;
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
