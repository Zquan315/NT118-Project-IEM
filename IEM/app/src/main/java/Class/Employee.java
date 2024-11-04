package Class;

public class Employee {
    private final String name;
    private final String depart;
    private final String role;
    private final String id;
    private final String key;
    private final String phone;
    private final String email;
    private final String gender;


    public Employee(String name, String id, String key, String phone, String email, String department, String gender, String role) {
        this.name = name;
        this.id = id;
        this.key = key;
        this.phone = phone;
        this.email = email;
        this.depart = department;
        this.gender = gender;
        this.role = role;
    }


    public String getName() {
        return name;
    }
    public String getDepart() {
        return depart;
    }
    public String getRole() {return role;}
    public String getId() {return id;}
    public String getKey() {return key;}
    public String getPhone() {return phone;}
    public String getEmail() {return email;}
    public String getGender() {return gender;}


}
