package br.com.foodcompany.order.amqp;

import br.com.foodcompany.order.model.dto.CardDetailsDto;
import br.com.foodcompany.order.model.dto.OrderDto;
import br.com.foodcompany.order.model.dto.PaymentDto;
import br.com.foodcompany.order.model.dto.PaymentInfoDto;
import br.com.foodcompany.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentListener {

    private final OrderService orderService;

    @RabbitListener(queues = "${spring.rabbitmq.queue-name}")
    public void onPaymentConfirmed(PaymentDto payment) {
        var orderDto = mapPaymentToOrder(payment);
        orderService.updateOrderPaymentInfo(payment.getOrderId(), orderDto);
    }

    public OrderDto mapPaymentToOrder(PaymentDto payment) {
        var paymentInfoDto = new PaymentInfoDto();

        paymentInfoDto.setPaymentTypeId(payment.getPaymentTypeId());
        paymentInfoDto.setName(Optional.ofNullable(payment.getName()).orElse(""));
        paymentInfoDto.setValue(payment.getValue());

        /*//Validation to Credit card payment format
        if (payment.getPaymentTypeId() == 1L) {
            var cardDetails = new CardDetailsDto(payment.getNumber(), payment.getExpiration(), payment.getCode());
            paymentInfoDto.setCardDetails(cardDetails);
        }*/

        Optional.ofNullable(payment.getPaymentTypeId())
                .filter(typeId -> typeId == 1L)
                .ifPresent(typeId -> {
                    var cardDetails = new CardDetailsDto(payment.getNumber(), payment.getExpiration(), payment.getCode());
                    paymentInfoDto.setCardDetails(cardDetails);
                });

        //Create OrderDto and setting PaymentInfoDto
        var orderDto = new OrderDto();
        orderDto.setPaymentInfo(paymentInfoDto);

        return orderDto;
    }
}
