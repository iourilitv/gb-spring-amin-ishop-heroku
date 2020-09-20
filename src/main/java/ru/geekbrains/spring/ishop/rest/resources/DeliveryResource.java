package ru.geekbrains.spring.ishop.rest.resources;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.service.DeliveryService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/order/delivery")
@RequiredArgsConstructor
public class DeliveryResource extends AbstractResource {
    private final DeliveryService deliveryService;

    @GetMapping(value = "/{deliveryId}/deliveryId")
    public ResponseEntity<OutEntity> getOrderOutEntity(@PathVariable("deliveryId") Long deliveryId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(deliveryService.convertDeliveryToOutEntity(deliveryService.findByIdOptional(deliveryId)));
    }

    @PutMapping(value = "/{deliveryId}/deliveryId/deliveredAt/string")
    public ResponseEntity<OutEntity> updateServerAcceptedAtFieldOfDelivery(
        @RequestBody @Valid String newValue,
        @PathVariable("deliveryId") Long deliveryId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(deliveryService.convertDeliveryToOutEntity(
                        deliveryService.updateServerAcceptedAt(deliveryId, newValue)));
    }


}
