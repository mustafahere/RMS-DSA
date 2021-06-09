import java.util.Scanner;
import java.util.UUID;
import java.util.Base64;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  

class User{

    private String userID;
    private String userName;
    private String userPassword;
    private String userEmail;
    
    LinkedList userData = new LinkedList();
    UUID uuid = UUID.randomUUID();

    Scanner input = new Scanner(System.in);

    void setUserID(){
        userID=uuid.toString();
    }

    void setUserName(){
        System.out.print("\nEnter your username: ");
        userName=input.nextLine();
    }

    void setUserEmail(){
        System.out.print("\nEnter your user email: ");
        userEmail=input.nextLine();
    }

    void setUserPassword(){
        System.out.print("\nEnter your user password: ");
        userPassword=input.nextLine();
        userPassword=encodePassword(userPassword);
    }

    String getUserID(){
        return userID;
    }

    String getUserName(){
        return userName;
    }

    String getUserEmail(){
        return userEmail;
    }

    String getUserPassword(){
        userPassword=decodePassword(userPassword);
        return userPassword;
    }

    
    String encodePassword(String pass){
        Base64.Encoder encoder = Base64.getEncoder(); 
        pass=encoder.encodeToString(pass.getBytes());
        return pass;
    }

    String decodePassword(String pass){
        Base64.Decoder decoder = Base64.getDecoder(); 
        pass=new String(decoder.decode(pass));
        return pass;
    }


    boolean checkEmail(){
        boolean check=false;

        check=userData.checkEmail(getUserEmail());

        return check;
    }

    int fileLastIndex(){
        int index=0;
        try {
            FileInputStream file = new FileInputStream(new File("db/users.xlsx"));
    
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
    
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
    
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                index++;
            }
            workbook.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    
    void getFile(){
        boolean afterFirstRow=false;
        try {
            FileInputStream file = new FileInputStream(new File("db/users.xlsx"));
    
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
    
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
    
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                String [] arr = new String[4];
                while (cellIterator.hasNext()) 
                {
                    Cell cell = cellIterator.next();
                    // System.out.println("Index: "+cell.getColumnIndex()+"\n");
                    
                    // System.out.println("Value: "+cell+"\n");
                    if(cell.toString()!=""){
                        arr[cell.getColumnIndex()]=cell.toString();
                    }

                }

                if(afterFirstRow){
                    userData.insert_end(arr);
                }
                afterFirstRow=true;
            }
            workbook.close();
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

class Login extends User{
    void matchCredentials(){

        try{
            if(userData.checkEmail(getUserEmail()) && userData.checkPassword(encodePassword(getUserPassword()))){
                System.out.println("Dashboard");
            }
            else{
                System.out.println("Inorrect email or password!");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }


    }
}

class Signup extends User{

    void addUser(int lastIndex){

        if(checkEmail()){
            System.out.println("\nEmail Already Exist!");
            return;
        }

        try{
            FileOutputStream fileOut = null;
            FileInputStream fileIn = null;

            fileIn = new FileInputStream("db/users.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(fileIn);
            XSSFSheet sheet = workbook.getSheet("Sheet0");

            Object[][] bookData = {
                {getUserID(),getUserName(),getUserEmail(),encodePassword(getUserPassword())}
            };
        
            for (Object[] aBook : bookData) {
                Row row = sheet.createRow(lastIndex);
                int columnCount = 0;
                
                for (Object field : aBook) {
                    Cell cell = row.createCell(columnCount);
                    columnCount++;
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }
                
            }

            fileOut = new FileOutputStream("db/users.xlsx");
            workbook.write(fileOut);
            workbook.close();
            System.out.println("\nYou are registerd successfully!");
        }
        catch(Exception e){
            System.out.println(e);
        }

    }

}