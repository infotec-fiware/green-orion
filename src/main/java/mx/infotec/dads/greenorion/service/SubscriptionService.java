package mx.infotec.dads.greenorion.service;

import mx.infotec.dads.greenorion.domain.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Subscription.
 */
public interface SubscriptionService {

    /**
     * Save a subscription.
     *
     * @param subscription the entity to save
     * @return the persisted entity
     */
    Subscription save(Subscription subscription);

    /**
     *  Get all the subscriptions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Subscription> findAll(Pageable pageable);

    /**
     *  Get the "id" subscription.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Subscription findOne(String id);

    /**
     *  Delete the "id" subscription.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
