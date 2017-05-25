package mx.infotec.dads.greenorion.repository;

import mx.infotec.dads.greenorion.domain.Subscription;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Subscription entity.
 */
@SuppressWarnings("unused")
public interface SubscriptionRepository extends MongoRepository<Subscription,String> {

}
