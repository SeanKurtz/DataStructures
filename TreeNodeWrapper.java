/**
 * A wrapper for the tree node in a binary search tree. This is so we can return multiple values.
 * @author Sean Kurtz
 * @version 1.0
 */
public class TreeNodeWrapper {
	// The reference to the tree node we are wrapping
	TreeNode treeNodeRef = null;
	// The reference to the left child.
	TreeNode left = null;
	// The reference to the right child.
	TreeNode right = null;
	
	/**
	 * A method which gets the wrappers 'wrapped' node reference.
	 * @return a reference to the TreeNode.
	 */
	public TreeNode get() {
		return treeNodeRef;
	}
	/**
	 * A method which sets the wrappers 'wrapped' node reference.
	 * @param node A reference to a TreeNode to wrap.
	 */
	public void set(TreeNode node) {
		this.treeNodeRef = node;
	}
	
}
