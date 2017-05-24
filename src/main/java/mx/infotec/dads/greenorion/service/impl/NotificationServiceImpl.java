package mx.infotec.dads.greenorion.service.impl;

import mx.infotec.dads.greenorion.service.NotificationService;
import mx.infotec.dads.greenorion.domain.Notification;
import mx.infotec.dads.greenorion.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Notification.
 */
@Service
public class NotificationServiceImpl implements NotificationService{

    private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
    
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Save a notification.
     *
     * @param notification the entity to save
     * @return the persisted entity
     */
    @Override
    public Notification save(Notification notification) {
        log.debug("Request to save Notification : {}", notification);
        Notification result = notificationRepository.save(notification);
        return result;
    }

    /**
     *  Get all the notifications.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Notification> findAll(Pageable pageable) {
        log.debug("Request to get all Notifications");
        Page<Notification> result = notificationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one notification by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Notification findOne(String id) {
        log.debug("Request to get Notification : {}", id);
        Notification notification = notificationRepository.findOne(id);
        return notification;
    }

    /**
     *  Delete the  notification by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Notification : {}", id);
        notificationRepository.delete(id);
    }
}
