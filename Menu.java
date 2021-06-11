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

class Menu {
    String menuID;
    String menuName;
    String menuPrice;
    String menuQuantity;

    String filePath="db/menu.xlsx";
    
    LinkedList userData = new LinkedList();
    UUID uuid = UUID.randomUUID();

    Scanner input = new Scanner(System.in);

    void setMenuID(){
        menuID=uuid.toString();
    }

    void setMenuName(){
        System.out.print("\nEnter your product name: ");
        menuName=input.nextLine();
    }

    void setMenuPrice(){
        System.out.print("\nEnter your price: ");
        menuPrice=input.nextLine();
    }

    void setMenuQuantity(){
        System.out.print("\nEnter your quantity: ");
        menuQuantity=input.nextLine();
    }


  
    int fileLastIndex(){
        int index=0;
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
    
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

    boolean addMenu(int lastIndex){
        boolean success=false;

        if(!validateFields()){
            System.out.println("\nPlease insert the fields properly!");
            return success;
        }

        try{
            FileOutputStream fileOut = null;
            FileInputStream fileIn = null;

            fileIn = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fileIn);
            XSSFSheet sheet = workbook.getSheet("Sheet1");
            Object[][] bookData = {
                {menuID,menuName,menuPrice,menuQuantity,"0"}
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
            
            fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            workbook.close();
            success=true;
        }
        catch(Exception e){
            System.out.println("Error: "+e);
        }

        return success;

    }

    boolean validateFields(){
        boolean validate=false;
        String priceRegex="^([+-]?\\d*\\.?\\d*)$";

        if(menuName!="" && menuPrice!="" && menuQuantity!=""){
            if(menuPrice.matches(priceRegex)){
                validate=true;
            }
            else{
                System.out.println("\nPrice pattern is incorrect!");
                validate=false;
            }
        }
        else{
            validate=false;
        }

        return validate;
    }

    void editMenu(){}
    void deleteMenu(){}
    void viewMenu(){}


}
