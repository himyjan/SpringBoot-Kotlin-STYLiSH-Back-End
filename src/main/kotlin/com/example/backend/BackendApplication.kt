package com.example.backend

import com.example.backend.storage.StorageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.listener.PatternTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter

@SpringBootApplication class BackendApplication

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}

@Configuration
class MessagingRedisConfig {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(MessagingRedisConfig::class.java)
    }

    @Bean
    fun container(
        connectionFactory: RedisConnectionFactory,
        listenerAdapter: MessageListenerAdapter
    ): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(connectionFactory)
        container.addMessageListener(listenerAdapter, PatternTopic("chat"))
        return container
    }

    @Bean
    fun listenerAdapter(receiver: Receiver): MessageListenerAdapter {
        return MessageListenerAdapter(receiver, "receiveMessage")
    }

    @Bean
    fun receiver(): Receiver {
        return Receiver()
    }

    @Bean
    fun template(connectionFactory: RedisConnectionFactory): StringRedisTemplate {
        return StringRedisTemplate(connectionFactory)
    }
}

fun init(storageService: StorageService): CommandLineRunner {
    return CommandLineRunner { args ->
        storageService.deleteAll()
        storageService.init()
    }
}

fun demo(repository: CustomerRepository): CommandLineRunner {
    val log: Logger = LoggerFactory.getLogger(BackendApplication::class.java)

    return CommandLineRunner {
        // save a few customers
        repository.save(Customer("Jack", "Bauer"))
        repository.save(Customer("Chloe", "O'Brian"))
        repository.save(Customer("Kim", "Bauer"))
        repository.save(Customer("David", "Palmer"))
        repository.save(Customer("Michelle", "Dessler"))

        // fetch all customers
        log.info("Customers found with findAll():")
        log.info("-------------------------------")
        repository.findAll().forEach { customer -> log.info(customer.toString()) }
        log.info("")

        // fetch an individual customer by ID
        val customer = repository.findById(1L)
        log.info("Customer found with findById(1L):")
        log.info("--------------------------------")
        log.info(customer.toString())
        log.info("")

        // fetch customers by last name
        log.info("Customer found with findByLastName('Bauer'):")
        log.info("--------------------------------------------")
        repository.findByLastName("Bauer").forEach { bauer -> log.info(bauer.toString()) }
        log.info("")
    }
}
