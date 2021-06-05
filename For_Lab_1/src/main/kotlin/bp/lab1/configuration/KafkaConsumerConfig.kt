package bp.lab1.configuration

import bp.lab1.POJO.DeliveryMessageInformation
import bp.lab1.POJO.DeliveryStatus
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory

import org.springframework.kafka.core.DefaultKafkaConsumerFactory

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StringDeserializer

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import java.util.HashMap

import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer


@Configuration
class KafkaConsumerConfig {
    @Bean
    fun consumerFactory(): ConsumerFactory<String, DeliveryStatus> {
        val config: MutableMap<String, Any> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        config[ConsumerConfig.GROUP_ID_CONFIG] = "FromDeliveryOrdersStatus"
//        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
//        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = DeliveryStatus::class.java
        val deserializer = JsonDeserializer<DeliveryStatus>()
        deserializer.addTrustedPackages("*")
        return DefaultKafkaConsumerFactory(config,org.apache.kafka.common.serialization.StringDeserializer(),deserializer)
    }

    @Bean
    fun deliveryListener(): ConcurrentKafkaListenerContainerFactory<String, DeliveryStatus> {
        val factory: ConcurrentKafkaListenerContainerFactory<String, DeliveryStatus> =
            ConcurrentKafkaListenerContainerFactory<String, DeliveryStatus>()
        factory.setConsumerFactory(consumerFactory())
        return factory
    }
}