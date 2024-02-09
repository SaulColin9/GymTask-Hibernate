package org.example.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@Configuration
public class JmsConfiguration {
//
//    @Bean
//    public Queue deleteTrainingQueue() {
//        return new ActiveMQQueue("training.delete.queue");
//    }
//
//    @Bean
//    public MessageConverter jacksonJmsMessageConverter() {
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();
//        typeIdMappings.put("_type", DeleteTrainingRequestDTO.class);
//        converter.setTypeIdMappings(typeIdMappings);
//        converter.setTargetType(MessageType.TEXT);
//        converter.setTypeIdPropertyName("_type");
//        return converter;
//    }
//
//    @Bean
//    public JmsListenerContainerFactory wareHouseFactory(ConnectionFactory factory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
//        DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
//        configurer.configure(containerFactory, factory);
//        return containerFactory;
//    }
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        return SQSConnectionFactory.builder()
//                .withAWSCredentialsProvider(new DefaultAWSCredentialsProviderChain())
//                .build();
//    }
//
//
//    @Bean
//    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory());
//        return factory;
//    }

}
