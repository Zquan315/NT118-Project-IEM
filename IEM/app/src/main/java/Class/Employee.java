package Class;

public class Employee {
    private final String name;
    private final String depart;
    private final String role;
    private final String id;

    public Employee(String name, String depart, String role, String id) {
        this.name = name;
        this.depart = depart;
        this.role = role;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDepart() {
        return depart;
    }

    public String getRole() {return role;}

    public String getId() {return id;}
}
