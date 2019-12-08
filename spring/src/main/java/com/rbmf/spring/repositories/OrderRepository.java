package com.rbmf.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rbmf.spring.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	
}
