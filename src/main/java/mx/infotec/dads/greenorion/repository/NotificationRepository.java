package mx.infotec.dads.greenorion.repository;

import mx.infotec.dads.greenorion.domain.Notification;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Notification entity.
 */
@SuppressWarnings("unused")
public interface NotificationRepository extends MongoRepository<Notification,String> {

}
