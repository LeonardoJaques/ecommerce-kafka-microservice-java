package br.com.jaquesprojetos.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class FraudeDetectorService {
		public static void main(String[] args) {
				var fraudeService = new FraudeDetectorService();
				try {
						var service = new KafkaService(FraudeDetectorService.class.getSimpleName(), "ECOMMERCE_NEW_ORDER", fraudeService::parse, Order.class, Map.of());
						service.run();
				} catch (Exception e) {
						throw new RuntimeException(e);
				}
		}
		
		private void parse(ConsumerRecord<String, Order> record) {
				System.out.println("----------");
				System.out.println("Processando novo pedido, verificando por fraude");
				System.out.println(record.key());
				System.out.println(record.value());
				System.out.println(record.partition());
				System.out.println(record.offset());
				try {
						Thread.sleep(5000);
				} catch (InterruptedException e) {
//								throw new RuntimeException(e);
						e.printStackTrace();
				}
				System.out.println("Pedido processado");
		}
}
				
		

