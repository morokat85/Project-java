package T5.project;

public class Student {
    private String fullName;
    private String studentID;
    private String gender;
    private int age;


    public student_info(String fullName, String studentID, string gender, int age){
         setFullName(fullName);
         setStudentID(studentID);
         setGender(studentID);
         setAge(age);
    }
    
    public String getFullName(){
        return fullName;
    }
    public String getStudentID(){
        return studentID;
    }
    public String getGender(){
        return gender;
    }
    public int getAge(){
        return age;
    }

    public void setFullName(String fullName){
        if(fullName.isBlank()){
            throw new IllegalAccessException("Name is empty, please enter name")
        }
        return this.fullName;
    }
    public void setStudentID(String studentID){
        if(studentID.isBlank()){
            throw new IllegalAccessException("ID is empty, please enter ID")
        }
        return this.studentID;
    }
    public void setGender(String gender){
        if (gender == null || !gender.equals("F") || !gender.equals("M")){
            throw new IllegalAccessException("only F for female and M for male");
        }
        return this.gender;
    }
    public void setAge(int age){
        if (age <= 0 || age >=100 ){
            throw new IllegalAccessException("age not negative");
        }
        return this.age;
    }
}
