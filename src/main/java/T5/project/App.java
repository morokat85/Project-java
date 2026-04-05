package T5.project;

import T5.project.Library.Library;
import T5.project.Sevice.LoginForm;

public class App {
    public static Library library = new Library();

    public static void main(String[] args) {
        new LoginForm();
    }
}