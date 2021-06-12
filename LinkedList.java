public class LinkedList {
    Node head=null,Tail=null;

    public void insert_end(String [] arr){
        Node n=new Node(arr);
        if(head==null)
        {
            head=n;
        }
        else
        {
        Node new_node=new Node(arr);
        new_node=head;
        while(new_node.next!=null)
        {
        new_node=new_node.next;
        }
        new_node.next=n;
        Tail=n;
        }
    }

    public boolean checkEmail(String userEmail){
        boolean check=false;

        Node temp=new Node();
        temp=head;
        if(head!=null)
        {
            Node new_node=new Node();
            new_node=head;
            while(new_node.next!=null)
            {
                if(new_node.arr[2]!=null){
                    if(new_node.arr[2].equals(userEmail)){
                        check=true;
                        break;
                    }
                }
                
                new_node=new_node.next;
            }

            if(new_node.arr[2]!=null){
                if(new_node.arr[2].equals(userEmail)){
                    check=true;
                }
            }
        }
        return check;
    }
    
    public boolean checkCredentials(String userEmail,String userPassword){
        boolean check=false;
        Node temp=new Node();
        temp=head;
        if(head!=null)
        {
          Node new_node=new Node();
        new_node=head;
        while(new_node.next!=null)
        {
            if(new_node.arr[2].equals(userEmail) && new_node.arr[3].equals(userPassword)){
                check=true;
                break;
            }
            new_node=new_node.next;
        }

            if(new_node.arr[2].equals(userEmail) && new_node.arr[3].equals(userPassword)){
                check=true;
            }

        }
        return check;
    }

    public boolean checkMenu(String menuName){
        boolean check=false;
        Node temp=new Node();
        temp=head;
        if(head!=null)
        {
            Node new_node=new Node();
            new_node=head;
            while(new_node.next!=null)
            {
                if(new_node.arr[1]!=null){
                    if(new_node.arr[1].equals(menuName)){
                        check=true;
                        break;
                    }
                }
               
                new_node=new_node.next;
            }

            if(new_node.arr[1]!=null){
                if(new_node.arr[1].equals(menuName)){
                    check=true;
                }
            }
        }
        return check;
    }
    
    public String [] getRow(String name){
        String [] arr = new String[5];
        int index=0;
        Node temp=new Node();
        temp=head;
        if(head!=null)
        {
            Node new_node=new Node();
            new_node=head;
            while(new_node.next!=null)
            {
                if(new_node.arr[1]!=null){
                    index++;                    
                    if(new_node.arr[1].equals(name)){
                        arr[0]=new_node.arr[0];
                        arr[1]=new_node.arr[1];   
                        arr[2]=new_node.arr[2];   
                        arr[3]=new_node.arr[3];   
                        arr[4]=Integer.toString(index);

                        break;
                    }
                }
               
                new_node=new_node.next;
            }

            if(new_node.arr[1]!=null){
                index++;
                if(new_node.arr[1].equals(name)){       
                    arr[0]=new_node.arr[0];
                    arr[1]=new_node.arr[1];   
                    arr[2]=new_node.arr[2];   
                    arr[3]=new_node.arr[3];   
                    arr[4]=Integer.toString(index);
                }
            }
        }

        return arr;
    }

    public void viewMenu(){
        Node temp=new Node();
        temp=head;
        if(head!=null)
        {
            Node new_node=new Node();
            new_node=head;
            while(new_node.next!=null)
            {
                System.out.println("\n---------------------------------------");
                System.out.println("\nProduct Name: "+new_node.arr[1]);
                System.out.println("\nProduct Price: "+new_node.arr[2]);
                System.out.println("\nProduct Quantity: "+new_node.arr[3]);
                System.out.println("---------------------------------------\n");

                new_node=new_node.next;
            }

            System.out.println("\n---------------------------------------");
            System.out.println("\nProduct Name: "+new_node.arr[1]);
            System.out.println("\nProduct Price: "+new_node.arr[2]);
            System.out.println("\nProduct Quantity: "+new_node.arr[3]);
            System.out.println("---------------------------------------\n");
        }

    }
}

class Node{
    String [] arr = new String[4];
    Node next;
    
    Node(){}

    Node(String [] arr){
        this.arr=arr;
        next=null;
    }
}
