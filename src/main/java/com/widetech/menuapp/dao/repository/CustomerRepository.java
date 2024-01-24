package com.widetech.menuapp.dao.repository;

import com.widetech.menuapp.dao.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
