import java.util.Scanner;

public class Resturant {
    public static void main(String[] args) {
        projectInterface pi = new projectInterface();
        pi.Header();
        pi.initialScreen();
        int choice=pi.userChoice();


        switch(choice){
            case 1:
                Login login = new Login();
                login.getFile();
                login.setUserEmail();
                login.setUserPassword();
                login.matchCredentials();
                break;
            case 2:
                Signup signup = new Signup();
                signup.setUserID();
                signup.setUserName();
                signup.setUserEmail();
                signup.setUserPassword();
                signup.getFile();
                signup.addUser(signup.fileLastIndex());
                break;

            default:
                System.out.println("Invalid choice!");
        }

    }
}

class projectInterface{
    Scanner obj = new Scanner(System.in);

    void Header(){
        System.out.println("\n-----------------------------------");
        System.out.println("Resturant Management System");
        System.out.println("-----------------------------------\n");
    }

    void initialScreen(){
        System.out.println("Please select a option: ");
        System.out.println("1- Login");
        System.out.println("2- Signup");
        System.out.print("\nEnter your choice: ");
    }

    int userChoice(){
        int choice=0;
        try{
            choice = obj.nextInt();
        }
        catch(Exception e){
            System.out.println("\nError: "+e);
        }

        return choice;
    }


}
