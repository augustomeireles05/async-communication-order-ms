package br.com.foodcompany.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfoDto {

    private Long paymentTypeId;
    private String name;
    private BigDecimal value;
    private CardDetailsDto cardDetails;
}
