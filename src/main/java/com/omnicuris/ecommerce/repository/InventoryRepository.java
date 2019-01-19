package com.omnicuris.ecommerce.repository;

import com.omnicuris.ecommerce.model.inventory.Inventory;
import com.omnicuris.ecommerce.model.item.Item;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

  Optional<Inventory> findByItemId(Item itemid);

}
