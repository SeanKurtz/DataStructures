package sean.assignment1;

/**
 *      This class implements a circular queue data structure for an array of Integers.
 *
 *      @author Sean Kurtz
 *      @version 1.0
 */
public class Queue {
    // The data set of integers for the queue.
    private Integer[] data;
    // Maximum number of nodes the queue can hold.
    private int size;
    // The index into the array of data where the next Dequeue operation will be performed.
    private int front;
    // The index into the array of data where the next Enqueue operation will be performed.
    private int rear;
    // The number of nodes currently in the queue.
    private int numNodes;

    /**
     * Default constructor for a Queue which defaults the size to 100.
     */
    public Queue(){
        this(100);
    }

    /**
     * Constructor for Queue which takes a size as parameter.
     * @param size The maximum number of nodes the queue will hold.
     */
    public Queue(int size){
        this.size = size;
        numNodes = 0;
        front = 0;
        rear = 0;
        data = new Integer[size];
    }

    /**
     * Insert the node (given by parameter) to the queue.
     * @param value The integer value to insert at rear of queue.
     * @return true if inserted, false otherwise.
     */
    public boolean enque(int value){
        // if the queue is full
        if (numNodes == size){
            // we have an overflow, return false and don't enque.
            return false;
        }
        else{
            // Increment number of nodes since we are about to add one.
            numNodes++;
            // Insert item at rear of queue.
            data[rear] = value;
            /*
                Increment rear, if the increment is going to take us off edge,
                circle back around to 0 with modular arithmetic.
             */
            rear = (rear + 1) % size;
            // Return true, we have enqueud successfully.
            return true;
        }
    }

    /**
     * Fetch a node from the queue and returns it.
     * @return the node at the front of the queue.
     */
    public Integer deque(){
        // if the queue is empty
        if (numNodes == 0){
            // return null.
            return null;
        }
        else {
            // save location of front of queue.
           int frontLocation = front;
           // increment queue, but circle back to 0 if we were about to go out of bounds.
           front = (front + 1) % size;
           // decrease counter of number of nodes since we just moved passed a node.
            numNodes--;
            // save value from data at front location.
            int value = data[frontLocation];
            // reset the value at frontLocation to null.
            data[frontLocation] = null;
            // return data from the previous front position that we had saved.
            return value;
        }
    }

    /**
     * Outputs all the values in the queue
     */
    public void showAll(){
        // Save index of front of queue.
        int i = front;
        // for every node in the queue
        for (int c = 0; c < numNodes; c++){
            System.out.println(data[i]);
            i = (i + 1) % size;
        }

    }

    /**
     * Peek at the front of the queue.
     * @return The value at front of the queue.
     */
    public Integer peek(){
        // if the queue is empty
        if (front == 0 && rear == 0){
            // return null
            return null;
        }
        // otherwise
        else {
            // return the item at front of queue.
            return data[front];
        }

    }

    /**
     * Reinitialize the queue to the empty queue.
     */
    public void empty(){
        numNodes = 0;
        front = 0;
        rear = 0;
    }
    /**
     * Tests whether the queue is empty.
     * @return True if the queue is empty, false otherwise.
     */
    public boolean isEmpty(){
        if (numNodes == 0){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Finds the node on a certain position in the queue. (given by parameter).
     * @param index The index to return from the queue.
     * @return The item found at given index. Null if empty.
     */
    public Integer find(int index){
        // if the queue is empty, or our index is out of bounds
         if (numNodes == 0 || index < 0 || index > size - 1){
            // return null
            return null;
        }
        // otherwise
        else {
            // return the value at the given index.
            return data[index];
        }
    }
}
