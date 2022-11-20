package me.code.fulldemo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.code.fulldemo.models.Product;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPayload {

    private UUID id;

    private String name, description;
    private double price;

    public static ProductPayload from(Product product) {
        return new ProductPayload(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

}
