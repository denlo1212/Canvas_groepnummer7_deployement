package nl.hu.inno.hulp.application;
import nl.hu.inno.hulp.data.CourseRepository;
import nl.hu.inno.hulp.data.GroupRepository;
import nl.hu.inno.hulp.domain.*;
import nl.hu.inno.hulp.domain.id.CourseID;
import nl.hu.inno.hulp.domain.id.TeacherID;
import nl.hu.inno.hulp.presentation.controllers.NotFoundException;
import nl.hu.inno.hulp.presentation.dtos.response.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;;
    private final GroupRepository groupRepository;


    @Autowired
    public CourseService(CourseRepository courseRepository, GroupRepository groupRepository) {
        this.courseRepository = courseRepository;
        this.groupRepository = groupRepository;
    }


    public List<GroupDTO> generateClasses(Course course) throws NotFoundException {

        List<GroupDTO> groupDTOS = new ArrayList<>();
        List<Registration> registrations = course.getRegistrations();


//        note:
//        omdat er geen logica for the teaddachers is geïmplementeerd
//        is het tijdelijk gehardcode zodat deze methode werkt/getest kan worden
        List<TeacherID> teachers = new ArrayList<>();
        teachers.add(new TeacherID("T000001"));
        teachers.add(new TeacherID("T000002"));

        List<Group> classes =  ClassGenerator.generateClasses(registrations, teachers);

        for(Group currentClass: classes){
            groupDTOS.add(GroupDTO.build(currentClass)) ;
        }

        groupRepository.saveAll(classes);

        return groupDTOS;
    }
}