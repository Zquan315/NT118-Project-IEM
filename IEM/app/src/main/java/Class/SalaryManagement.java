package Class;

public class SalaryManagement {
    private final String name, id;
    private final String basicSalary;
    private final int leaveDays;
    private final String totalSalary;

    public SalaryManagement(String id, String name, String basicSalary, int leaveDays, String totalSalary) {
        this.name = name;
        this.id = id;
        this.basicSalary = basicSalary;
        this.leaveDays = leaveDays;
        this.totalSalary = totalSalary;
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public String getBasicSalary() {
        return basicSalary;
    }

    public int getLeaveDays() {
        return leaveDays;
    }

    public String getTotalSalary() {
        return totalSalary;
    }
}

