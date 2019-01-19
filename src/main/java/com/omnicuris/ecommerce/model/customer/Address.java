package com.omnicuris.ecommerce.model.customer;

import com.omnicuris.ecommerce.model.order.Order;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "t_address")
public class Address {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(targetEntity = Customer.class)
  private Customer custId;

  private String address;
  private String tag;

  @OneToOne(targetEntity = Order.class)
  private Order orderId;
}
