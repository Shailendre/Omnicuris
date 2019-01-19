package com.omnicuris.ecommerce.service;

import com.omnicuris.ecommerce.error.ServiceResponseException;
import com.omnicuris.ecommerce.model.order.Order;
import com.omnicuris.ecommerce.model.order.OrderStatus;
import com.omnicuris.ecommerce.model.order.Transaction;
import com.omnicuris.ecommerce.model.order.TransactionRequest;
import com.omnicuris.ecommerce.model.order.TransactionStatus;
import com.omnicuris.ecommerce.repository.OrderRepository;
import com.omnicuris.ecommerce.repository.TransactionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;
  @Autowired
  private OrderRepository orderRepository;

  public void validateRequest(TransactionRequest transactionRequest)
      throws ServiceResponseException {

    if (CollectionUtils.isEmpty(transactionRequest.getOrderIds())) {
      throw ServiceResponseException.status(HttpStatus.BAD_REQUEST).message("Invalid order ids");
    }

    for (Long orderId : transactionRequest.getOrderIds()) {
      if (!orderRepository.existsById(orderId)) {
        throw ServiceResponseException.status(HttpStatus.BAD_REQUEST)
            .message("Invalid order id " + orderId);
      }
      Order order = orderRepository.findById(orderId).get();
      if (!order.getStatus().equals(OrderStatus.ORDERED)) {
        throw ServiceResponseException.status(HttpStatus.BAD_REQUEST)
            .message("Transaction not allowed if status NOT ORDERED");
      }
      if (order.getTransId() != null
          && order.getTransId().getStatus().equals(TransactionStatus.SUCCESS)) {
        throw ServiceResponseException.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .message(
                "Order id " + orderId + " has a successful trnsaction with id " + order.getTransId()
                    .getId() + "!");
      }
    }
  }

  public Transaction buyCartItems(TransactionRequest transactionRequest) {

    List<Order> orders = orderRepository.findAllById(transactionRequest.getOrderIds());
    Transaction transaction = new Transaction();
    transaction.setOrders(orders);
    transaction.setStatus(transactionRequest.getStatus());
    return transactionRepository.save(transaction);

  }
}
