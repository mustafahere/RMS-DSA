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
            if(new_node.arr[2].equals(userEmail)){
                check=true;
                break;
            }
            new_node=new_node.next;
        }

            if(new_node.arr[2].equals(userEmail)){
                check=true;
            }

        }
        return check;
    }

    public boolean checkPassword(String userPassword){
        boolean check=false;

        Node temp=new Node();
        temp=head;
        if(head!=null)
        {
          Node new_node=new Node();
        new_node=head;
        while(new_node.next!=null)
        {
            if(new_node.arr[3].equals(userPassword)){
                check=true;
                break;
            }
            new_node=new_node.next;
        }

            if(new_node.arr[3].equals(userPassword)){
                check=true;
            }

        }
        return check;
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
