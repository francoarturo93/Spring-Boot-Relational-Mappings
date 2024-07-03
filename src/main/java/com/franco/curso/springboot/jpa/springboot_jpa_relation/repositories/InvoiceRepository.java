package com.franco.curso.springboot.jpa.springboot_jpa_relation.repositories;

import org.springframework.data.repository.CrudRepository;

import com.franco.curso.springboot.jpa.springboot_jpa_relation.entities.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Long>{
} 
