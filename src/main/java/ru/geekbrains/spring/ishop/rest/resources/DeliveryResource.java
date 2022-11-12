package ru.geekbrains.spring.ishop.rest.resources;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;
import ru.geekbrains.spring.ishop.service.AddressService;
import ru.geekbrains.spring.ishop.service.DeliveryService;

@RestController
@RequestMapping("/api/v1/order/delivery")
@RequiredArgsConstructor
public class DeliveryResource extends AbstractResource {
    private final DeliveryService deliveryService;

    //TODO for test only
    private final OutEntityService outEntityService;

    //TODO for test only
    private final AddressService addressService;

    @GetMapping(value = "/{deliveryId}/deliveryId")
    public ResponseEntity<OutEntity> getDeliveryOutEntity(@PathVariable("deliveryId") Long deliveryId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(outEntityService.convertEntityToOutEntity(deliveryService.findByIdOptional(deliveryId)));
    }
//    @GetMapping(value = "/{deliveryId}/deliveryId")
//    public ResponseEntity<OutEntity> getDeliveryOutEntity(@PathVariable("deliveryId") Long deliveryId) {
//        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(deliveryService.convertDeliveryToOutEntity(deliveryService.findByIdOptional(deliveryId)));
//    }

    @PutMapping(value = "/{deliveryId}/deliveryId/deliveredAt/string")
    public ResponseEntity<OutEntity> updateServerAcceptedAtFieldOfDelivery(
        @RequestBody @Valid String newValue,
        @PathVariable("deliveryId") Long deliveryId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(outEntityService.convertEntityToOutEntity(
                        deliveryService.updateServerAcceptedAt(deliveryId, newValue)));
    }
//    @PutMapping(value = "/{deliveryId}/deliveryId/deliveredAt/string")
//    public ResponseEntity<OutEntity> updateServerAcceptedAtFieldOfDelivery(
//            @RequestBody @Valid String newValue,
//            @PathVariable("deliveryId") Long deliveryId) {
//        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(deliveryService.convertDeliveryToOutEntity(
//                        deliveryService.updateServerAcceptedAt(deliveryId, newValue)));
//    }

    //TODO for test only
    @GetMapping(value = "/{addressId}/addressId")
    public ResponseEntity<OutEntity> getAddressOutEntity(@PathVariable("addressId") Long addressId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(outEntityService.convertEntityToOutEntity(addressService.findById(addressId)));
    }

}
