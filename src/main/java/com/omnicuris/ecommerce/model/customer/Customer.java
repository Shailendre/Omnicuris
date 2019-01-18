package com.omnicuris.ecommerce.model.customer;

import com.omnicuris.ecommerce.model.order.Order;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_customer")
@Setter
@Getter
public class Customer {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String email_id;

	private String role;
	private String f_name;
	private String l_name;
	private String contact;

	@OneToMany(mappedBy = "customer_id", targetEntity = Address.class)
	private List<Address> addresses;

	@OneToMany(mappedBy = "customer_id", targetEntity = Order.class)
	private List<Order> orders;
}
