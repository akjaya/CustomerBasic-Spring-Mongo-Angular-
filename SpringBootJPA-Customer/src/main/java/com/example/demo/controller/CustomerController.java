package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerRepository cp;
	@PostMapping("/customer")
	public ResponseEntity<Customer> saveCustomer(@RequestBody Customer c) {
		Customer bk=cp.save(c);
		return new ResponseEntity<Customer>(bk,HttpStatus.OK);
	}
	@GetMapping("/getCustomer/{custId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("custId") int id) {
		Optional<Customer> b = cp.findById(id);
		if(b.isPresent()) {
		Customer bk=b.get();
		return new ResponseEntity<Customer>(bk,HttpStatus.OK);
	}
		return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
}
	@DeleteMapping("/deleteCustomer/{custId}")
	public ResponseEntity<Customer> deleteCustomerById(@PathVariable("custId") int id) {
		if(cp.existsById(id)) {
		cp.deleteById(id);
		return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
	}
		return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
}
	@GetMapping("/getCustomerDetails")
	public ResponseEntity<List<Customer>> getAllCustomerDetails() {
		List<Customer> lstCustomer = cp.findAll();
		return new ResponseEntity<List<Customer>>(lstCustomer,HttpStatus.OK);
}
	@PutMapping("/update/{custId}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("custId") int id,@RequestParam ("address") String address) {
		Customer ob = new Customer();
		Optional<Customer> b = cp.findById(id);
		if(b.isPresent()) {
		Customer bk=b.get();
		ob.setName(bk.getName());
//		ob.setContact(bk.getContact());
//		ob.setEmail(bk.getEmail());
		ob.setAddress(address);
		ob.setId(id);
		Customer updated = cp.save(ob);
		return new ResponseEntity<Customer>(ob,HttpStatus.OK);
	}
		return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
}
}
