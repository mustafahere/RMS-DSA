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
    
    LinkedList menuData = new LinkedList();
    UUID uuid = UUID.randomUUID();

    Scanner input = new Scanner(System.in);


    void setMenuID(){
        menuID=UUID.randomUUID().toString();
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

    boolean checkName(){
        boolean check=false;
        check=menuData.checkMenu(menuName);
        return check;
    }

    void getFile(){
        boolean afterFirstRow=false;
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
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                String [] arr = new String[4];
                String [] eachRow = new String[5];
                boolean dontadd=false;
                while (cellIterator.hasNext()) 
                {
                    Cell cell = cellIterator.next();
                    eachRow[cell.getColumnIndex()]=cell.toString();

                    if(cell.getColumnIndex()==4){
                        if(eachRow[4].equals("0")){
                            arr[0]=eachRow[0];
                            arr[1]=eachRow[1];
                            arr[2]=eachRow[2];
                            arr[3]=eachRow[3];
                        }
                        else{
                            dontadd=true;
                        }
                    }

                }

                if(afterFirstRow && !dontadd){
                    menuData.insert_end(arr);
                }
                afterFirstRow=true;
            }
            workbook.close();
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


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

        if(checkName()){
            System.out.println("\nProduct Already Exist!");
            return success;
        }


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
                {menuID,menuName,menuPrice.toString(),menuQuantity,"0"}
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

    boolean editMenu(){
        boolean success=false;

        if(checkName()){
                getFile();
                String [] menuRow = menuData.getRow(menuName);
                
                System.out.println("\nPrice: "+menuRow[2]);
                System.out.println("Quantity: "+menuRow[3]);

                int in=Integer.parseInt(menuRow[4])-1;
                menuRow[4]= Integer.toString(in);

                setMenuPrice();
                setMenuQuantity();


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
                        {menuRow[0],menuName,menuPrice.toString(),menuQuantity,"0"}
                    };

                    for (Object[] aBook : bookData) {
                        Row row = sheet.createRow(Integer.parseInt(menuRow[4]));
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
                    System.out.println("\nMenu Updated Successfully!");
                    success=true;
                }
                catch(Exception e){
                    System.out.println("Error: "+e);
                }
        
            }       
        else{
            System.out.println("\nProduct Doesn't Exist!");
        }

        return success;


    }

    boolean deleteMenu(){
        boolean success=false;

        if(checkName()){
                getFile();
                String [] menuRow = menuData.getRow(menuName);
                
                int in=Integer.parseInt(menuRow[4])-1;
                menuRow[4]= Integer.toString(in);

                try{
                    FileOutputStream fileOut = null;
                    FileInputStream fileIn = null;

                    fileIn = new FileInputStream(filePath);
                    XSSFWorkbook workbook = new XSSFWorkbook(fileIn);
                    XSSFSheet sheet = workbook.getSheet("Sheet1");
                    Object[][] bookData = {
                        {menuRow[0],menuRow[1],menuRow[2],menuRow[3],"1"}
                    };

                    for (Object[] aBook : bookData) {
                        Row row = sheet.createRow(Integer.parseInt(menuRow[4]));
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
                    System.out.println("\nMenu Deleted Successfully!");
                    success=true;
                }
                catch(Exception e){
                    System.out.println("Error: "+e);
                }
        
            }       
        else{
            System.out.println("\nProduct Doesn't Exist!");
        }

        return success;


    }

    void viewMenu(){
        getFile();
        menuData.viewMenu();
    }


}
