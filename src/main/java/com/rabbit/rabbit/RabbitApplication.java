package com.rabbit.rabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rabbit.rabbit.consumer.Consumer;
import com.rabbit.rabbit.producer.Producer;

@SpringBootApplication
public class RabbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitApplication.class, args);
		
		Producer p = new Producer();
		
		Consumer c = new Consumer();
		
		Thread tConsumer = new Thread(c);
		Thread tProducer = new Thread(p);
		
		tConsumer.start();
		tProducer.start();
		
	}

}
