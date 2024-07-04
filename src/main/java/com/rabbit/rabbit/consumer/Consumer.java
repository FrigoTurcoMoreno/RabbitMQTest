package com.rabbit.rabbit.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumer implements Runnable{
	
	private String queue = "Queue test";
	
	
	public Consumer() {
		
	}
	
	public Consumer(String queue) {
		this.queue = queue;
	}

	
	private void consumeMessage(int i) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("user");
        factory.setPassword("password");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(this.queue, false, false, false, null);
        System.out.println(" [Consumer n." + i + "] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [Consumer n." + i + "] Received '" + message + "'");
        };
        channel.basicConsume(this.queue, true, deliverCallback, consumerTag -> { });
	}

	@Override
	public void run() {
		for (int i=1; i<=10; i++) {
			try {
				this.consumeMessage(i);
			} catch (IOException | TimeoutException e) {
				e.printStackTrace();
			}
		}
		
	}
}
