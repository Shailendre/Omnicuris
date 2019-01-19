package com.omnicuris.ecommerce.service;

import com.omnicuris.ecommerce.error.ServiceResponseException;
import com.omnicuris.ecommerce.model.customer.Address;
import com.omnicuris.ecommerce.model.customer.Customer;
import com.omnicuris.ecommerce.model.item.Item;
import com.omnicuris.ecommerce.model.order.Order;
import com.omnicuris.ecommerce.model.order.OrderRequest;
import com.omnicuris.ecommerce.model.order.OrderStatus;
import com.omnicuris.ecommerce.model.order.Transaction;
import com.omnicuris.ecommerce.repository.AddressRepository;
import com.omnicuris.ecommerce.repository.CustomerRepository;
import com.omnicuris.ecommerce.repository.InventoryRepository;
import com.omnicuris.ecommerce.repository.ItemRepository;
import com.omnicuris.ecommerce.repository.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private TransactionService transactionRepository;
  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private InventoryRepository inventoryRepository;

  public void validateRequest(List<OrderRequest> orderRequests) throws ServiceResponseException {

    for (OrderRequest orderRequest : orderRequests) {
      validateEachOrder(orderRequest);
      if (orderRequest.getStatus() != OrderStatus.ORDERED) {
        throw ServiceResponseException.status(HttpStatus.BAD_REQUEST)
            .message("First time order can be in ORDERED status only!");
      }
    }
  }

  public List<Order> addToCart(List<OrderRequest> orderRequests) {

    List<Order> orders = new ArrayList<>();
    orderRequests
        .stream()
        .map(this::mapOrderRequestToOrder)
        .forEach(
            order -> {
              orders.add(orderRepository.save(order));
            });

    return orders;
  }

  private Order mapOrderRequestToOrder(OrderRequest orderRequest) {

    Order order = new Order();

    Item item = itemRepository.findById(orderRequest.getItemId()).get();
    Address address = addressRepository.findById(orderRequest.getAddressId()).get();
    Customer customer = customerRepository.findById(orderRequest.getCustomerId()).get();

    order.setItemId(item);
    order.setItemQty(orderRequest.getQty());
    order.setOrderTotal(item.getPrice() * orderRequest.getQty());
    order.setAddrId(address);
    order.setCustId(customer);
    order.setStatus(orderRequest.getStatus());

    return order;
  }

  public Order validateAndSaveOrderEditRequest(Long id, OrderRequest orderRequest)
      throws ServiceResponseException {

    if (!orderRepository.existsById(id)) {
      throw ServiceResponseException.status(HttpStatus.BAD_REQUEST)
          .message("Invalid order id " + id);
    }
    validateEachOrder(orderRequest);

    Transaction transaction = orderRepository.findById(id).get().getTransId();
    if (transaction != null) {
      throw ServiceResponseException.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .message("Cannot modify cart since transaction was recorded against this order!");
    }

    Order order = mapOrderRequestToOrder(orderRequest);
    order.setId(id);
    return orderRepository.save(order);
  }

  private void validateEachOrder(OrderRequest orderRequest) throws ServiceResponseException {

    if (!itemRepository.existsById(orderRequest.getItemId())) {
      throw ServiceResponseException.status(HttpStatus.BAD_REQUEST)
          .message("Invalid item id " + orderRequest.getItemId());
    }
    if (!customerRepository.existsById(orderRequest.getCustomerId())) {
      throw ServiceResponseException.status(HttpStatus.BAD_REQUEST)
          .message("Invalid customer id " + orderRequest.getCustomerId());
    }
    if (!addressRepository.existsById(orderRequest.getAddressId())) {
      throw ServiceResponseException.status(HttpStatus.BAD_REQUEST)
          .message("Invalid address id " + orderRequest.getAddressId());
    }
    if (!addressRepository
        .findById(orderRequest.getAddressId())
        .get()
        .getCustId()
        .equals(orderRequest.getCustomerId())) {
      throw ServiceResponseException.status(HttpStatus.BAD_REQUEST)
          .message(
              "Invalid address "
                  + orderRequest.getAddressId()
                  + " for customer "
                  + orderRequest.getCustomerId());
    }

    Item item = itemRepository.findById(orderRequest.getItemId()).get();
    Integer inStockItems = inventoryRepository.findByItemId(item).get().getQty();

    if (orderRequest.getQty() <= 0 || orderRequest.getQty() > inStockItems) {
      throw ServiceResponseException.status(HttpStatus.BAD_REQUEST)
          .message(
              "Invalid quantity for item id" + item.getId() + ". Can be between 1-" + inStockItems);
    }
  }

  public Order findById(Long id) throws ServiceResponseException {
    return orderRepository
        .findById(id)
        .orElseThrow(
            () ->
                ServiceResponseException.status(HttpStatus.NOT_FOUND)
                    .message("No order found with id " + id));
  }
}
