package nl.hu.inno.hulp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Exchange exerciseExchange(){
        return ExchangeBuilder.directExchange("course").durable(true).build();
    }

    @Bean
    public Queue courseIdQueue(){
        return QueueBuilder.durable("courseId-queue").build();
    }

    @Bean
    public Queue courseQueue(){
        return QueueBuilder.durable("course-queue").build();
    }

    @Bean
    public Binding courseIdBinding(){
        return BindingBuilder.bind(courseIdQueue()).to(exerciseExchange()).with("courseId-key").noargs();
    }

    @Bean
    public Binding courseBinding(){
        return BindingBuilder.bind(courseQueue()).to(exerciseExchange()).with("course-key").noargs();
    }

    @Bean
    public MessageConverter getConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
