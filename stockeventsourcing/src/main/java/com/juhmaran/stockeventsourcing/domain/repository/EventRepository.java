package com.juhmaran.stockeventsourcing.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by Juliane Maran
 *
 * @since 13/07/2025
 */
@Repository
public interface EventRepository extends JpaRepository<EventStore, UUID> {

  List<EventStore> findByAggregateIdOrderByTimestampAsc(String aggregateId);

}
