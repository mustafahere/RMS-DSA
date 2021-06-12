import java.util.Scanner;

public class Resturant {

    public static void main(String[] args) {
        projectInterface pi = new projectInterface();
        pi.Header();

        String [] homeOptions={"Are you a customer?","Are you a admin?"};

        pi.options(homeOptions);
        int choice=pi.userChoice();

        if(choice==1){
            pi.customerFunctionality();
        }
        else if(choice==2){
            pi.clearScreen();
            pi.Header();
            String [] homeOptions2={"Login","SignUp","Exit"};

            pi.options(homeOptions2);
            int choice2=pi.userChoice();


            switch(choice2){
                case 1:
                    if(pi.loginFunctionality()){
                        pi.dashboardFunctionality();
                    }
                    break;
                
                case 2:
                    if(pi.signUpFunctionality()){
                        pi.clearScreen();
                        if(pi.loginFunctionality()){
                            pi.dashboardFunctionality();
                        }
                    }
                    break;
                case 3:
                    pi.clearScreen();
                    String [] dummy={""};
                    main(dummy);
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }

        else{
            pi.clearScreen();
            String [] dummy={""};
            main(dummy);
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

    void options(String [] homeOptions){
        System.out.println("Please select a option: ");

        for(int i=0;i<homeOptions.length;i++){
            System.out.println((i+1)+"- "+homeOptions[i]);
        }
        
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


    void signUp(){
        System.out.println("\n---------------------------------------");
        System.out.println("\t\tSIGNUP");
        System.out.println("-----------------------------------------\n");
    }

    void login(){
        System.out.println("\n-------------------------------------");
        System.out.println("\t\tLOGIN");
        System.out.println("---------------------------------------\n");
    }

    void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    boolean loginFunctionality(){
        Login login = new Login();
        login.getFile();
        clearScreen();
        boolean success=false;
        while(success==false){
            login();
            login.setUserEmail();
            login.setUserPassword();
            if(login.matchCredentials()){
                success=true;
            }
            else{
                System.out.println("\nIncorrect email or password!");
                success=false;
            }
        }
        
        return success;
    }

    boolean signUpFunctionality(){

        Signup signup = new Signup();
        boolean success=false;
        while(success==false){
            signUp();
            signup.setUserID();
            signup.setUserName();
            signup.setUserEmail();
            signup.setUserPassword();
            signup.getFile();
            if(signup.addUser(signup.fileLastIndex())){
                success=true;
            }
            else{
                success=false;
            }
        }

        return success;
    }


    void customerFunctionality(){
        clearScreen();
        Header();

        String [] homeOptions={"Menu","Order","Exit"};

        options(homeOptions);

        int choice = userChoice();

        switch (choice) {
            case 1:
                customerMenuOptions();
                break;

            case 2:
                customerOrderOptions();
                break;

            case 3:
                clearScreen();
                String [] dummy={""};
                Resturant res = new Resturant();
                res.main(dummy);
                break;

            default:
                customerFunctionality();
                break;
        }


    }


    void customerMenuOptions(){
        clearScreen();
        Header();
        System.out.println("\n-------------------------------------");
        System.out.println("\t\tMENU");
        System.out.println("---------------------------------------\n");

        String [] homeOptions={"View Menu","Exit"};

        options(homeOptions);

        int choice = userChoice();
        
        switch (choice) {
            case 1:
                
                break;
            case 2:
                customerFunctionality();
                break;
        
            default:
                customerMenuOptions();
                break;
        }
    }

    void customerOrderOptions(){
        clearScreen();
        Header();
        System.out.println("\n-------------------------------------");
        System.out.println("\t\tORDER");
        System.out.println("---------------------------------------\n");

        String [] homeOptions={"Place Order","Exit"};

        options(homeOptions);

        int choice = userChoice();

        switch (choice) {
            case 1:
                
                break;
            case 2:
                customerFunctionality();
                break;
        
            default:
                customerOrderOptions();
                break;
        }

    }

    void dashboard(){
        System.out.println("\n-------------------------------------");
        System.out.println("\t\tDASHBOARD");
        System.out.println("---------------------------------------\n");
    }

    void dashboardFunctionality(){
        clearScreen();
        Header();
        dashboard();

        String [] homeOptions={"Menu","Orders","Profits","Logout"};

        options(homeOptions);

        int choice = userChoice();


        switch (choice) {
            case 1:
                menuOptions();
                break;

            case 2:
                orderOptions();
                break;

            case 3:
                profitOptions();
                break;

            case 4:
                clearScreen();
                String [] dummy={""};
                Resturant res = new Resturant();
                res.main(dummy);
                break;

            default:
                dashboardFunctionality();
                break;
        }

    }

    void menuOptions(){
        clearScreen();
        Header();
        System.out.println("\n-------------------------------------");
        System.out.println("\t\tMENU");
        System.out.println("---------------------------------------\n");

        String [] homeOptions={"Add Menu","Update Menu","Delete Menu","View Menu","Exit"};

        options(homeOptions);

        int choice = userChoice();

        Menu menu = new Menu();

        switch (choice) {

            case 1:
                boolean continueLoop=true;
                while(continueLoop==true){
                    menu.setMenuID();
                    menu.setMenuName();
                    menu.setMenuPrice();
                    menu.setMenuQuantity();
                    menu.getFile();
                    if(menu.addMenu(menu.fileLastIndex())){
                        System.out.println("\nMenu added successfully!");
                        System.out.println("\nDo you want to continue or go back?");
                        System.out.print("\nEnter your choice: ");
                        int choice2=userChoice();

                        if(choice2==1){
                            continueLoop=true;
                        }
                        else{
                            continueLoop=false;
                        }

                    } 
                
                }
                menuOptions();
                break;


            case 2:
                boolean continueLoop2=true;
                clearScreen();
                Header();
                System.out.println("\n-------------------------------------");
                System.out.println("\t\tEdit MENU");
                System.out.println("---------------------------------------\n");

                while(continueLoop2==true){
                   
                    menu.setMenuName();
                    menu.getFile();
                    if(menu.editMenu()){
                        System.out.println("\nDo you want to continue or go back?");
                        System.out.print("\nEnter your choice: ");
                        int choice2=userChoice();

                        if(choice2==1){
                            continueLoop2=true;
                        }
                        else{
                            break;
                        }
                    }
                    else{
                        continueLoop2=true;
                    }

                }

                menuOptions();

                break;
            case 3:
                boolean continueLoop3=true;
                clearScreen();
                Header();
                System.out.println("\n-------------------------------------");
                System.out.println("\t\tDelete MENU");
                System.out.println("---------------------------------------\n");
                while(continueLoop3==true){
                   
                    menu.setMenuName();
                    menu.getFile();
                    if(menu.deleteMenu()){
                        System.out.println("\nDo you want to continue or go back?");
                        System.out.print("\nEnter your choice: ");
                        int choice2=userChoice();

                        if(choice2==1){
                            continueLoop3=true;
                        }
                        else{
                            break;
                        }
                    }
                    else{
                        continueLoop3=true;
                    }

                }

                menuOptions();
                break;   

            case 4:
                clearScreen();
                Header();
                System.out.println("\n-------------------------------------");
                System.out.println("\t\tVIEW MENU");
                System.out.println("---------------------------------------\n");
                menu.viewMenu();

                System.out.println("\nDo you want to go back?");
                System.out.print("\nEnter your choice: ");
                int choice2=userChoice();

                if(choice2==1){
                    menuOptions();
                }
                else{
                    menuOptions();
                }

                break;
            
            case 5:
                dashboardFunctionality();
                break;
        
            default:
                menuOptions();
                break;
        }

    }

    void profitOptions(){
        clearScreen();
        Header();
        System.out.println("\n-------------------------------------");
        System.out.println("\t\tPROFIT");
        System.out.println("---------------------------------------\n");

        String [] homeOptions={"View Profits","Exit"};

        options(homeOptions);

        int choice = userChoice();

        switch (choice) {
            case 2:
                dashboardFunctionality();
                break;
        
            default:
                profitOptions();
                break;
        }

    }

    void orderOptions(){
        clearScreen();
        Header();
        System.out.println("\n-------------------------------------");
        System.out.println("\t\tORDERS");
        System.out.println("---------------------------------------\n");

        String [] homeOptions={"View Orders","Update Orders","Exit"};

        options(homeOptions);

        int choice = userChoice();

        switch (choice) {
            case 3:
                dashboardFunctionality();
                break;
        
            default:
                orderOptions();
                break;
        }
    }
}
