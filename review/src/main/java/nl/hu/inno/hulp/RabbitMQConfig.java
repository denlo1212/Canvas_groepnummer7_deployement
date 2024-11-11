package nl.hu.inno.hulp;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private static final String DIRECT_EXCHANGE = "direct.exchange";
    private static final String SUBMISSION_ROUTING_KEY = "submission.key";

    // Define the direct exchange
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    // RabbitTemplate for sending messages
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange(DIRECT_EXCHANGE);
        template.setRoutingKey(SUBMISSION_ROUTING_KEY);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
