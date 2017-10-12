package sample.load

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class Application {
    
    @Value("\${loadtarget.host}")
    lateinit var targetHost: String;

    @Bean
    fun apis(passThroughHandler: PassThroughHandler) = router {
        POST("/passthrough/messages", passThroughHandler::handle)
    }
    
    @Bean
    fun webClient() : WebClient {
        return WebClient.builder()
                .baseUrl(targetHost)
                .build()
    }
    
    @Bean
    fun passThroughHandler(): PassThroughHandler {
        return PassThroughHandler(webClient())
    } 
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}