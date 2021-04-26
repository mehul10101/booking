package com.uber.booking.repositories;

import com.uber.booking.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
}
