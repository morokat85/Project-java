package T5.project;

public class LibraryStaff extends LibraryUser {

    private String staffID;

    public LibraryStaff(String name, String staffID){
        super(name);
        this.staffID = staffID;
    }

    public String getStaffID(){
        return staffID;
    }

    @Override
    public void borrowBook(){
        system.out.println(name + " (Library Staff) can borrow up to 10 books.");
    }
}