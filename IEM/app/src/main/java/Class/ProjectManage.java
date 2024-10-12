package Class;

public class ProjectManage {
    private final String id;
    private final String name;
    private final int progress;

    public ProjectManage(String id, String name, int progress) {
        this.name = name;
        this.id = id;
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }

    public int getProgress() {return progress;}

}
