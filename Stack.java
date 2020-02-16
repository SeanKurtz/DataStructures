package sean.assignment1;
/**
 * This class implements a stack data structure.
 * @author Sean Kurtz
 * @version 1.0
 */
public class Stack {
	// Stores the top location  of the stack.
    private int top;
    // Stores the maximum number of nodes that can be in stack.
    private int size;
    // The array of integers. Boxed so I can return null.
    private Integer[] data; 

    /**
     * No parameter constructor that defaults to a size 100 queue.
     */
    public Stack(){
        this(100);
    }

    /**
     * One parameter constructor which takes a size for the queue as input.
     * @param size The maximum number of nodes the queue can store.
     */
    public Stack(int size){
        // set stacks size to input parameter.
        this.size = size;
        // set top of stack to -1 since there are no elements yet.
        top = -1;
        // initialize data array.
        data = new Integer[size];
    }

    /**
     * Inserts the value given by parameter onto top of stack.
     * @param num The value to insert.
     * @return True if value was inserted, false otherwise.
     */
    public boolean push(int num){
        // if stack is full
        if (top == size - 1){
            // return false and don't push
            return false;
        }
        // increment top to next location, the location we will insert at.
        top++;
        // set data value at index top to value we wish to insert.
        data[top] = num;
        // return true since push was completed.
        return true;
    }

    /**
     * Fetches a node from top of stack and returns it. Also deletes the node.
     * @return The node that is on top of stack.
     */
    public Integer pop(){
        // if the stack is empty
        if (top == -1){
            // return null
            return null;
        }
        // otherwise
        else{
            // save value from top of stack
            int value = data[top];
            // reset top of stack to null
            data[top] = null;
            // decrement top since there is now one fewer items
            top--;
            // return the value that was saved from the previous top of stack.
            return value;
        }
    }

    /**
     * Outputs all the values in the stack.
     */
    public void showAll(){
        // for every node in the stack
        for (int i = top; i >= 0; i--){
            // output the value of the node
            System.out.println(data[i]);
        }
    }

    /**
     * Resets the stack to the empty stack.
     */
    public void empty(){
        // so long as the stack is not empty
        while (top != -1){
            // set the item at top of stack to null.
            data[top] = null;
            // decrement top since there is now one fewer item in the stack.
            top--;
        }
    }

    /**
     * Tests if stack is currently empty.
     * @return True if stack is empty, false otherwise.
     */
    public boolean isEmpty(){
        if (top == -1){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * View the item at the top of the stack without deleting.
     * @return The value at the top of the stack.
     */
    public Integer peek(){
        if (top == -1){
            return null;    // underflow
        }
        else{
            return data[top];
        }
    }

    /**
     * Finds the node on a certain position on the stack (given by parameter).
     * @param index Index to search for node at (0 is the bottom, top is the top).
     * @return The item found at the given index.
     */
    public Integer find(int index){
        // if index out of bounds
        if (index <= -1 || index > top){
            // return null
            return null;
        }
        // otherwise
        else {
            // return element at index
            return data[index];
        }
    }
}
