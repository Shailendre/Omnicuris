package com.omnicuris.ecommerce.service;

import com.omnicuris.ecommerce.error.ServiceResponseException;
import com.omnicuris.ecommerce.model.inventory.Inventory;
import com.omnicuris.ecommerce.model.inventory.UpdateInventoryItemRequest;
import com.omnicuris.ecommerce.model.item.Item;
import com.omnicuris.ecommerce.repository.InventoryRepository;
import com.omnicuris.ecommerce.repository.ItemRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private InventoryRepository inventoryRepository;

  /**
   * validate the item id and qty
   *
   * @param updateInventoryItemRequests incoming request
   * @throws ServiceResponseException bad request
   */
  public void validateRequest(List<UpdateInventoryItemRequest> updateInventoryItemRequests)
      throws ServiceResponseException {

    final UpdateInventoryItemRequest[] invalidInventoryUpdateRequest = new UpdateInventoryItemRequest[1];

    Optional<UpdateInventoryItemRequest> optionalUpdateItemRequest =
        updateInventoryItemRequests
            .stream()
            .filter(
                updateInventoryItemRequest -> {
                  invalidInventoryUpdateRequest[0] = updateInventoryItemRequest;
                  return
                      itemRepository.findById(updateInventoryItemRequest.getItemId()).orElse(null)
                          == null
                          || updateInventoryItemRequest.getQty() < 0;
                })
            .findFirst();

    if (optionalUpdateItemRequest.isPresent()) {
      throw ServiceResponseException.status(HttpStatus.BAD_REQUEST)
          .message(
              "Invalid inventory request: "
                  + invalidInventoryUpdateRequest[0].getItemId()
                  + ","
                  + invalidInventoryUpdateRequest[0].getQty());
    }
  }

  public void updateInventory(List<UpdateInventoryItemRequest> updateInventoryItemRequests) {

    updateInventoryItemRequests.forEach(updateInventoryItemRequest -> {
      Item item = itemRepository.findById(updateInventoryItemRequest.getItemId()).get();
      Inventory inventory = inventoryRepository.findByItemId(item).orElse(new Inventory());
      inventory.setItemId(item);
      inventory.setQty(updateInventoryItemRequest.getQty());
      inventoryRepository.save(inventory);
    });


  }

  public List<Inventory> findAll() {
    return inventoryRepository.findAll();
  }
}
