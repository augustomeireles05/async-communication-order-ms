package br.com.foodcompany.order.model.dto;

import br.com.foodcompany.order.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDto {
    private Status status;
}
