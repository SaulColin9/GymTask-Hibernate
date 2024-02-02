package org.example;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.messaging.Sender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableJms
@EnableAutoConfiguration
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"org.example.configuration", "org.example.messaging"})
public class GymApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(GymApplication.class, args);
        Sender sender = ctx.getBean(Sender.class);
//        sender.sendMessage("order-queue", "Hello!");
    }




}
