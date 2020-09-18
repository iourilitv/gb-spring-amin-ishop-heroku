package ru.geekbrains.spring.ishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.ishop.entity.Event;

import java.util.Optional;

@Repository
public interface EventRepository extends
        JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    Optional<Event> findFirstByServerAcceptedAtIsNull();
//    Optional<Event> findFirstByRecipientAcceptedAtIsNullAndIssuerEquals(String issuer);

}
