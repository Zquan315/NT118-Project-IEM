package Class;

public class ProjectManage {
    private final String id;
    private final String name;
    private final String uderTake;

    public ProjectManage(String id, String name, String uderTake) {
        this.name = name;
        this.id = id;
        this.uderTake = uderTake;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }

    public String getUnderTake() {return uderTake;}

}
