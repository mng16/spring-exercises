package com.customers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.customers.model.Customer;
import com.customers.repository.CustomerRepository;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/customer") // This means URL's start with /demo (after Application path)
public class CustomerController {
	@Autowired // This means to get the bean called userRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	private CustomerRepository customerRepository;

	@GetMapping(path="/add") // Map ONLY GET Requests
	public @ResponseBody String addNewUser (@RequestParam String name
			, @RequestParam String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		Customer n = new Customer();
		n.setName(name);
		n.setEmail(email);
		customerRepository.save(n);
		return "Saved";
	}

	@GetMapping(path="/getallcustomers")
	public @ResponseBody Iterable<Customer> getAllUsers() {
		// This returns a JSON or XML with the users
		return customerRepository.findAll();
	}

	@PostMapping("/adduser")
	public ResponseEntity addNewUser(@RequestBody Customer customerJson) {
		customerRepository.save(customerJson);
		return new ResponseEntity(customerJson, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity updateUserInfo(@PathVariable Integer id, @RequestBody Customer customerJson) {

		Customer customer = customerRepository.findById(id).orElse(null);

		customer.setName(customerJson.getName());
		customer.setEmail(customerJson.getEmail());
		customerRepository.save(customer);

		return new ResponseEntity(customer, HttpStatus.OK);
	}

	@PutMapping("/updatename/{id}")
	public ResponseEntity updateUserName(@PathVariable Integer id,
										 @RequestParam String name) {

		Customer customer = customerRepository.findById(id).orElse(null);

		customer.setName(name);

		customerRepository.save(customer);

		return new ResponseEntity(customer, HttpStatus.OK);
	}

	@PutMapping("/updateemail/{id}")
	public ResponseEntity updateUserEmail(@PathVariable Integer id,
										  @RequestParam String email) {

		Customer customer = customerRepository.findById(id).orElse(null);
		customer.setEmail(email);

        customerRepository.save(customer);

		return new ResponseEntity(customer, HttpStatus.OK);
	}
}
