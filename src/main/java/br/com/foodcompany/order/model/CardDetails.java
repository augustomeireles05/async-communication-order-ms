package br.com.foodcompany.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDetails {

    @Field(name = "card_number")
    private String number;

    @Field(name = "expiration_date")
    private String expiration;

    @Field(name = "security_code")
    private String code;
}
