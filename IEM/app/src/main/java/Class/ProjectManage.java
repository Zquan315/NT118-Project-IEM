package Class;

public class ProjectManage {
    private final String id;
    private final String name;
    private final String underTake;
    private final String description;

    public ProjectManage(String id, String name, String underTake, String description) {
        this.name = name;
        this.id = id;
        this.underTake = underTake;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public String getID() {
        return id;
    }

    public String getUnderTake() {return underTake;}

}
