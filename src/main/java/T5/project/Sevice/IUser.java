package T5.project.Sevice;
//interface concept for user types 
// (BorrowUser, LibrarianUser) to implement common methods
public interface IUser {
 
    String getUserId();
    String getFullName();
    String getUsername();
    boolean isActive();
    boolean checkPassword(String input);
    boolean can(String action);
}
