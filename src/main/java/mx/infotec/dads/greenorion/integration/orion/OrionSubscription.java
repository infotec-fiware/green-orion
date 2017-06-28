
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
@JsonPropertyOrder({ "id", "description", "subject", "notification", "expires", "status", "throttling" })
public class OrionSubscription {

	@JsonProperty("id")
	private String id;
	@JsonProperty("description")
	private String description;
	@JsonProperty("subject")
	private Subject subject;
	@JsonProperty("notification")
	private Notification notification;
	@JsonProperty("expires")
	private String expires;
	@JsonProperty("status")
	private String status;
	@JsonProperty("throttling")
	private Integer throttling;

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("subject")
	public Subject getSubject() {
		return subject;
	}

	@JsonProperty("subject")
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@JsonProperty("notification")
	public Notification getNotification() {
		return notification;
	}

	@JsonProperty("notification")
	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	@JsonProperty("expires")
	public String getExpires() {
		return expires;
	}

	@JsonProperty("expires")
	public void setExpires(String expires) {
		this.expires = expires;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("throttling")
	public Integer getThrottling() {
		return throttling;
	}

	@JsonProperty("throttling")
	public void setThrottling(Integer throttling) {
		this.throttling = throttling;
	}
}
