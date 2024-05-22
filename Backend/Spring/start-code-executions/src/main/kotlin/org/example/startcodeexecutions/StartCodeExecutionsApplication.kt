package org.example.startcodeexecutions

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.SmartInitializingSingleton
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@SpringBootApplication
class StartCodeExecutionsApplication

fun main(args: Array<String>) {
    runApplication<StartCodeExecutionsApplication>(*args)
}

@Service
class MyService {
    init {
        println("MyService init")
    }

    @PostConstruct
    private fun postConstruct() {
        println("MyService postConstruct")
    }

    @EventListener(ApplicationReadyEvent::class)
    private fun onApplicationReadyEvent() {
        println("MyService onApplicationReadyEvent")
    }
}

@Component
class MyComponent : ApplicationListener<ApplicationReadyEvent> {
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        println("MyComponent onApplicationEvent")
    }
}

@Configuration
class MyConfig {
    @Bean
    fun smartInitializingSingleton(): SmartInitializingSingleton {
        return SmartInitializingSingleton {
            println("SmartInitializingSingleton called")
        }
    }

    @Bean
    fun commandLineRunner(): CommandLineRunner {
        return CommandLineRunner {
            println("CommandLineRunner called")
        }
    }

    @Bean
    fun applicationRunner(): ApplicationRunner {
        return ApplicationRunner {
            println("ApplicationRunner called")
        }
    }
}
