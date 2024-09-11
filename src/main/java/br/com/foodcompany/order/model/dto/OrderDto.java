package br.com.foodcompany.order.model.dto;

import br.com.foodcompany.order.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String id;
    private LocalDateTime dateTime;
    private Status status;
    private List<OrderItemDto> items = new ArrayList<>();
    private PaymentInfoDto paymentInfo;
}
