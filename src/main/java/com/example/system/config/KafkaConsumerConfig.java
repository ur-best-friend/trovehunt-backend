package com.example.system.config;

import com.example.system.dto.messagebroker.user.ImportUserDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@ConditionalOnProperty(value = "kafka.enabled", matchIfMissing = true)
public class KafkaConsumerConfig {
    @Value("${kafka.bootstrap-address}")
    private String bootstrapAddress;
    private static String CONSUMER_GROUP_ID = "trovehunt_api";
    public static final String TOPIC_IMPORT_USER = "trovehunt_import_user";

    @Bean
    public ConsumerFactory<String, ImportUserDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(ImportUserDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ImportUserDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ImportUserDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public NewTopic importUserTopic() {
        return new NewTopic(TOPIC_IMPORT_USER, 1, (short) 1);
    }
}
