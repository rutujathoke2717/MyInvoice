package com.dev.springboot.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.dev.springboot.rest.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.dev.springboot.rest.entity.Invoice;
import com.dev.springboot.rest.exception.InvoiceNotFoundException;

import com.dev.springboot.rest.util.InvoiceUtil;

@RestController
@RequestMapping("/api")
public class InvoiceController {
	
	@Autowired(required=true)
	private InvoiceService service;  
	
	@Autowired
	private InvoiceUtil util;
	
	@PostMapping("/invoices")
	public ResponseEntity<String> saveInvoice(@RequestBody Invoice inv){
		ResponseEntity<String> resp = null;
		try{
			Long id = service.saveInvoice(inv);
			resp= new ResponseEntity<String>(
					"Invoice '"+id+"' created",HttpStatus.CREATED); //201-created
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>(
					"Unable to save Invoice", 
					HttpStatus.INTERNAL_SERVER_ERROR); //500-Internal Server Error
		}
		return resp;
	}
	
	
	@GetMapping("/invoices")
	public ResponseEntity<?> getAllInvoices() {
		ResponseEntity<?> resp=null;
		try {
			List<Invoice> list= service.getAllInvoice();
			resp= new ResponseEntity<List<Invoice>>(list,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>(
					"Unable to get Invoice", 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
	
	@GetMapping("/invoices/{id}")
	public ResponseEntity<?> getOneInvoice(@PathVariable Long id){
		ResponseEntity<?> resp= null;
		try {
			Invoice inv= service.getInvoiceById(id);
			resp= new ResponseEntity<Invoice>(inv,HttpStatus.OK);
		}catch (InvoiceNotFoundException nfe) {
			throw nfe; 
		}catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>(
					"Unable to find Invoice", 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
	
	
	@DeleteMapping("/invoices/{id}")
	public ResponseEntity<String> deleteInvoice(@PathVariable Long id){
		
		ResponseEntity<String> resp= null;
		try {
			service.deleteInvoice(id);
			resp= new ResponseEntity<String> (
					"Invoice '"+id+"' deleted",HttpStatus.OK);
			
		} catch (InvoiceNotFoundException nfe) {
			throw nfe;
		} catch (Exception e) {
			e.printStackTrace();
			resp= new ResponseEntity<String>(
					"Unable to delete Invoice", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return resp;
		
	}
	
	@PutMapping("/invoices/{id}")
	public ResponseEntity<String> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice){
		
		ResponseEntity<String> resp = null;
		try {
			//db Object
			Invoice inv= service.getInvoiceById(id);
			//copy non-null values from request to Database object
			util.copyNonNullValues(invoice, inv);
			//finally update this object
			service.updateInvoice(inv);
			resp = new ResponseEntity<String>(
					//"Invoice '"+id+"' Updated",
					HttpStatus.RESET_CONTENT); //205- Reset-Content(PUT)
			
		} catch (InvoiceNotFoundException nfe) {
			throw nfe; // re-throw exception to handler
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>(
					"Unable to Update Invoice", 
					HttpStatus.INTERNAL_SERVER_ERROR); //500-ISE
		}
		return resp;
	}
	@PatchMapping("/invoices/{id}/{number}")
	public ResponseEntity<String> updateInvoiceNumberById(
			@PathVariable Long id,
			@PathVariable String number
			) 
	{
		ResponseEntity<String> resp = null;
		try {
			service.updateInvoiceNumberById(number, id);
			resp = new ResponseEntity<String>(
					"Invoice '"+number+"' Updated",
					HttpStatus.PARTIAL_CONTENT); //206- Reset-Content(PUT)
			
		} catch(InvoiceNotFoundException pne) {
			throw pne; // re-throw exception to handler
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>(
					"Unable to Update Invoice", 
					HttpStatus.INTERNAL_SERVER_ERROR); //500-ISE
		}
		return resp;
	}
}

		
		
		 	
