package nl.hu.inno.hulp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    private static final String DIRECT_EXCHANGE = "direct.exchange";
    private static final String SUBMISSION_QUEUE = "submission.queue";
    private static final String DL_QUEUE = "dead.letter.queue";
    private static final String SUBMISSION_ROUTING_KEY = "submission.key";
    private static final String DL_ROUTING_KEY = "dead.letter.routing.key";

    // Define Direct Exchange
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    // Define Dead Letter Queue
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DL_QUEUE);
    }

    // Define Main Queue with Dead Letter Exchange settings
    @Bean
    public Queue mainQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DIRECT_EXCHANGE); 
        args.put("x-dead-letter-routing-key", DL_ROUTING_KEY);
        args.put("x-message-ttl", 5000); // Time-to-live for retry messages (in milliseconds)
        return new Queue(SUBMISSION_QUEUE, true, false, false, args);
    }

    // Bind Main Queue to Direct Exchange
    @Bean
    public Binding mainQueueBinding() {
        return BindingBuilder.bind(mainQueue()).to(directExchange()).with(SUBMISSION_ROUTING_KEY);
    }

    // Bind Dead Letter Queue to Direct Exchange
    @Bean
    public Binding deadLetterQueueBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(directExchange()).with(DL_ROUTING_KEY);
    }

    // Define RabbitListenerContainerFactory with Retry Mechanism
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);

        // Retry Template with a max attempt of 3
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(3); // Retry 3 times
        retryTemplate.setRetryPolicy(retryPolicy);

        factory.setRetryTemplate(retryTemplate);

        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
