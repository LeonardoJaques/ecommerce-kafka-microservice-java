package br.com.jaquesprojetos.ecommerce;

import java.math.BigDecimal;

public class Order {
		private final String email;
		private final String orderId;
		private final BigDecimal amount;
		
		public Order(String email, String orderId, BigDecimal value) {
				this.orderId = orderId;
				this.amount = value;
				this.email = email;
		}
		
		public String getEmail() {
				return email;
		}
		
		public BigDecimal getAmount() {
				return amount;
		}
		
		public String getOrderId() {
				return orderId;
		}
		
		@Override
		public String toString() {
				return "Order{" +
								"email='" + email + '\'' +
								", orderId='" + orderId + '\'' +
								", amount=" + amount +
								'}';
		}
}
