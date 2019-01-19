package com.omnicuris.ecommerce.model.inventory;

import com.omnicuris.ecommerce.model.item.Item;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Entity
@Table(name = "t_inventory")
@EntityListeners(AuditingEntityListener.class)
public class Inventory {

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne(targetEntity = Item.class)
  private Item itemId;

  private Integer qty;

  @Column(nullable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @LastModifiedDate
  private Date updatedDate;
}
