import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

class Profits {
    
    String filePath="db/orders.xlsx";
    String menuFilePath="db/menu.xlsx";
    
    Queue orderData = new Queue();
    LinkedList menuData = new LinkedList();


    float profit=0;
    
    String getMonth(int month){
        String [] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        return months[month];
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
                    eachRow[cell.getColumnIndex()]=cell.toString();

                    if(cell.getColumnIndex()==7){
                        if(eachRow[7].equals("Completed")){
                            arr[0]=eachRow[0];
                            arr[1]=eachRow[1];
                            arr[2]=eachRow[2];
                            arr[3]=eachRow[3];
                            arr[4]=eachRow[4];
                            arr[5]=eachRow[5];
                            arr[6]=eachRow[6];
                            arr[7]=eachRow[7];
                        }
                        else{
                            dontadd=true;
                        }
                    }
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

    void calculateProfits(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        LocalDateTime now = LocalDateTime.now();  
        String Date=dtf.format(now);
        QNode temp=new QNode();
        temp=orderData.front;
        if(orderData.front!=null)
        {
            QNode new_node=new QNode();
            new_node=temp;
            
            while(new_node.next!=null)
            {
           
                if(new_node.key[6].equals(Date)){
                    String data [] = menuData.getRowById(new_node.key[4]);
                    profit+=(Integer.parseInt(data[2]) * Integer.parseInt(new_node.key[5]));
                }

                new_node=new_node.next;
            }

      
            if(new_node.key[6].equals(Date)){
                String data [] = menuData.getRowById(new_node.key[4]);
                profit+=(Integer.parseInt(data[2]) * Integer.parseInt(new_node.key[5]));
            }
        }
    }

    void viewProfits(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM");  
        LocalDateTime now = LocalDateTime.now();  
        String Date=dtf.format(now);

        getMenuFile();
        getFile();
        calculateProfits();

        System.out.println("\nMonth: "+getMonth(Integer.parseInt(Date)-1));
        System.out.println("Profit: "+profit);
    }
}
