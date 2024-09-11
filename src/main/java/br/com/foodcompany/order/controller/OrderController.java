package br.com.foodcompany.order.controller;

import br.com.foodcompany.order.model.dto.OrderDto;
import br.com.foodcompany.order.model.dto.StatusDto;
import br.com.foodcompany.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable String id) {
       var dto = service.getOrderById(id);
       return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto dto, UriComponentsBuilder uriBuilder) {
        var createdOrder = service.createOrder(dto);
        var uri = uriBuilder.path("/orders/{id}").buildAndExpand(createdOrder.getId()).toUri();
        return ResponseEntity.created(uri).body(createdOrder);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable String id, @RequestBody StatusDto statusDto) {
        var dto = service.updateOrder(id, statusDto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/paid")
    public ResponseEntity<Void> approveOrder(@PathVariable String id) {
        service.approveOrderPayment(id);
        return ResponseEntity.noContent().build();
    }

    /*
    * Para testar o load balancer do gateway, executa em 2 ou mais terminais o comando abaixo para subirmos várias
    *  instâncias dessa aplicação:
    *
    * & "C:\Users\Cadmus\Documents\estudos\alura\ms-implementando-java-spring\order\mvnw.cmd" spring-boot:run -f "C:\Users\Cadmus\Documents\estudos\alura\ms-implementando-java-spring\order\pom.xml"
    *
    * e a partir daí executa esse endpoint e ver que a cada chamada ele te direciona a uma porta diferente.
     * */
    @GetMapping("/port")
    public String getPort(@Value("${local.server.port}") String port) {
        return String.format("Response instance executing on the port %s", port);
    }
}
