/**
 * A class which implements the Doubly Linked List data structure.
 * @author Sean Kurtz
 * @version 1.0
 */
public class DoublyLinkedList {
	// The head node of the list.
	private Node header;
	
	/*	
	 * An internal private class that represents the nodes we are storing in this data structure. An alternative
	 * would be to store this in its own file, however for this assignment we are only supposed to submit three files
	 * so I have chosen to write the node this way. Another option was to code it at the same level as the DoublyLinkedList
	 * class and just give it a package-level modifier. However, for this extremely simple node I have no need of creating nodes
	 * on their own in the driver, and would rather just do it within the DoublyLinkedList methods, so the internal class works fine.
	 * 
	 */
	private class Node {
		/*
		 *  The key-field of this node. If we wanted to store more than a name, this could be another custom class
		 *  and we would compare against that classes' key field when searching.
		 */
		private String data;
		// Reference to the next node in the list.
		private Node next;
		// Reference to the previous node in the list.
		private Node back;
		
		/**
		 * Initializes a node with data as the data string. By default this node is not linked to anything
		 * and must be linked in the actual data structure methods.
		 * @param data
		 */
		public Node(String data) {
			// The key is given by the parameter.
			this.data = data;
			// The next reference is not linked to anything at the point of creation of a Node.
			this.next = null;
			// Neither is the back reference.
			this.back = null;
		}
		/**
		 * Outputs the Node as a String.
		 */
		public String toString() {
			// Return the data (in this case it is just the key.)
			return this.data;
		}
	}
	
	/**
	 * 	Creates an empty Doubly Linked List.
	 */
	public DoublyLinkedList() {
		// The header does not reference anything since there is no node at the 'head' of the list.
		header = null;
	}
	/**
	 * Inserts a new node at the front of the list.
	 * @param listing= the data for the node that we are inserting.
	 */
	public boolean insert(String listing) {
		// If the list is empty
		if (header == null) {
			// Have header reference the node we are creating.
			header = new Node(listing);
			
			// If we couldn't create the header because of lack of memory.
			if (header == null) {
				// Return false
				return false;
			}
			
			// We have inserted the first node in the list, so return true.
			return true;
		}
		// Otherwise, the list is not empty.
		else {
			// Store the header node in a temporary location.
			Node temp = header;
			// Insert a new node as the header.
			header = new Node(listing);
			// Make sure this node is at the front of the list and does not reference backward to anything.
			header.back = null;
			// Set the next reference of the node we are inserting to the last header that we stored in a temporary location.
			header.next = temp;
			// Set the back reference of the previous header to reference the newly inserted node.
			header.next.back = header;
			// Return true since we have inserted a new node at the front of the list.
			return true;
		}
	}
	/**
	 * Fetches a nodes data based on input parameter.
	 * @param listing = the key-field of the node we wish to get data from.
	 * @return The only data that this simple node has. The nodes key field.
	 */
	public String fetch(String listing) {
		Node current = header;
		while (current != null && !(current.data.compareTo(listing) == 0)) {
			current = current.next;
		}
		
		return current.data;
	}
	/**
	 * @param listing = The key-field of the node we wish to delete.
	 * @param newListing = The new key-field that will be inserted instead.
	 * @return True if we both deleted the listing, and inserted the new listing, false otherwise.
	 */
	public boolean update(String listing, String newListing) {
		// Try to delete, if we cannot delete
		if (delete(listing) == false) {
			// Return false if we couldn't delete.
			return false;
		}
		// Try to insert, if we cannot insert
		else if (insert(newListing) == false) {
			// Return false if we cannot insert
			return false;
		}
		
		// If we got past those two checks, we have deleted and inserted, so return true.
		return true;
	}
	/**
	 * 
	 * @param listing = The key-field of the node we wish to delete.
	 * @return True if we have deleted the node, false otherwise.
	 */
	public boolean delete(String listing) {
		// If the list is empty
		if (header == null) {
			// Return false since there is nothing to remove.
			return false;
		}
		// If the first item is the item we are removing
		if (header.data.compareTo(listing) == 0) {
			// Skip over the first item to remove it.
			header = header.next;
			// Return true since we successfully removed the first item.
			return true;
		}
		
		// Set current to front of list.
		Node current = header;
		// While we aren't at the end and we haven't found the correct listing
		while (current != null && !(current.data.compareTo(listing) == 0)) {
			// Advance current to the next position.
			current = current.next;
		}
		
		// If we haven't found the listing and have reached the end
		if (current == null) {
			// We have not found the listing and therefore cannot remove, so return false.
			return false;
		}
		
		// If we found the node but it isn't the last node
		if (current.next != null) {
			// Set the next nodes back pointer to the node before the current node.
			current.next.back = current.back;
		}
		
		// Set the previous nodes next pointer to the node after the current node.
		current.back.next = current.next;
		
		// We have successfully removed the node.
		return true;
	}
	/**
	 * 	Outputs the list in forward order, starting with the header and advancing to the end of the list.
	 */
	public void outputForward() {
		// Set current node we are looking at the the first node in the list.
		Node current = header;
		// So long as the node we are looking at is not null
		while (current != null) {
			// Print the node we are looking at
			System.out.println(current);
			// Advance the node we are looking at forward to the next node.
			current = current.next;
		}
	}
	/**
	 * Outputs the list in forward order, starting with the header and advancing to the end of the list.
	 * This method is recursive since it calls itself. The difference is that we do not have to use
	 * iterative loops like the while loop in the other output methods, although this implementation is messy indeed.
	 * @param current
	 */
	public void outputRecursive(Node current) {
		// Base case when method is called with null parameter. If the node passed in is null.
		if (current == null) {
			// Set current to the front of list.
			current = header;
			// Advance recursively to the front of the list.
			outputRecursive(current);
		}
		// Otherwise, if current is not null
		else {
			// If we are not yet on the last item to output
			if (current.next != null) {
				// Output the current item
				System.out.println(current);
				// Advance recursively to the next item.
				outputRecursive(current.next);
			}
			// Otherwise, we are on the last item.
			else {
				// Output the last item but do not advance any further.
				System.out.println(current);
			}
		}
		
	}
	/**
	 * Outputs the list in backwards order, starting with the last item in the list and ending with 
	 * the node at the head of the list.
	 */
	public void outputBackward() {
		/*
		 * Set current node we are looking at to the first node in the list. If we had a tail reference to the last
		 * item in the list we could use that, but the assignment did not call for that.
		 */
		Node current = header;
		
		// Advance current to the last node.
		while (current.next != null) {
			current = current.next;
		}
		
		// So long as the node we are looking at is not null
		while (current != null) {
			// Print the node
			System.out.println(current);
			// Step backward the node we are looking at to the node before it.
			current = current.back;
		}
		
	}
}
