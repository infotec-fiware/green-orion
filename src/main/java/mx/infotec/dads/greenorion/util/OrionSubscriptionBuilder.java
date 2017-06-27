package mx.infotec.dads.greenorion.util;

import java.util.ArrayList;

import org.json.JSONObject;

import mx.infotec.dads.greenorion.integration.orion.Condition;
import mx.infotec.dads.greenorion.integration.orion.Entity;
import mx.infotec.dads.greenorion.integration.orion.Notification;
import mx.infotec.dads.greenorion.integration.orion.OrionSubscription;
import mx.infotec.dads.greenorion.integration.orion.Subject;

/**
 * OrionSubscriptionBuilder
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class OrionSubscriptionBuilder {
	private OrionSubscription os;

	private OrionSubscriptionBuilder(OrionSubscription os) {
		this.os = os;
	}

	public static OrionSubscriptionBuilder createBuilder() {
		OrionSubscription os = new OrionSubscription();
		Subject subject = new Subject();
		subject.setEntities(new ArrayList<>());
		subject.setCondition(new Condition(new ArrayList<>()));
		os.setSubject(subject);
		os.setNotification(new Notification(new ArrayList<>()));
		return new OrionSubscriptionBuilder(os);
	}

	public OrionSubscriptionBuilder addDescription(String description) {
		os.setDescription(description);
		return this;
	}

	public OrionSubscriptionBuilder addExpires(String expires) {
		os.setExpires(expires);
		return this;
	}

	public OrionSubscriptionBuilder addCondition(String condition) {
		os.getSubject().getCondition().getAttrs().add(condition);
		return this;
	}

	public OrionSubscriptionBuilder addEntities(String id, String type) {
		os.getSubject().getEntities().add(new Entity(id, type));
		return this;
	}

	public OrionSubscriptionBuilder addUrl(String url) {
		os.getNotification().getHttp().setUrl(url);
		return this;
	}

	public OrionSubscriptionBuilder addMethodInvocation(String method) {
		os.getNotification().getHttp().getAdditionalProperties().put("method", method);
		return this;
	}

	public OrionSubscriptionBuilder addReturnedAttribute(String attrs) {
		os.getNotification().getAttrs().add(attrs);
		return this;
	}

	public OrionSubscriptionBuilder addReturnedAttributesFormat(String attrsFormat) {
		os.getNotification().getAdditionalProperties().put("attrsFormat", attrsFormat);
		return this;
	}

	public OrionSubscriptionBuilder addThrottling(int throttling) {
		os.setThrottling(throttling);
		return this;
	}

	public JSONObject createJsonObject() {
		return new JSONObject(this.os);
	}

	public static void main(String[] args) {
		JSONObject t = OrionSubscriptionBuilder.createBuilder().addDescription("description").addCondition("df")
				.addCondition("newCondition").addEntities("2345", "type").addReturnedAttribute("ff").createJsonObject();
		System.out.println(t.toString());
	}
}
