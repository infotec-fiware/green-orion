package mx.infotec.dads.greenorion.service.util;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * SubscriptionBuilder
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class Subscription {

	private String description;
	HashMap<String, Object> subject = new LinkedHashMap<>();
	HashMap<String, Object> entities = new LinkedHashMap<>();
	HashMap<String, Object> condition = new LinkedHashMap<>();
	HashMap<String, Object> notification = new LinkedHashMap<>();
	HashMap<String, Object> http = new LinkedHashMap<>();
	private String expires;
	private String throttling;
}
