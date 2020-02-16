/**
 * A class which implements the Tree Node for a binary search tree.
 * @author Sean Kurtz
 * @version 1.0
 */
public class TreeNode {
	// The data stored by the tree node.
	public Integer data;
	// The left child of the tree node.
	public TreeNode left;
	// The right child of the tree node.
	public TreeNode right;
	/**
	 * A constructor which initializes a new tree nodes fields to null.
	 */
	public TreeNode() {
		data = null;
		left = null;
		right = null;
	}
}
