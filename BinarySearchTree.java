/**
 * @author Sean Kurtz
 * @version 1.0
 * A class which implements the BinarySearchTree data structure.
 */
public class BinarySearchTree {
	// The root node of the tree
	public TreeNode root;
	
	/**
	 * Constructs a new empty binary search tree.
	 */
	public BinarySearchTree() {
		root = null;
	}
	/**
	 * A method which inserts a new node into the tree.
	 * @param value The data value for the node we are going to insert
	 */
	public void insert(int value) {
		// Create our wrappers
		TreeNodeWrapper p = new TreeNodeWrapper();
		TreeNodeWrapper c = new TreeNodeWrapper();
		
		// Create the node to insert.
		TreeNode n = new TreeNode();
		n.data = value;
		
		// If the tree is empty
		if (root == null) {
			// Insert as root node
			root = n;
		}
		// Otherwise
		else {
			// Find where to insert
			findNode(value, p, c);
			// If the value to insert is less than the parents value
			if (value < p.get().data ) {
				// Insert on the left of parent
				p.get().left = n;
			}
			// Otherwise
			else {
				// Insert on the right of parent
				p.get().right = n;
			}
		}
		
	}
	/**
	 * A method which fetches a node given by a parameter from the tree.
	 * @param The data value of the node we are trying to fetch
	 * @return true if we fetched the value, false otherwise.
	 */
	public Integer fetch(int target) {
		// Whether we found the item to delete.
		boolean found;
		// Create our wrappers.
		TreeNodeWrapper parent = new TreeNodeWrapper();
		TreeNodeWrapper child = new TreeNodeWrapper();
		// Try to find the item
		found = findNode(target, parent, child);
		// If we found it
		if (found) {
			// Return it.
			return child.get().data;
		}
		// Otherwise
		else {
			// Return null
			return null;
		}
	}
	/**
	 * A method which deletes a node given by a parameter from the tree.
	 * @param target The data value of the node we are trying to delete
	 * @return true if we deleted the node, false otherwise.
	 */
	public boolean delete(int target) {
		// Whether we found the item to delete.
		boolean found;
		// Create our wrappers
		TreeNodeWrapper parent = new TreeNodeWrapper();
		TreeNodeWrapper child = new TreeNodeWrapper();
		TreeNode largest;
		TreeNode nextLargest;
		// Try to find the item.
		found = findNode(target, parent, child);
		// If we couldn't find it.
		if (!found) {
			// Return false
			return false;
		}
		// Otherwise, we go through all the possible cases
		else {
			// Case 1: The node has no children
			if (child.get().left == null && child.get().right == null) {
				// The node we are deleting was a left child
				if (parent.get().left == child.get()) {
					parent.get().left = null;
				}
				// Otherwise the node we are deleting was a right child.
				else {
					parent.get().right = null;
				}
			}
			// Case 2: The node has one child
			else if (child.get().left == null || child.get().right == null) {
				// Node we are deleting is a left child
				if (parent.get().left == child.get()) {
					// If the node we are deleting HAS a left child
					if (child.get().left != null)
						parent.get().left = child.get().left;
					// Otherwise
					else
						parent.get().right = child.get().right;
				}
				// Node we are deleting is a right child
				else {
					// If the node we are deleting HAS a left child
					if (child.get().left != null)
						parent.get().right = child.get().left;
					// Otherwise
					else 
						parent.get().right = child.get().right;
				}
			}
			// Case 3: The node has two children
			else {
				nextLargest = child.get().left;
				largest = nextLargest.right;
				// If the left child (nextLargest) has a right subtree
				if (largest != null) {
					// Continue to move down the right subtree until we hit null
					while(largest.right != null) {
						nextLargest = largest;
						largest = largest.right;
					}
					// Overwrite the data
					child.get().data = largest.data;
					// Save the left subtree
					nextLargest.right = largest.left;
				}
				// Otherwise, the left child does not have a right subtree
				else {
					// Save the right subtree
					nextLargest.right = child.get().right;
					// If the deleted node is a left child
					if (parent.get().left == child.get()) {
						// Jump around the node.
						parent.get().left = nextLargest;
					}
					// Otherwise the deleted node is a right child
					else {
						// Jump around the node.
						parent.get().right = nextLargest;
					}
				}
			}
		}
		return true;
	}
	/**
	 * A method which finds a particular node in the tree.
	 * @param target
	 * @param parent The 'parent' node. This is so we can move around the tree.
	 * @param child The 'child' node. This holds what we are trying to find.
	 * @return true if we found the node, false otherwise.
	 */
	private boolean findNode(int target, TreeNodeWrapper parent, TreeNodeWrapper child) {
		// Set both parent and child to root node
		parent.set(root);
		child.set(root);
		// If the tree is empty.
		if (root == null) {
			return true;
		}
		// While the child is not null
		while (child.get() != null) {
			// If the child's data is the same as target, we found the node.
			if (child.get().data == target) {
				return true;
			}
			// Otherwise
			else {
				// Move parent down to child.
				parent.set(child.get());
				// If the data we are inserting is less than the child
				if (target < child.get().data) {
					// Insert on left
					child.set(child.get().left);
				}
				// Otherwise
				else {
					// Insert on right
					child.set(child.get().right);
				}
			}
		}
		return false;
	}
	/**
	 * A method to perform a LEFT-ROOT-RIGHT traversal, outputting along the way.
	 * @param root The node where we wish to start.
	 */
	public void TraverseLNR(TreeNode root) {
		// Traverse the left subtree.
		if (root.left != null) {
			TraverseLNR(root.left);
		}
		// Operate on the root.
		System.out.println(root.data);
		// Traverse the right subtree.
		if (root.right != null) {
			TraverseLNR(root.right);
		}
	}
	/**
	 * A method to perform a LEFT-RIGHT-ROOT traversal, outputting along the way.
	 * @param root The node where we wish to start.
	 */
	public void traverseLRN(TreeNode root) {
		if (root.left != null){
			traverseLRN(root.left);
		}
		if (root.right != null) {
			traverseLRN(root.right);
		}
		System.out.println(root.data);
	}
	/**
	 * A method to perform a ROOT-LEFT-RIGHT traversal, outputting along the way.
	 * @param root The node where we wish to start.
	 */
	public void traverseNLR(TreeNode root) {
		System.out.println(root.data);
		if (root.left != null) {
			traverseNLR(root.left);
		}
		if (root.right != null) {
			traverseNLR(root.right);
		}
	}
	/**
	 * A method to perform a ROOT-RIGHT-LEFT traversal, outputting along the way.
	 * @param root The node where we wish to start.
	 */
	public void traverseNRL(TreeNode root) {
		System.out.println(root.data);
		if (root.right != null) {
			traverseNRL(root.right);
		}
		if (root.left != null) {
			traverseNRL(root.left);
		}
	}
	/**
	 * A method to perform a RIGHT-LEFT-ROOT traversal, outputting along the way.
	 * @param root The node where we wish to start.
	 */
	public void traverseRLN(TreeNode root) {
		if (root.right != null) {
			traverseRLN(root.right);
		}
		if (root.left != null) {
			traverseRLN(root.left);
		}
		System.out.println(root.data);
	}
	/**
	 * A method to perform a RIGHT-ROOT-LEFT traversal, outputting along the way.
	 * @param root The node where we wish to start.
	 */
	public void traverseRNL(TreeNode root) {
		if (root.right != null) {
			traverseRNL(root.right);
		}
		System.out.println(root.data);
		if (root.left != null) {
			traverseRNL(root.left);
		}
	}
	/**
	 * A method to update a node. This deletes the old node and inserts a new node.
	 * @param target The value of the node we wish to update.
	 * @param newValue The value we wish to replace the old node with.
	 * @return True if we have both deleted the old node and inserted the new one, false otherwise.
	 */
	public boolean update(int target, int newValue) {
		if (!delete(target)) {
			return false;
		}
		insert(newValue);
		return true;
	}
}
