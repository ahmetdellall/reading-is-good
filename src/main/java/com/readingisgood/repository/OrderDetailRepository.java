package com.readingisgood.repository;

import com.readingisgood.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    Optional<OrderDetail> findOrderDetailByCreatedDateTime(LocalDateTime localDateTime);
    Optional<OrderDetail> findByCustomerId(Long customerId);
}
