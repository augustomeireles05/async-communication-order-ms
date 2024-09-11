package br.com.foodcompany.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    @Field(name = "order_id")
    private Long id;

    @Field(name = "order_description")
    private String description;

    @Field(name = "quantity")
    private Integer quantity;
}
