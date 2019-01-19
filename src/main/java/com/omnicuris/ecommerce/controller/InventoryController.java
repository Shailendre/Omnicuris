package com.omnicuris.ecommerce.controller;

import com.omnicuris.ecommerce.error.MessageResponse;
import com.omnicuris.ecommerce.error.ServiceResponseException;
import com.omnicuris.ecommerce.model.inventory.UpdateInventoryItemRequest;
import com.omnicuris.ecommerce.service.InventoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@PreAuthorize("hasRole('ADMIN')")
public class InventoryController {

  @Autowired
  private InventoryService inventoryService;

  @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity addItemRequest(
      @RequestBody List<UpdateInventoryItemRequest> updateInventoryItemRequests) {

    try {
      inventoryService.validateRequest(updateInventoryItemRequests);
    } catch (ServiceResponseException e) {
      return ResponseEntity.status(e.getStatus()).body(new MessageResponse(e.getMessage()));
    }

    inventoryService.updateInventory(updateInventoryItemRequests);
    return ResponseEntity.ok(
        new MessageResponse("Updated " + updateInventoryItemRequests.size() + " items."));
  }
}
