package br.com.jaquesprojetos.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
		public static void main(String[] args) throws ExecutionException, InterruptedException {
				try (var dispatcherOrder = new KafkaDispatcher<Order>(); var dispatcherEmail = new KafkaDispatcher<String>()) {
						var email = Math.random() + "@email.com";
						for (var i = 0; i < 10; i++) {
								var orderId = UUID.randomUUID().toString();
								var amount = BigDecimal.valueOf(Math.random() * 5000 + 1);
								
								var order = new Order(orderId, amount, email);
								
								dispatcherOrder.send("ECOMMERCE_NEW_ORDER", email, order);
								var emailCode = "Obrigado pelo seu pedido, estamos " + "processando seu pedido";
								dispatcherEmail.send("ECOMMERCE_SEND_EMAIL", email, emailCode);
								
						}
				} catch (Exception e) {
						e.printStackTrace();
				}
		}
}
		
		

