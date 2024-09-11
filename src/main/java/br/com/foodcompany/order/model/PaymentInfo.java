package br.com.foodcompany.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo {

    @Field(name = "payment_type_id")
    private Long paymentTypeId;

    @Field(name = "payment_description")
    private String paymentDescription;

    @Field(name = "payment_value")
    private BigDecimal value;

    @Field(name = "card_details")
    private CardDetails cardDetails;
}
