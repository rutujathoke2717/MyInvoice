package com.dev.springboot.rest.serviceImpl;

import java.util.List;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.springboot.rest.entity.Invoice;
import com.dev.springboot.rest.exception.InvoiceNotFoundException;
import com.dev.springboot.rest.repo.InvoiceRepository;
import com.dev.springboot.rest.service.InvoiceService;
import com.dev.springboot.rest.util.InvoiceUtil;

@Service
public class InvoiceServiceImpl implements InvoiceService{
	
	@Autowired
	private InvoiceRepository repo;

	@Autowired
	private InvoiceUtil util;

	
	@Override
	public Long saveInvoice(Invoice inv) {
		util.CalculateFinalAmountIncludingGST(inv);
		Long id = repo.save(inv).getId();
		return id;
	}

	@Override
	public void updateInvoice(Invoice inv) {
		util.CalculateFinalAmountIncludingGST(inv);
		repo.save(inv);
	}

	@Override
	public void deleteInvoice(Long id) {
		Invoice inv= getInvoiceById(id);
		repo.delete(inv);
	}
	
	public Optional<Invoice> getSingleInvoice(Long Id) {
		return repo.findById(Id);
	}



	@Override
	public List<Invoice> getAllInvoice() {
		List<Invoice> list = repo.findAll();
		list.sort((ob1,ob2)->ob1.getId().intValue()-ob2.getId().intValue());
		return list;
	}

	@Override
	public boolean isInvoiceExist(Long id) {		
		return repo.existsById(id);
	
	}

	@Override
	@Transactional
	public Integer updateInvoiceNumberById(String number, Long id) {
		{
			if(!repo.existsById(id)) { 
				throw new InvoiceNotFoundException(
						new StringBuffer()
						.append("Invoice '")
						.append(id)
						.append("' not exist")
						.toString());
			}
			return repo.updateInvoiceNumberById(number, id);
		}
	}

	@Override
	public Invoice getInvoiceById(Long id) {
		Invoice inv = repo.findById(id)
				.orElseThrow(()->new InvoiceNotFoundException(
						new StringBuffer().append("Product  '")
						.append(id)
						.append("' not exist")
						.toString())
						);
		return inv;	
		}

}
