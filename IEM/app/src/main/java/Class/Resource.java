package Class;

public class Resource {
    private final String name, id;
    private int num;
    private boolean isHardware;;


    public Resource(String id, String name, int num, boolean isHardware) {
        this.name = name;
        this.id = id;
        this.num = num;
        this.isHardware = isHardware;

    }
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public int getNum() {
        return num;
    }
    public boolean getType() {
        return isHardware;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public void setType(boolean isHardware) {
        this.isHardware = isHardware;
    }
}
