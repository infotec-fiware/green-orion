
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
@JsonPropertyOrder({ "id", "idPattern", "type", "typePattern" })
public class Entity {

	@JsonProperty("id")
	private String id;
	@JsonProperty("idPattern")
	private String idPattern;
	@JsonProperty("type")
	private String type;
	@JsonProperty("typePattern")
	private String typePattern;

	public Entity() {
	}

	public Entity(String id, String type) {
		this.id = id;
		this.type = type;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("idPattern")
	public String getIdPattern() {
		return idPattern;
	}

	@JsonProperty("idPattern")
	public void setIdPattern(String idPattern) {
		this.idPattern = idPattern;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("typePattern")
	public String getTypePattern() {
		return typePattern;
	}

	@JsonProperty("typePattern")
	public void setTypePattern(String typePattern) {
		this.typePattern = typePattern;
	}
}
