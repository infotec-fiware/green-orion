
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
@JsonPropertyOrder({ "q", "mq", "georel", "geometry", "coords" })
public class Expression {

	@JsonProperty("q")
	private String q;
	@JsonProperty("mq")
	private String mq;
	@JsonProperty("georel")
	private String georel;
	@JsonProperty("geometry")
	private String geometry;
	@JsonProperty("coords")
	private String coords;

	@JsonProperty("q")
	public String getQ() {
		return q;
	}

	@JsonProperty("q")
	public void setQ(String q) {
		this.q = q;
	}

	@JsonProperty("mq")
	public String getMq() {
		return mq;
	}

	@JsonProperty("mq")
	public void setMq(String mq) {
		this.mq = mq;
	}

	@JsonProperty("georel")
	public String getGeorel() {
		return georel;
	}

	@JsonProperty("georel")
	public void setGeorel(String georel) {
		this.georel = georel;
	}

	@JsonProperty("geometry")
	public String getGeometry() {
		return geometry;
	}

	@JsonProperty("geometry")
	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	@JsonProperty("coords")
	public String getCoords() {
		return coords;
	}

	@JsonProperty("coords")
	public void setCoords(String coords) {
		this.coords = coords;
	}

}
