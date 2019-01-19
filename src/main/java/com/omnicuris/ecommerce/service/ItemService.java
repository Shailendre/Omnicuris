package com.omnicuris.ecommerce.service;

import com.omnicuris.ecommerce.error.ServiceResponseException;
import com.omnicuris.ecommerce.model.item.Item;
import com.omnicuris.ecommerce.model.item.UpdateItemRequest;
import com.omnicuris.ecommerce.repository.ItemRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

  @Autowired
  private ItemRepository itemRepository;

  public void validateRequest(Item item) throws ServiceResponseException {
    // validation
    // for simplicity keeping only price validation
    if (item.getPrice() == null || item.getPrice() <= 0) {
      throw ServiceResponseException.status(HttpStatus.BAD_REQUEST).message("Price is invalid");
    }
  }

  public void validateUpdateRequest(UpdateItemRequest updateItemRequest)
      throws ServiceResponseException {
    // validation
    // for simplicity keeping only price validation
    if (updateItemRequest.getPrice() == null || updateItemRequest.getPrice() <= 0) {
      throw ServiceResponseException.status(HttpStatus.BAD_REQUEST).message("Price is invalid");
    }
  }

  public Item addItem(Item item) {
    return itemRepository.save(item);
  }

  public Item getItemById(Long id) throws ServiceResponseException {

    return itemRepository
        .findById(id)
        .orElseThrow(
            () ->
                ServiceResponseException.status(HttpStatus.BAD_REQUEST)
                    .message("Failed to find Item with id " + id));
  }

  public Item updateItem(Long id, UpdateItemRequest updateItemRequest)
      throws ServiceResponseException {
    Optional<Item> itemOptional = itemRepository.findById(id);
    if (!itemOptional.isPresent()) {
      throw ServiceResponseException.status(HttpStatus.BAD_REQUEST).message("Invalid item " + id);
    }

    Item item = itemOptional.get();
    item.setName(updateItemRequest.getName());
    item.setDescription(updateItemRequest.getDescription());
    item.setPrice(updateItemRequest.getPrice());

    return itemRepository.save(item);
  }

  public List<Item> findAll() {
    return itemRepository.findAll();
  }
}
