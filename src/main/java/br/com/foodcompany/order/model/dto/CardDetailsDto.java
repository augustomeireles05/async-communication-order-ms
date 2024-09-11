package br.com.foodcompany.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDetailsDto {

    private String number;
    private String expiration;
    private String code;
}
