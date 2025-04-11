package net.petstore.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic petTopic() {
        return new NewTopic("pet-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic userTopic() {
        return new NewTopic("user-topic", 1, (short) 1);
    }
}