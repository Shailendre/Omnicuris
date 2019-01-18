package com.omnicuris.ecommerce.model.customer;

import com.omnicuris.ecommerce.model.order.Order;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_address")
public class Address {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(targetEntity = Customer.class)
  @JoinColumn(name = "customer_id")
  private Customer customer_id;

  private String tag;

  @OneToOne(mappedBy = "address_id", targetEntity = Order.class)
  private Order order_id;
}
