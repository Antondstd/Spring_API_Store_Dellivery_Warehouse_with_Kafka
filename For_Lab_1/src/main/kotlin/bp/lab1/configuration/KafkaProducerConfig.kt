package bp.lab1.configuration

import org.springframework.kafka.core.KafkaTemplate

import org.springframework.kafka.core.DefaultKafkaProducerFactory

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import java.util.HashMap

import org.springframework.kafka.core.ProducerFactory

import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
@EnableKafka
class KafkaProducerConfig {
    @Bean
    fun producerFactory(): ProducerFactory<String, Any> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        return DefaultKafkaProducerFactory<String, Any>(configProps)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, *> {
        return KafkaTemplate<String, Any>(producerFactory())
    }
}