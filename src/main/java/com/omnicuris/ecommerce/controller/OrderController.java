package com.omnicuris.ecommerce.controller;

import com.omnicuris.ecommerce.error.MessageResponse;
import com.omnicuris.ecommerce.error.ServiceResponseException;
import com.omnicuris.ecommerce.model.order.OrderRequest;
import com.omnicuris.ecommerce.model.order.TransactionRequest;
import com.omnicuris.ecommerce.service.OrderService;
import com.omnicuris.ecommerce.service.TransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderService orderService;
  @Autowired
  private TransactionService transactionService;

  @PreAuthorize("hasRole('USER')")
  @PostMapping(
      value = "/add/cart",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity addToCart(@RequestBody List<OrderRequest> orderRequests) {
    try {
      orderService.validateRequest(orderRequests);
    } catch (ServiceResponseException e) {
      return ResponseEntity.status(e.getStatus()).body(new MessageResponse(e.getMessage()));
    }

    return ResponseEntity.ok(orderService.addToCart(orderRequests));
  }

  @PreAuthorize("hasRole('USER')")
  @PutMapping(
      value = "/edit/cart/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity editCart(
      @PathVariable("id") Long id, @RequestBody OrderRequest orderRequest) {

    try {
      return ResponseEntity.ok(orderService.validateAndSaveOrderEditRequest(id, orderRequest));
    } catch (ServiceResponseException e) {
      return ResponseEntity.status(e.getStatus()).body(new MessageResponse(e.getMessage()));
    }
  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping(
      value = "/buy",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity buyCartOrder(@RequestBody TransactionRequest transactionRequest) {
    try {
      transactionService.validateRequest(transactionRequest);
    } catch (ServiceResponseException e) {
      return ResponseEntity.status(e.getStatus()).body(new MessageResponse(e.getMessage()));
    }
    return ResponseEntity.ok(transactionService.buyCartItems(transactionRequest));
  }

  @PreAuthorize("hasRole('ADMIN', 'USER')")
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity findAll(@PathVariable("id") Long id) {
    try {
      return ResponseEntity.ok(orderService.findById(id));
    } catch (ServiceResponseException e) {
      return ResponseEntity.status(e.getStatus()).body(new MessageResponse(e.getMessage()));
    }
  }
}
