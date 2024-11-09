package nl.hu.inno.hulp.domain;

import nl.hu.inno.hulp.domain.id.GroupID;
import nl.hu.inno.hulp.domain.id.StudentID;
import nl.hu.inno.hulp.domain.id.TeacherID;
import nl.hu.inno.hulp.presentation.controllers.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Note: een generator maken op basis van mensen hun 1e, 2de, 3de keuzen en ze dan groeperen is extreem complex
// en buiten de scope van dit project op het moment
// voor deze reden is er voor een simpelere manier gegaan dat een student met 1 van hun preferences terecht komt
// geen van deze preferences hebben een prioriteit
// als iemand hier geïnteresseerd in is om dit te implementeren is hier een start punt:
// https://www.reddit.com/r/algorithms/comments/uwukwe/sorting_people_into_groups_based_on_preferences/
// hiernaast kan je zoeken voor de staple marriage problem dit algoritme ervoor is in principe hetzelfde concept


//
public class ClassGenerator {

    public static List<Group> generateClasses(List<Registration> registrationList, List<TeacherID> teachers) throws NotFoundException {
        List<Group> classes = initializeClasses(teachers);

        List<Registration> unassignedStudents = new ArrayList<>(registrationList);
        Map<Preference, List<Registration>> preferenceGroups = groupByPreferences(registrationList);

        int numberOfClasses = classes.size();
        int maxCapacity = (int) Math.ceil((double) registrationList.size() / numberOfClasses);

        assignPreferenceGroupsToClasses(preferenceGroups, classes, maxCapacity);
        assignLeftoverStudents(unassignedStudents, classes, maxCapacity);

        return classes;
    }

    private static List<Group> initializeClasses(List<TeacherID> teachers) throws NotFoundException {
        List<Group> classes = new ArrayList<>();
        for (int i = 0; i < teachers.size(); i++) {
            Group schoolClass = new Group(new GroupID("Class " + (i + 1)));
            schoolClass.addTeacher(teachers.get(i));
            classes.add(schoolClass);
        }
        return classes;
    }

    private static Map<Preference, List<Registration>> groupByPreferences(List<Registration> registrations) {
//        https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html
        return registrations.stream()
                .filter(registration -> registration.getPreference() != null)
                .collect(Collectors.groupingBy(Registration::getPreference));
    }

    private static void assignPreferenceGroupsToClasses(Map<Preference, List<Registration>> preferenceGroups, List<Group> classes, int maxCapacity) {
        for (Map.Entry<Preference, List<Registration>> entry : preferenceGroups.entrySet()) {
            List<Registration> groupOfStudents = entry.getValue();
            if (groupOfStudents.size() <= maxCapacity) {
                assignGroupToClass(groupOfStudents, classes, maxCapacity);
            } else {
                redistributeLargeGroup(groupOfStudents, classes, maxCapacity);
            }
        }
    }

    private static void assignGroupToClass(List<Registration> groupOfStudents, List<Group> classes, int maxCapacity) {
        for (Group schoolClass : classes) {
            if (schoolClass.getStudents().size() + groupOfStudents.size() <= maxCapacity) {
                List<StudentID> studentIDs = groupOfStudents.stream()
                        .map(Registration::getStudentID)
                        .collect(Collectors.toList());

                schoolClass.addStudents(studentIDs);
                break;
            }
        }
    }

    private static void redistributeLargeGroup(List<Registration> group, List<Group> classes, int maxCapacity) {
        int start = 0;
        int groupSize = group.size();

        while (start < groupSize) {
            int end = Math.min(start + maxCapacity, groupSize);
            List<Registration> subGroup = group.subList(start, end);
            assignGroupToClass(subGroup, classes, maxCapacity);
            start = end;
        }
    }

    private static void assignLeftoverStudents(List<Registration> unassignedRegistrations, List<Group> classes, int maxCapacity) {
        for (Registration registration : unassignedRegistrations) {
            for (Group group : classes) {
                if (group.getStudents().size() < maxCapacity) {
                    group.addStudents(List.of(registration.getStudentID()));
                    break;
                }
            }
        }
    }
}


