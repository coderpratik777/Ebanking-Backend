package com.pratiti.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pratiti.entity.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer> {
	
	@Query("select b from Beneficiary b where b.customerBeneficiary.customerId=?1")
	List<Beneficiary> findByCustomerId(int id);

}
