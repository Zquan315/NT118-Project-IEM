package Class;

public class ProjectManage {
    private final String id;
    private final String name;
    private final String underTake;
    private final String description;
    private final String deadline;

    public ProjectManage(String id, String name, String underTake, String description, String deadline) {
        this.name = name;
        this.id = id;
        this.underTake = underTake;
        this.description = description;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getDeadline() {
        return deadline;
    }

    public String getID() {
        return id;
    }

    public String getUnderTake() {return underTake;}

}
