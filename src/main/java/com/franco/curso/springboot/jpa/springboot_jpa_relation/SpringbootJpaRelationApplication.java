package com.franco.curso.springboot.jpa.springboot_jpa_relation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.franco.curso.springboot.jpa.springboot_jpa_relation.entities.Address;
import com.franco.curso.springboot.jpa.springboot_jpa_relation.entities.Client;
import com.franco.curso.springboot.jpa.springboot_jpa_relation.entities.Invoice;
import com.franco.curso.springboot.jpa.springboot_jpa_relation.repositories.ClientRepository;
import com.franco.curso.springboot.jpa.springboot_jpa_relation.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationApplication implements CommandLineRunner {
	
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// manyToOne();
		// manytoOneFindByIdClient();

		// oneToMany();
		// oneToManyFindById();
		// removeAddress();
		removeAddressFindById();

	}
	
	/***************************************************************************** */
	
	/***************************************************************************** */

	/******************************OneToMany**********************************/
	@Transactional
	public void removeAddressFindById() {
		Optional<Client> optionalClient = clientRepository.findById(2L);
		optionalClient.ifPresent(client -> {
			Address address1 = new Address("Guillermo Prescott", 232);
			Address address2 = new Address("Julia Codesido", 101);
			
			// List<Address> addresses = new ArrayList<>();
			// addresses.add(address1);
			// addresses.add(address2);

			// client.setAddresses(addresses);
			client.setAddresses(Arrays.asList(address1,address2));

			clientRepository.save(client);  

			System.out.println(client);
			
			Optional<Client> optionalClient2 = clientRepository.findOne(2L); 
			optionalClient2.ifPresent(c -> {
			Address address3 = c.getAddresses().get(1);
			c.getAddresses().remove(address3);
			clientRepository.save(c);
			System.out.println(c);

		});
		});
	}

	@Transactional
	private void removeAddress() {
		Client client = new Client("Fran", "Moras");

		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9875);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);

		Optional<Client> optionalClient = clientRepository.findById(3L);
		optionalClient.ifPresent(c -> {
			c.getAddresses().remove(address1);
			clientRepository.save(c);
			System.out.println(c);

		});
	}

	@Transactional
	private void oneToMany() {
		Client client = new Client("Fran", "Moras");

		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9875);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);
	}
	
	@Transactional
	public void oneToManyFindById() {
		Optional<Client> optionalClient = clientRepository.findById(2L);
		optionalClient.ifPresent(client -> {
			Address address1 = new Address("Guillermo Prescott", 232);
			Address address2 = new Address("Julia Codesido", 101);
			
			// List<Address> addresses = new ArrayList<>();
			// addresses.add(address1);
			// addresses.add(address2);

			// client.setAddresses(addresses);
			client.setAddresses(Arrays.asList(address1,address2));

			clientRepository.save(client);  

			System.out.println(client); 
		});
	}
	/**************************************************************************/
	/********************************ManyToOne*******************************/
	@Transactional
	private void manyToOne() {
		//creamos y lo persistimos
		Client client = new Client("Franco", "Aguilar");
		clientRepository.save(client);//persistencia

		//se le asigna la clave foranea
		Invoice invoice = new Invoice("compras de oficina", 2000L);
		invoice.setClient(client);

		Invoice invoiceDB = invoiceRepository.save(invoice);

		System.out.println(invoiceDB);
	}

	@Transactional
	public void manytoOneFindByIdClient() {

		Optional<Client> optionalClient = clientRepository.findById(1L);

		if (optionalClient.isPresent()) {
			Client client = optionalClient.orElseThrow();//si esta presente devuelve el valor

			Invoice invoice = new Invoice("compras del hogar", 3000L);
			invoice.setClient(client);

			Invoice invoiceDB = invoiceRepository.save(invoice);
			System.out.println(invoiceDB);
		}
	}

}
