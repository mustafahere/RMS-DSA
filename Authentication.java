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
            FileInputStream file = new FileInputStream(new File("db/admins.xlsx"));
    
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
            FileInputStream file = new FileInputStream(new File("db/admins.xlsx"));
    
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
    boolean matchCredentials(){
        boolean match=false;
        try{

            boolean checkCredentials=userData.checkCredentials(getUserEmail(),encodePassword(getUserPassword()));

            if(checkCredentials){
                match=true;
            }
            else{
                match=false;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        return match;
    }
}

class Signup extends User{

    boolean addUser(int lastIndex){
        boolean success=false;

        if(checkEmail()){
            System.out.println("\nEmail Already Exist!");
            return success;
        }

        if(!validateFields()){
            System.out.println("\nPlease insert the fields properly!");
            return success;
        }

        try{
            FileOutputStream fileOut = null;
            FileInputStream fileIn = null;

            fileIn = new FileInputStream("db/admins.xlsx");
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

            fileOut = new FileOutputStream("db/admins.xlsx");
            workbook.write(fileOut);
            workbook.close();
            success=true;
        }
        catch(Exception e){
            System.out.println(e);
        }

        return success;

    }

    boolean validateFields(){
        boolean validate=false;
        String emailRegex="^(.+)@(.+)$";

        if(getUserPassword()!="" && getUserName()!="" && getUserEmail()!=""){
            
            if(getUserEmail().matches(emailRegex)){
                validate=true;
            }
            else{
                System.out.println("\nEmail pattern is incorrect!");
                validate=false;
            }
            
            
        }
        else{
            validate=false;
        }

        return validate;
    }

}