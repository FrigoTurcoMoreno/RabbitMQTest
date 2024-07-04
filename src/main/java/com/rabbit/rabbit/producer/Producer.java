package com.rabbit.rabbit.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer implements Runnable{
	
	private String queue = "Queue test";
	private String messsage = "Messaggio test";
	
	public Producer() {
		
	}
	
	public Producer(String message) {
		this.messsage = message;
	}
	
	public Producer(String queue, String message) {
		this.queue = queue;
		this.messsage = message;
	}
	
	private void produceMessage(String message, int i) throws IOException, TimeoutException {
		 ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("localhost");
	        factory.setPort(5672);
	        factory.setUsername("user");
	        factory.setPassword("password");
	        try (Connection connection = factory.newConnection();
	             Channel channel = connection.createChannel()) {
	            channel.queueDeclare(this.queue, false, false, false, null);
	            channel.basicPublish("", this.queue, null, message.getBytes());
	            System.out.println(" [Producer n." + i + "] Sent '" + message + "'");
	        }
	}

	@Override
	public void run() {
		for (int i=1; i<=10; i++) {
			try {
				this.produceMessage(this.messsage, i);
			} catch (IOException | TimeoutException e) {
				e.printStackTrace();
			}
		}
		
	}
}
