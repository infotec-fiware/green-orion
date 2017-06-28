package mx.infotec.dads.greenorion.service.impl;

import java.util.List;

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

import mx.infotec.dads.greenorion.domain.Subscription;
import mx.infotec.dads.greenorion.repository.SubscriptionRepository;
import mx.infotec.dads.greenorion.service.SubscriptionService;
import mx.infotec.dads.greenorion.util.OrionSubscriptionBuilder;

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
			String t = OrionSubscriptionBuilder.builder()
					.addDescription(description)
					.addExpires("2020-04-05T14:00:00.00Z")
					.addThrottling(1)
					.addEntities(id, type)
					.addCondition("pressures")
					.addReturnedAttribute("id")
					.addReturnedAttribute("type")
					.addReturnedAttribute("CO")
					.addReturnedAttribute("NO2")
					.addReturnedAttribute("O3")
					.addReturnedAttribute("location").addReturnedAttributesFormat("keyValues")
					.addUrl(projectUrl + "/notifications")
					.addMethodInvocation("POST").createJson();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<String> element = new HttpEntity<String>(t, headers);
			ResponseEntity<String> loginResponse = restTemplate.exchange("http://green.mx:1026/v2/subscriptions",
					HttpMethod.POST, element, String.class);
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
			throw new RuntimeException("Comunication Error", e);
		}

	}

	public static void main(String[] args) {
		String t = OrionSubscriptionBuilder.builder()
				.addDescription("description")
				.addExpires("2020-04-05T14:00:00.00Z")
				.addThrottling(1)
				.addEntities("hola", "type")
				.addCondition("pressures")
				.addReturnedAttribute("id")
				.addReturnedAttribute("type")
				.addReturnedAttribute("CO")
				.addReturnedAttribute("NO2")
				.addReturnedAttribute("O3")
				.addReturnedAttribute("location").addReturnedAttributesFormat("keyValues")
				.addUrl("url" + "/notifications")
				.addMethodInvocation("POST").createJson();
		System.out.println(t);

	}
}
