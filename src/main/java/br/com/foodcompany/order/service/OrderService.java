package br.com.foodcompany.order.service;

import br.com.foodcompany.order.exception.EntityNotFoundException;
import br.com.foodcompany.order.model.Order;
import br.com.foodcompany.order.model.PaymentInfo;
import br.com.foodcompany.order.model.Status;
import br.com.foodcompany.order.model.dto.OrderDto;
import br.com.foodcompany.order.model.dto.PaymentDto;
import br.com.foodcompany.order.model.dto.PaymentInfoDto;
import br.com.foodcompany.order.model.dto.StatusDto;
import br.com.foodcompany.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    private final ModelMapper mapper;

    private AtomicLong counter = new AtomicLong(1);

    public List<OrderDto> getAllOrders() {
        return repository.findAll().stream()
                .map(o -> mapper.map(o, OrderDto.class))
                .toList();
    }

    public OrderDto getOrderById(String id) {
        var order = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found!"));

        return mapper.map(order, OrderDto.class);
    }

    public OrderDto createOrder(OrderDto dto) {
        var order = mapper.map(dto, Order.class);

        order.setDateTime(LocalDateTime.now().withNano(0));
        order.setStatus(Status.PLACED);

        order.getItems().forEach(item -> item.setId(counter.getAndIncrement()));

        var savedOrder = repository.save(order);

        return mapper.map(savedOrder, OrderDto.class);
    }

    public OrderDto updateOrder(String id, StatusDto dto) {
        var order = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found!"));

        order.setStatus(dto.getStatus());

        var updatedOrder = repository.save(order);

        return mapper.map(updatedOrder, OrderDto.class);
    }

    public void approveOrderPayment(String id) {
        var order = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found!"));

        order.setStatus(Status.PAID);

        repository.save(order);
    }

    public void updateOrderPaymentInfo(Long orderId, OrderDto orderDto) {
        var order = repository.findById(orderId.toString())
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + orderId + " not found!"));

        var paymentInfoDto = orderDto.getPaymentInfo();
        var paymentInfo = convertToPaymentInfo(paymentInfoDto);

        order.setPaymentInfo(paymentInfo);
        order.setStatus(orderDto.getStatus() != null ? orderDto.getStatus() : order.getStatus());

        repository.save(order);
    }

    public PaymentInfo convertToPaymentInfo(PaymentInfoDto paymentInfoDto) {
        return mapper.map(paymentInfoDto, PaymentInfo.class);
    }
}
