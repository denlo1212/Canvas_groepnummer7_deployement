package nl.hu.inno.hulp.domain;

import nl.hu.inno.hulp.domain.id.StudentID;
import nl.hu.inno.hulp.domain.id.TeacherID;
import nl.hu.inno.hulp.presentation.controllers.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassGeneratorTest {

    private List<Registration> registrationsWithPreference;
    private List<Registration> registrationsWithoutPreference;
    private List<TeacherID> teachers;
    List<Registration> largePreferenceGroup = new ArrayList<>();

    @BeforeEach
    void setUp() throws NotFoundException {
        teachers = new ArrayList<>();
        teachers.add(new TeacherID("T000001"));
        teachers.add(new TeacherID("T000002"));

        registrationsWithPreference = new ArrayList<>();
        List<StudentID> studentIDs = List.of(
                new StudentID("1888123"),
                new StudentID("1888124"),
                new StudentID("1888125"),
                new StudentID("1888126"),
                new StudentID("1888127")
        );

        List<StudentID> preferences = List.of(
                studentIDs.get(1),  // John prefers Jane
                studentIDs.get(0),  // Jane prefers John
                studentIDs.get(3),  // Alice prefers Bob
                studentIDs.get(2),  // Bob prefers Alice
                studentIDs.get(3)   // Charlie prefers Bob
        );

        for (int i = 0; i < studentIDs.size(); i++) {
            Registration registration = Mockito.mock(Registration.class);
            Preference preference = new Preference();
            preference.addStudentPreference(preferences.get(i));
            when(registration.getStudentID()).thenReturn(studentIDs.get(i));
            when(registration.getPreference()).thenReturn(preference);
            registrationsWithPreference.add(registration);
        }

        registrationsWithoutPreference = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Registration registration = Mockito.mock(Registration.class);
            when(registration.getStudentID()).thenReturn(new StudentID("188812" + (8 + i)));
            registrationsWithoutPreference.add(registration);
        }

        largePreferenceGroup = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Registration registration = Mockito.mock(Registration.class);
            StudentID studentID = new StudentID(Integer.toString(1000000 + (i + 1)));
            Preference preference = new Preference();
            preference.addStudentPreference(new StudentID("1000001"));
            when(registration.getStudentID()).thenReturn(studentID);
            when(registration.getPreference()).thenReturn(preference);
            largePreferenceGroup.add(registration);
        }
    }


    @AfterEach
    void tearDown() {
        registrationsWithPreference.clear();
        registrationsWithoutPreference.clear();
        teachers.clear();
        largePreferenceGroup.clear();
    }

    @Test
    void testAmountOfClasses() throws NotFoundException {
        List<Group> classes = ClassGenerator.generateClasses(registrationsWithPreference, teachers);
        assertEquals(teachers.size(), classes.size(), "Number of classes should match the number of teachers.");
    }

    @Test
    void testStudentPreference() throws NotFoundException {
        List<Group> classes = ClassGenerator.generateClasses(registrationsWithPreference, teachers);
        Group class1 = classes.get(0);
        Group class2 = classes.get(1);

        assertTrue(class1.getStudents().contains(registrationsWithPreference.get(0).getStudentID()) ||
                class2.getStudents().contains(registrationsWithPreference.get(0).getStudentID()), "John should be in a class.");
        assertTrue(class1.getStudents().contains(registrationsWithPreference.get(1).getStudentID()) ||
                class2.getStudents().contains(registrationsWithPreference.get(1).getStudentID()), "Jane should be in a class.");

        assertTrue(class1.getStudents().contains(registrationsWithPreference.get(2).getStudentID()) ||
                class2.getStudents().contains(registrationsWithPreference.get(2).getStudentID()), "Alice should be in a class.");
        assertTrue(class1.getStudents().contains(registrationsWithPreference.get(3).getStudentID()) ||
                class2.getStudents().contains(registrationsWithPreference.get(3).getStudentID()), "Bob should be in a class.");

        assertTrue(class1.getStudents().contains(registrationsWithPreference.get(4).getStudentID()) ||
                class2.getStudents().contains(registrationsWithPreference.get(4).getStudentID()), "Charlie should be in a class with Bob.");
    }

    @Test
    void testRegistrationsWithoutPreference() throws NotFoundException {
        List<Group> classes = ClassGenerator.generateClasses(registrationsWithoutPreference, teachers);
        Group class1 = classes.get(0);
        Group class2 = classes.get(1);

        assertTrue(class1.getStudents().contains(registrationsWithoutPreference.get(0).getStudentID()) ||
                class2.getStudents().contains(registrationsWithoutPreference.get(0).getStudentID()), "Student 1 should be assigned to a class.");
        assertTrue(class1.getStudents().contains(registrationsWithoutPreference.get(1).getStudentID()) ||
                class2.getStudents().contains(registrationsWithoutPreference.get(1).getStudentID()), "Student 2 should be assigned to a class.");

        assertEquals(class1.getStudents().size(), class2.getStudents().size(), "The classes should have evenly distributed students");
    }

    @Test
    void testEvenDistribution() throws NotFoundException {
        List<Registration> mixedRegistrations = new ArrayList<>(registrationsWithPreference);
        mixedRegistrations.addAll(registrationsWithoutPreference);

        List<Group> classes = ClassGenerator.generateClasses(mixedRegistrations, teachers);
        Group class1 = classes.get(0);
        Group class2 = classes.get(1);

        assertEquals(class1.getStudents().size(), class2.getStudents().size(), "The classes should have evenly distributed students");

        int maxCapacity = (mixedRegistrations.size() + teachers.size() - 1) / teachers.size();
        assertTrue(class1.getStudents().size() <= maxCapacity, "Class 1 should not exceed max capacity.");
        assertTrue(class2.getStudents().size() <= maxCapacity, "Class 2 should not exceed max capacity.");
    }

    @Test
    void testLargeGroupRedistribution() throws NotFoundException {
        List<Group> classes = ClassGenerator.generateClasses(largePreferenceGroup, teachers);
        for(Group group: classes){
            assertTrue(group.getStudents().size() <= (largePreferenceGroup.size())/ 2);
        }
    }
}
