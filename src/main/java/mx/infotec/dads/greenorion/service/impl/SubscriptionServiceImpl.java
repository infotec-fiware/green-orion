package mx.infotec.dads.greenorion.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Service Implementation for managing Subscription.
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final Logger log = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

    private final SubscriptionRepository subscriptionRepository;

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

    public static String doPost(String description, String id, String type, String attr, String orionUrl) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            // create request body
            JSONObject request = new JSONObject();
            // description
            request.put("description", description);
            // subject
            HashMap<String, Object> subject = new LinkedHashMap<>();
            HashMap<String, Object> entityElement = new LinkedHashMap<>();
            entityElement.put("id", id);
            entityElement.put("type", type);
            ArrayList<HashMap<String, Object>> entities = new ArrayList<>();
            entities.add(entityElement);
            subject.put("entities", entities);
            HashMap<String, Object> condition = new LinkedHashMap<>();
            condition.put("attrs", new String[] { attr });
            subject.put("condition", condition);
            request.put("subject", subject);
            // notification
            HashMap<String, Object> notification = new LinkedHashMap<>();
            HashMap<String, Object> http = new LinkedHashMap<>();
            http.put("url", "http://200.38.177.199:8080/notifications");
            http.put("method", "POST");
            notification.put("http", http);
            notification.put("attrsFormat", "keyValues");
            notification.put("attrs", new String[] { "id", "type", "CO", "NO2", "O3", "location" });
            request.put("notification", notification);
            // expires
            request.put("expires", "2020-04-05T14:00:00.00Z");
            // throttling
            request.put("throttling", 1);
            // // set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
            //
            // send request and parse result
            ResponseEntity<String> loginResponse = restTemplate.exchange(orionUrl, HttpMethod.POST, entity,
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
}
