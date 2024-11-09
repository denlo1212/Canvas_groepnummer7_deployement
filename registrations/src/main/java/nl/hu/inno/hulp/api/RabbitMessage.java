package nl.hu.inno.hulp.api;

import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.domain.Student;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

//@Primary
//@Component
//public class RabbitMessage {
//
//    // Hier zou dus repository van studenten moeten staan, maar deze logica is niet geïmplementeerd
//    // Net als @Primary maar omdat in student module niks is geïmplementeerd, is deze klasse niet nodig
//
//    private final RabbitTemplate rabbitTemplate;
////    private final StudentRepository studentRepository;
//
//    public RabbitMessage(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//        this.rabbitTemplate.setExchange("student");
//        this.rabbitTemplate.setRoutingKey("studentId-key");
//    }
//
//    public void studentProducer() {
//        rabbitTemplate.convertAndSend("Can I get all the students?");
//    }
//
////    @RabbitListener(queues = "student-queue")
//    public void studentConsumer(List<Student> studentList) throws NotFoundException {
//        if(studentList != null){
////            studentRepository.saveAll(studentList);
//        }
//        throw new NotFoundException("could not find student with id");
//    }
//
//}
