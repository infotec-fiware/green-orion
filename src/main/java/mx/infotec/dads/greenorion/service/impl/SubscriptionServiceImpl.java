package mx.infotec.dads.greenorion.service.impl;

import mx.infotec.dads.greenorion.service.SubscriptionService;
import mx.infotec.dads.greenorion.domain.Subscription;
import mx.infotec.dads.greenorion.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Subscription.
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService{

    private final Logger log = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
    
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Save a subscription.
     *
     * @param subscription the entity to save
     * @return the persisted entity
     */
    @Override
    public Subscription save(Subscription subscription) {
        log.debug("Request to save Subscription : {}", subscription);
        Subscription result = subscriptionRepository.save(subscription);
        return result;
    }

    /**
     *  Get all the subscriptions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Subscription> findAll(Pageable pageable) {
        log.debug("Request to get all Subscriptions");
        Page<Subscription> result = subscriptionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one subscription by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Subscription findOne(String id) {
        log.debug("Request to get Subscription : {}", id);
        Subscription subscription = subscriptionRepository.findOne(id);
        return subscription;
    }

    /**
     *  Delete the  subscription by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Subscription : {}", id);
        subscriptionRepository.delete(id);
    }
}
