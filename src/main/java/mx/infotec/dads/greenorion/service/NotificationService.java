package mx.infotec.dads.greenorion.service;

import mx.infotec.dads.greenorion.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Notification.
 */
public interface NotificationService {

    /**
     * Save a notification.
     *
     * @param notification the entity to save
     * @return the persisted entity
     */
    Notification save(Notification notification);

    /**
     *  Get all the notifications.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Notification> findAll(Pageable pageable);

    /**
     *  Get the "id" notification.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Notification findOne(String id);

    /**
     *  Delete the "id" notification.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
