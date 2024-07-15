package br.com.jaquesprojetos.ecommerce;

import java.math.BigDecimal;

public class Order {
		private final String userId;
		private final String orderId;
		private final BigDecimal amount;
		
		public Order(String userId, String orderId, BigDecimal value) {
				this.userId = userId;
				this.orderId = orderId;
				this.amount = value;
		}
		
		public BigDecimal getAmount() {
				return amount;
		}
		
		public String getUserId() {
				return userId;
		}
		
		public String getOrderId() {
				return orderId;
		}
		
		@Override
		public String toString() {
				return "Order{" +
								"userId='" + userId + '\'' +
								", orderId='" + orderId + '\'' +
								", amount=" + amount +
								'}';
		}
}
