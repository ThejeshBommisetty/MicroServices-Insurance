package com.mc;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mc.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
