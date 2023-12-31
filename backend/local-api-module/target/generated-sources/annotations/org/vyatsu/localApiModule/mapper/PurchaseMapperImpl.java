package org.vyatsu.localApiModule.mapper;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.vyatsu.localApiModule.dto.response.api.PurchaseDto;
import org.vyatsu.localApiModule.entity.purchase.Purchase;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-01T22:45:46+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PurchaseMapperImpl implements PurchaseMapper {

    @Override
    public Purchase toEntity(PurchaseDto purchaseDto) {
        if ( purchaseDto == null ) {
            return null;
        }

        Purchase.PurchaseBuilder purchase = Purchase.builder();

        purchase.id( purchaseDto.getId() );
        purchase.cost( purchaseDto.getCost() );
        purchase.createdAt( purchaseDto.getCreatedAt() );

        return purchase.build();
    }

    @Override
    public PurchaseDto toDto(Purchase purchase) {
        if ( purchase == null ) {
            return null;
        }

        Long id = null;
        double cost = 0.0d;
        LocalDateTime createdAt = null;

        id = purchase.getId();
        cost = purchase.getCost();
        createdAt = purchase.getCreatedAt();

        PurchaseDto purchaseDto = new PurchaseDto( id, cost, createdAt );

        return purchaseDto;
    }

    @Override
    public Purchase partialUpdate(PurchaseDto purchaseDto, Purchase purchase) {
        if ( purchaseDto == null ) {
            return purchase;
        }

        if ( purchaseDto.getId() != null ) {
            purchase.setId( purchaseDto.getId() );
        }
        purchase.setCost( purchaseDto.getCost() );
        if ( purchaseDto.getCreatedAt() != null ) {
            purchase.setCreatedAt( purchaseDto.getCreatedAt() );
        }

        return purchase;
    }
}
