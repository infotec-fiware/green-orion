
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
@JsonPropertyOrder({ "entities", "condition" })
public class Subject {

	@JsonProperty("entities")
	private List<Entity> entities = null;
	@JsonProperty("condition")
	private Condition condition;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Subject() {

	}

	public Subject(List<Entity> entities) {
		this.setEntities(entities);
	}

	@JsonProperty("entities")
	public List<Entity> getEntities() {
		return entities;
	}

	@JsonProperty("entities")
	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	@JsonProperty("condition")
	public Condition getCondition() {
		return condition;
	}

	@JsonProperty("condition")
	public void setCondition(Condition condition) {
		this.condition = condition;
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
