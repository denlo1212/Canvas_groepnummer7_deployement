package nl.hu.inno.hulp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private static final String PEER_REVIEW_EXCHANGE  = "peer_review_topic_exchange";
    private static final String SUBMISSION_UPDATE_QUEUE = "submission_queue";
    private static final String SUBMISSION_UPDATE_ROUTING_KEY = "submission.update.*";
    private static final String TEACHER_REVIEW_EXCHANGE  = "teacher_review_topic_exchange";


    @Bean
    public Queue submissionUpdateQueue() {
        return QueueBuilder.durable(SUBMISSION_UPDATE_QUEUE).build();
    }
    @Bean
    public TopicExchange peerReviewExchange() {
        return ExchangeBuilder.topicExchange(PEER_REVIEW_EXCHANGE).durable(true).build();
    }
    @Bean
    public Binding submissionUpdateBinding(Queue submissionUpdateQueue, TopicExchange peerReviewExchange) {
        return BindingBuilder.bind(submissionUpdateQueue).to(peerReviewExchange).with(SUBMISSION_UPDATE_ROUTING_KEY);
    }

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

    @Bean
    public TopicExchange teacherReviewExchange() {
        return ExchangeBuilder.topicExchange(TEACHER_REVIEW_EXCHANGE).durable(true).build();
    }
    @Bean
    public Binding submissionUpdateBindingTeacherReview(Queue submissionUpdateQueue, TopicExchange teacherReviewExchange) {
        return BindingBuilder.bind(submissionUpdateQueue).to(teacherReviewExchange).with(SUBMISSION_UPDATE_ROUTING_KEY);
    }
}
