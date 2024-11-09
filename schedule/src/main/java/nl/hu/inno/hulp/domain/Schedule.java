package nl.hu.inno.hulp.domain;

public class Schedule {
    private ClassroomID classroomID;
    private TimeBlock timeBlock;
    private Group group;

    public Schedule(ClassroomID classroomID, TimeBlock timeBlock, Group group) {
        this.classroomID = classroomID;
        this.timeBlock = timeBlock;
        this.group = group;
    }
}
