package com.vmzone.demo.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vmzone.demo.dto.OrderBasicInfo;
import com.vmzone.demo.exceptions.BadCredentialsException;
import com.vmzone.demo.exceptions.BadRequestException;
import com.vmzone.demo.exceptions.NotEnoughQuantityException;
import com.vmzone.demo.exceptions.ResourceDoesntExistException;
import com.vmzone.demo.models.Order;
import com.vmzone.demo.models.OrderDetails;
import com.vmzone.demo.service.OrderService;
import com.vmzone.demo.utils.SessionManager;

/**
 * Rest Controller for managing order requests
 * 
 * @author Sabiha Djurina and Stefan Rangelov
 * 
 *
 */
@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;

	@PostMapping("/order")
	public Order createNewOrder(HttpSession session) throws ResourceDoesntExistException, BadRequestException, NotEnoughQuantityException, BadCredentialsException {
		SessionManager.isAuthenticated(session);

		return this.orderService.createNewOrder(SessionManager.getLoggedUser(session));
	}

	@GetMapping("/orders")
	public List<OrderBasicInfo> getAllOrdersForUser(HttpSession session) throws BadCredentialsException, ResourceDoesntExistException {
		SessionManager.isAuthenticated(session);

		return this.orderService.getAllOrdersForUser(SessionManager.getLoggedUser(session));
	}

	@GetMapping("/order/{id}")
	public List<OrderDetails> getDetailsForOrderById(@PathVariable long id,HttpSession session) throws BadCredentialsException, ResourceDoesntExistException {
		SessionManager.isAuthenticated(session);

		return this.orderService.getOrderDetailsById(id,SessionManager.getLoggedUser(session));
	}
}
