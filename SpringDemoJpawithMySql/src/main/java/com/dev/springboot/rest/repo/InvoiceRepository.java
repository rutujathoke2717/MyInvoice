package com.dev.springboot.rest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dev.springboot.rest.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	@Modifying
	@Query("UPDATE Invoice SET number=:number WHERE id=:id")
	Integer updateInvoiceNumberById(String number,Long id);
}
