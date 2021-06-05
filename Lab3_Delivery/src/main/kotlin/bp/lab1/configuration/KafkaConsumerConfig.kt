package bp.lab1.configuration


import bp.lab1.POJO.DeliveryMessageInformation
import bp.lab1.POJO.ItemStockMessage
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import java.util.*


@Configuration
class KafkaConsumerConfig {
    @Bean
    fun consumerFactory(): ConsumerFactory<String, DeliveryMessageInformation> {
        val config: MutableMap<String, Any> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        config[ConsumerConfig.GROUP_ID_CONFIG] = "OrderInfoToDelivery"
//        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
//        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        val deserializer = JsonDeserializer<DeliveryMessageInformation>()
        deserializer.addTrustedPackages("*")
        return DefaultKafkaConsumerFactory(config, StringDeserializer(), deserializer)
    }

    @Bean
    fun websiteOrdersListener(): ConcurrentKafkaListenerContainerFactory<String, DeliveryMessageInformation> {
        val factory: ConcurrentKafkaListenerContainerFactory<String, DeliveryMessageInformation> =
            ConcurrentKafkaListenerContainerFactory<String, DeliveryMessageInformation>()
        factory.setConsumerFactory(consumerFactory())
        return factory
    }

    @Bean
    fun warehouseConsumerFactory(): ConsumerFactory<String, ItemStockMessage> {
        val config: MutableMap<String, Any> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        config[ConsumerConfig.GROUP_ID_CONFIG] = "Item-Stock-Info-To-Delivery-From-Warehouse-Consumers"
//        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
//        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        val deserializer = JsonDeserializer<ItemStockMessage>()
        deserializer.addTrustedPackages("*")
        return DefaultKafkaConsumerFactory(config, StringDeserializer(), deserializer)
    }

    @Bean
    fun warehouseListener(): ConcurrentKafkaListenerContainerFactory<String, ItemStockMessage> {
        val factory: ConcurrentKafkaListenerContainerFactory<String, ItemStockMessage> =
            ConcurrentKafkaListenerContainerFactory<String, ItemStockMessage>()
        factory.setConsumerFactory(warehouseConsumerFactory())
        return factory
    }
}