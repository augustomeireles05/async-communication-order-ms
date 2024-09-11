package br.com.foodcompany.order.repository;

import br.com.foodcompany.order.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
