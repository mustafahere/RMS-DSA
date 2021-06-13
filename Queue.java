class QNode {
    String [] key = new String[8];
    QNode next;

    public QNode(){}
    public QNode(String [] key)
    {
        this.key = key;
        this.next = null;
    }
}
  

class Queue {
    QNode front, rear;
    int size=0;
  
    public Queue()
    {
        this.front = this.rear = null;
    }

    void enqueue(String [] key)
    {
  
        QNode temp = new QNode(key);
  

        if (this.rear == null) {
            this.front = this.rear = temp;
            size++;
            return;
        }

        size++;
        if(size<11){
            this.rear.next = temp;
            this.rear = temp;
        }
        else{
            System.out.println("\nOrder limit reached.");
        }
    }
  
    void dequeue()
    {

        if (this.front == null)
            return;
  

        QNode temp = this.front;
        this.front = this.front.next;
  

        if (this.front == null)
            this.rear = null;
            size--;
    }

    String [] getRow(int orderId){
        String [] arr = new String[8];
        QNode temp=new QNode();
        temp=front;
        int orderNum=0;
        if(front!=null)
        {
            QNode new_node=new QNode();
            new_node=temp;
            
            while(new_node.next!=null)
            {
                orderNum++;

                if(orderNum==orderId){
                    for(int i=0;i<8;i++){
                        arr[i]=new_node.key[i];
                    }
                }

                new_node=new_node.next;
            }
            orderNum++;

            if(orderNum==orderId){
                for(int i=0;i<8;i++){
                    arr[i]=new_node.key[i];
                }
            }
           
        }

        return arr;
    }
}