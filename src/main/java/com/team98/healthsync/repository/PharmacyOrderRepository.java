package com.team98.healthsync.repository;

import com.team98.healthsync.enums.OrderState;
import com.team98.healthsync.models.PharmacyOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyOrderRepository  extends CrudRepository<PharmacyOrder,Long> {

    Iterable<PharmacyOrder> findAllByStateOrderByDateTimeDesc(OrderState state);
}
