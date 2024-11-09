package nl.hu.inno.hulp.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ClassroomID {
    private String classroomID;

    public ClassroomID(String classroomID) {
        this.classroomID = classroomID;
    }

    protected ClassroomID() {
    }

    private boolean isValid(String classroomID) {
        // Example validation: classroom ID must be exactly 4 digits
        // 5080
        return classroomID.matches("^[0-9]{4}$");
    }

    public String getClassroomID() {
        return classroomID;
    }

    public void setClassroomID(String classroomID) {
        this.classroomID = classroomID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassroomID that)) return false;
        return Objects.equals(getClassroomID(), that.getClassroomID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getClassroomID());
    }

    @Override
    public String toString() {
        return classroomID;
    }
}
