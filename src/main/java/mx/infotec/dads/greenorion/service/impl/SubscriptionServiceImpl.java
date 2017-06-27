package mx.infotec.dads.greenorion.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.infotec.dads.greenorion.domain.Subscription;
import mx.infotec.dads.greenorion.integration.orion.Condition;
import mx.infotec.dads.greenorion.integration.orion.Entity;
import mx.infotec.dads.greenorion.integration.orion.Http;
import mx.infotec.dads.greenorion.integration.orion.Notification;
import mx.infotec.dads.greenorion.integration.orion.OrionSubscription;
import mx.infotec.dads.greenorion.integration.orion.Subject;
import mx.infotec.dads.greenorion.repository.SubscriptionRepository;
import mx.infotec.dads.greenorion.service.SubscriptionService;

/**
 * Service Implementation for managing Subscription.
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private final Logger log = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

	private final SubscriptionRepository subscriptionRepository;

	@Value("${application.deployment.project-url}")
	private String projectUrl;

	public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	/**
	 * Save a subscription.
	 *
	 * @param subscription
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public Subscription save(Subscription subscription) {
		log.debug("Request to save Subscription : {}", subscription);
		String orionSubscrition = doPost(subscription.getDescription(), subscription.getEntityId(),
				subscription.getEntityType(), subscription.getAttrCondition(), subscription.getOrionUrl());
		subscription.setSubscriptionId(orionSubscrition);
		Subscription result = subscriptionRepository.save(subscription);
		return result;
	}

	/**
	 * Get all the subscriptions.
	 * 
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	public Page<Subscription> findAll(Pageable pageable) {
		log.debug("Request to get all Subscriptions");
		Page<Subscription> result = subscriptionRepository.findAll(pageable);
		return result;
	}

	/**
	 * Get one subscription by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	public Subscription findOne(String id) {
		log.debug("Request to get Subscription : {}", id);
		Subscription subscription = subscriptionRepository.findOne(id);
		return subscription;
	}

	/**
	 * Delete the subscription by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(String id) {
		log.debug("Request to delete Subscription : {}", id);
		Subscription subscription = findOne(id);
		log.info("Request to delete Orion Id Subscription : {}", subscription.getEntityId());
		subscriptionRepository.delete(id);
	}

	public String doPost(String description, String id, String type, String attr, String orionUrl) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			// create request body
			OrionSubscription os = new OrionSubscription();
			os.setDescription(description);
			os.setExpires("2020-04-05T14:00:00.00Z");
			os.setThrottling(1);
			
			
			// create subject
			Subject subject = new Subject();
			// --create entity
			Entity entity = new Entity();
			entity.setId(id);
			entity.setType(type);
			subject.setEntities(Arrays.asList(new Entity[] { entity }));

			// --create condition
			Condition condition = new Condition();
			condition.setAttrs(Arrays.asList(new String[] { "pressures" }));
			subject.setCondition(condition);

			os.setSubject(subject);
			
			// create notification
			Notification notification = new Notification();
			notification.setAttrs(Arrays.asList(new String[] { "id", "type", "CO", "NO2", "O3", "location" }));
			notification.setAdditionalProperty("attrsFormat", "keyValues");
			
			Http http = new Http();
			http.setUrl(projectUrl + "/notifications");
			http.setAdditionalProperty("method", "POST");
			notification.setHttp(http);
			os.setNotification(notification);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			JSONObject request = new JSONObject(os);
			HttpEntity<String> element = new HttpEntity<String>(request.toString(), headers);
			// send request and parse result
			ResponseEntity<String> loginResponse = restTemplate.exchange(orionUrl, HttpMethod.POST, element,
					String.class);
			if (loginResponse.getStatusCode() == HttpStatus.CREATED) {
				List<String> hedersReponse = loginResponse.getHeaders().get("location");
				for (String string : hedersReponse) {
					return string.substring(string.lastIndexOf("/") + 1, string.length());
				}
				return null;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}

	public static void main(String[] args) {
		OrionSubscription os = new OrionSubscription();
		os.setDescription("Description");
		Subject subject = new Subject();
		// add conditions
		Condition condition = new Condition();
		condition.setAttrs(Arrays.asList(new String[] { "pressures" }));
		subject.setCondition(condition);

		Entity entity = new Entity();
		entity.setId("ddfdf");
		entity.setType("type");
		subject.setEntities(Arrays.asList(new Entity[] { entity }));
		JSONObject request = new JSONObject(os);

		System.out.println(request.toString());

	}
}
