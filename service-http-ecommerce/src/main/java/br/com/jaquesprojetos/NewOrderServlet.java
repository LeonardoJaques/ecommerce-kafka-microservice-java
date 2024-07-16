package br.com.jaquesprojetos;

import br.com.jaquesprojetos.ecommerce.KafkaDispatcher;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderServlet extends HttpServlet {
		private final KafkaDispatcher<Order> dispatcherOrder = new KafkaDispatcher<>();
		private final KafkaDispatcher<String> dispatcherEmail = new KafkaDispatcher<>();
		
		@Override
		public void destroy() {
				super.destroy();
				dispatcherOrder.close();
				dispatcherEmail.close();
		}
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				try {
						
						// we ara care about any security here, it is just an example
						
						String email = req.getParameter("email");
						var amount = BigDecimal.valueOf(Long.parseLong(req.getParameter("amount")));
						
						var orderId = UUID.randomUUID().toString();
						
						var order = new Order(orderId, amount, email);
						
						dispatcherOrder.send("ECOMMERCE_NEW_ORDER", email, order);
						var emailCode = "Obrigado pelo seu pedido, estamos " + "processando seu pedido";
						dispatcherEmail.send("ECOMMERCE_SEND_EMAIL", email, emailCode);
						
						System.out.println("Processo da nova compra finalizado");
						resp.getWriter().println("Processo da nova compra finalizado");
						resp.setStatus(HttpServletResponse.SC_OK);
						
				} catch (ExecutionException | InterruptedException e) {
						throw new ServletException(e);
				}
		}
}

