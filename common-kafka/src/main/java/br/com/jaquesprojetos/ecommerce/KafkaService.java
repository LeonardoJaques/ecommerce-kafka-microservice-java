package br.com.jaquesprojetos.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class KafkaService<T> implements Closeable {
		private final KafkaConsumer<String, T> consumer;
		private final ConsumerFunction parse;
		
		public KafkaService(String groupId, String topic, ConsumerFunction parse, Class<T> type, Map<String, String> properties) {
				this(parse, groupId, type, properties);
				consumer.subscribe(Collections.singletonList(topic));
		}
		
		public KafkaService(String groupId, Pattern topic, ConsumerFunction parse, Class<T> type, Map<String, String> properties) {
				this(parse, groupId, type, properties);
				consumer.subscribe(topic);
		}
		
		private KafkaService(ConsumerFunction parse, String groupId, Class<T> type, Map<String, String> properties) {
				this.parse = parse;
				this.consumer = new KafkaConsumer<>(getProperties(type, groupId, properties));
		}
		
		
		private Properties getProperties(Class<T> type, String groupId, Map<String, String> overrideProperties) {
				Properties properties = new Properties();
				properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
				properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
				properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GSonDeserializer.class.getName());
				properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
				properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
				properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
				properties.setProperty(GSonDeserializer.TYPE_CONFIG, type.getName());
				properties.putAll(overrideProperties);
				
				return properties;
		}
		
		public void run() {
				while (true) {
						var records = consumer.poll(Duration.ofMillis(500));
						if (!records.isEmpty()) {
								System.out.println("Encontrei " + records.count() + " registros");
								for (var record : records) {
										try {
												parse.consume(record);
										} catch (Exception e) {
												// only catch the exception to avoid the service to stop
												// so far, just logging the exception
												e.printStackTrace();
										}
								}
						}
				}
		}
		
		@Override
		public void close() {
				consumer.close();
		}
}
