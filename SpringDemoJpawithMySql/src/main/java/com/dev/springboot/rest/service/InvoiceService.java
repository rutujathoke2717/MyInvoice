package com.dev.springboot.rest.service;


import java.util.List;
import com.dev.springboot.rest.entity.Invoice;

public interface InvoiceService {
	
	Long saveInvoice(Invoice inv);
	
	void updateInvoice(Invoice e);
	
	void deleteInvoice(Long id);
	
	Invoice getInvoiceById(Long id);
	
	List<Invoice> getAllInvoice();
	
	boolean isInvoiceExist(Long id);
	
	Integer updateInvoiceNumberById(String number,Long id);

	

	

}
