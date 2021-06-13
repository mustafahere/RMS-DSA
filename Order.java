import java.util.Scanner;
import java.util.UUID;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

class Order {
    String ID;
    String Name;
    String Email;
    String Address;
    String menuID;
    String menuName;
    String Quantity;
    String Date;
    String Status;
    int orderId;


    String filePath="db/orders.xlsx";
    String menuFilePath="db/menu.xlsx";
    
    Queue orderData = new Queue();
    LinkedList menuData = new LinkedList();

    UUID uuid = UUID.randomUUID();

    Scanner input = new Scanner(System.in);

    //Menu details fetch//

    boolean checkName(String productName){
        boolean check=false;
        check=menuData.checkMenu(productName);
        return check;
    }

    void getMenuFile(){
        boolean afterFirstRow=false;
        try {
            FileInputStream file = new FileInputStream(new File(menuFilePath));
    
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

    //////////end menu work///////////////

    void setOrderId(){
        System.out.print("\nEnter order number: ");
        orderId=input.nextInt();
    }

    void setID(){
        ID=UUID.randomUUID().toString();
    }

    void setName(){
        System.out.print("\nEnter your Name: ");
        Name=input.nextLine();
    }

    void setEmail(){
        System.out.print("\nEnter your Email: ");
        Email=input.nextLine();
    }

    void setAddress(){
        System.out.print("\nEnter your Address: ");
        Address=input.nextLine();
    }

    void setQuantity(){
        System.out.print("\nEnter your Quantity: ");
        Quantity=input.nextLine();
    }

    void setDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        LocalDateTime now = LocalDateTime.now();  
        Date=dtf.format(now);
    }

    void setMenuID(){
        getMenuFile();
        System.out.print("\nEnter your Product Name: ");
        String productName=input.nextLine(); 
       
        boolean check=checkName(productName);

        if(check){
            String data [] = menuData.getRow(productName);
            menuID=data[0];
            menuName=productName;
        }
        else{
            System.out.println("\n"+productName+" doesn't exist, Please enter a valid product!");
            setMenuID();
        }

    }

    void setStatus(){
        Status="Pending";
    }

    boolean placeOrder(int lastIndex){
        boolean success=false;
        if(!validateFields()){
            System.out.println("\nPlease insert the fields properly!");
            return success;
        }

        if(orderData.size >= 10){
            System.out.println("\nAlready 10 orders are in process, Please try later!");
            success=true;
            return success;
        }

        try{
            FileOutputStream fileOut = null;
            FileInputStream fileIn = null;

            fileIn = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fileIn);
            XSSFSheet sheet = workbook.getSheet("Sheet1");
            Object[][] bookData = {
                {ID,Name,Email,Address,menuID,Quantity,Date,Status}
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

            System.out.println("\nOrder placed successfully!");
            System.out.println("\n-------------------YOUR BILL----------------------");

            String data [] = menuData.getRow(menuName);

            System.out.println("\nProduct Name: "+data[1]);
            System.out.println("Quantity: "+Quantity+" x "+data[3]);
            System.out.println("Total Amount: "+data[2]+" * "+Quantity+" = PKR "+(Integer.parseInt(data[2]) * Integer.parseInt(Quantity)));
            System.out.println("\n-----------------------------------------------");

        }
        catch(Exception e){
            System.out.println("Error: "+e);
        }

        return success;

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
                String [] arr = new String[8];
                String [] eachRow = new String[8];
                boolean dontadd=false;
                while (cellIterator.hasNext()) 
                {
                    Cell cell = cellIterator.next();
                    arr[cell.getColumnIndex()]=cell.toString();
                    
                    // if(cell.getColumnIndex()==7){
                    //     if(eachRow[7].equals("Pending")){
                    //         arr[0]=eachRow[0];
                    //         arr[1]=eachRow[1];
                    //         arr[2]=eachRow[2];
                    //         arr[3]=eachRow[3];
                    //         arr[4]=eachRow[4];
                    //         arr[5]=eachRow[5];
                    //         arr[6]=eachRow[6];
                    //         arr[7]=eachRow[7];
                    //     }
                    //     else{
                    //         dontadd=true;
                    //     }
                    // }
                }

                if(afterFirstRow && !dontadd){
                    orderData.enqueue(arr);
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

    boolean validateFields(){
        boolean validate=false;
        String priceRegex="^([+-]?\\d*\\.?\\d*)$";
        String emailRegex="^(.+)@(.+)$";

        if(Name!="" && Email!="" && Address!="" && Quantity!=""){
            if(Quantity.matches(priceRegex)){
                validate=true;
            }
            else{
                System.out.println("\nQuantity pattern is incorrect!");
                validate=false;
            }

            if(Email.matches(emailRegex)){
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

    void viewOrders(){
        getMenuFile();
        QNode temp=new QNode();
        temp=orderData.front;
        int orderNum=0;
        if(orderData.front!=null)
        {
            QNode new_node=new QNode();
            new_node=temp;
            
            while(new_node.next!=null)
            {
                orderNum++;
                if(new_node.key[7].equals("Pending")){
                    System.out.println("\n-----------------ORDER "+orderNum+"----------------------");
                    System.out.println("\nCustomer Name: "+new_node.key[1]);
                    System.out.println("\nCustomer Email: "+new_node.key[2]);
                    System.out.println("\nCustomer Address: "+new_node.key[3]);
                    String data [] = menuData.getRowById(new_node.key[4]);
                    System.out.println("\nProduct Name: "+data[1]);
                    System.out.println("\nQuantity: "+new_node.key[5]+" x "+data[3]);
                    System.out.println("\nTotal Amount: "+"PKR "+(Integer.parseInt(data[2]) * Integer.parseInt(new_node.key[5])));
                    System.out.println("\nOrder Date: "+new_node.key[6]);
                    System.out.println("---------------------------------------\n");
                }


                new_node=new_node.next;
            }
            orderNum++;
            if(new_node.key[7].equals("Pending")){
                System.out.println("\n-----------------ORDER "+orderNum+"----------------------");
                System.out.println("\nCustomer Name: "+new_node.key[1]);
                System.out.println("\nCustomer Email: "+new_node.key[2]);
                System.out.println("\nCustomer Address: "+new_node.key[3]);
                String data [] = menuData.getRowById(new_node.key[4]);
                System.out.println("\nProduct Name: "+data[1]);
                System.out.println("\nQuantity: "+new_node.key[5]+" x "+data[3]);
                System.out.println("\nTotal Amount: "+"PKR "+(Integer.parseInt(data[2]) * Integer.parseInt(new_node.key[5])));
                System.out.println("\nOrder Date: "+new_node.key[6]);
                System.out.println("---------------------------------------\n");
            }
        }
    }

    boolean editOrder(){
        boolean success=false;

        if(orderId<=10){
            getFile();
            String [] orderRow = orderData.getRow(orderId);
            // for(int i=0;i<orderRow.length;i++){
            //         if(i!=0 && i!=4){
            //             System.out.println(orderRow[i]);
            //         }
            // }

            try{
                FileOutputStream fileOut = null;
                FileInputStream fileIn = null;

                fileIn = new FileInputStream(filePath);
                XSSFWorkbook workbook = new XSSFWorkbook(fileIn);
                XSSFSheet sheet = workbook.getSheet("Sheet1");
                Object[][] bookData = {
                    {orderRow[0],orderRow[1],orderRow[2],orderRow[3],orderRow[4],orderRow[5],orderRow[6],"Completed"}
                };

                for (Object[] aBook : bookData) {
                    Row row = sheet.createRow((orderId));
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
                System.out.println("\nOrder Updated Successfully!\n");
                success=true;


                

            }
            catch(Exception e){
                System.out.println("Error: "+e);
            }
        
            }       
        else{
            System.out.println("\nOrder Id is incorrect!");
        }

        return success;


    }

}
