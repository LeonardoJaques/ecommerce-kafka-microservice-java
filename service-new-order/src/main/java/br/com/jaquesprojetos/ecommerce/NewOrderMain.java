package br.com.jaquesprojetos.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
		public static void main(String[] args) throws ExecutionException, InterruptedException {
				try (var dispatcherOrder = new KafkaDispatcher<Order>(); var dispatcherEmail = new KafkaDispatcher<String>()) {
						
						for (var i = 0; i < 10; i++) {
								var userId = UUID.randomUUID().toString();
								var orderId = UUID.randomUUID().toString();
								var amount = BigDecimal.valueOf(Math.random() * 5000 + 1);
								var order = new Order(userId, orderId, amount);
								
								
								dispatcherOrder.send("ECOMMERCE_NEW_ORDER", userId, order);
								var email = "Obrigado pelo seu pedido, estamos processando seu pedido";
								dispatcherEmail.send("ECOMMERCE_SEND_EMAIL", userId, email);
								
						}
				} catch (Exception e) {
						e.printStackTrace();
				}
		}
}
		
		

