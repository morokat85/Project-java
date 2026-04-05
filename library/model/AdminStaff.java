package model;

// ============================================================
// CONCEPT: Inheritance  ("extends Staff")
// AdminStaff INHERITS everything from Staff automatically.
// It only needs to add its own unique stuff (salary, can()).
// ============================================================
public class AdminStaff extends Staff {

    // CONCEPT: Reference type (Double object vs primitive double)
    private double salary;  // primitive double — stores a number directly

    // CONCEPT: Constructor calling parent constructor with "super(...)"
    public AdminStaff(String staffId, String fullName, String username, String password, double salary) {
        super(staffId, fullName, username, password); // call Staff constructor
        setSalary(salary);
    }

    // CONCEPT: Overriding abstract method
    // Admin can do EVERYTHING
    @Override
    public boolean can(String action) {
        return true;
    }

    public double getSalary() { return salary; }

    public void setSalary(double salary) {
        if (salary < 0) this.salary = 0;
        else this.salary = salary;
    }

    @Override
    public String toString() {
        return super.toString() + " | Role: Admin | Salary: $" + salary;
    }
}
