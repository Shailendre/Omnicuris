package com.omnicuris.ecommerce.model.customer;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Setter
@Getter
public class Customer {

	@Id
	@GeneratedValue
	private String id;

	@Column(unique = true)
	private String emailId;

}
