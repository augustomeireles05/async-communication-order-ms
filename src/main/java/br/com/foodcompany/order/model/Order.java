package br.com.foodcompany.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order")
public class Order {

    @MongoId
    private String id;

    @Field(name = "order_date_time", targetType = FieldType.DATE_TIME)
    private LocalDateTime dateTime;

    @Field(name = "order_status")
    private Status status;

    @Field(name = "order_items")
    private List<OrderItem> items = new ArrayList<>();

    @Field(name = "payment_info")
    private PaymentInfo paymentInfo;

}
