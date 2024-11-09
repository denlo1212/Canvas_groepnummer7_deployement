package nl.hu.inno.hulp.domain.id;

import nl.hu.inno.hulp.presentation.controllers.NotFoundException;

import java.util.Objects;

public class GroupID {

    private String groupNumber;

    public GroupID(String groupNumber) throws NotFoundException {
        if (!isValid(groupNumber)) {
            throw new NotFoundException("Invalid group ID: " + groupNumber);
        }
        this.groupNumber = groupNumber;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    private boolean isValid(String className) {
        return className.matches("^[A-Z][a-zA-Z0-9 ]{1,15}$");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupID groupID = (GroupID) o;
        return Objects.equals(groupNumber, groupID.groupNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(groupNumber);
    }

    @Override
    public String toString() {
        return "GroupID{" +
                "groupNumber='" + groupNumber + '\'' +
                '}';
    }
}


