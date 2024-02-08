/**
 * class that creates a BTNode object to be used in a binary search tree
 * all of the methods in this class are simple getters and setters or constructors
 * Christopher Flippen
 * CMSC 401 - Professor Fung
 */
public class BTNode {

    /**
     * Instance variables to keep track of the nodes children, parent, and its key
     */
    private int key;
    private BTNode parent;
    private BTNode left;
    private BTNode right;

    public BTNode getParent() {
        return this.parent;
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public BTNode getLeft() {
        return this.left;
    }

    public boolean hasLeft() {
        return this.left != null;
    }

    public BTNode getRight() {
        return this.right;
    }

    public boolean hasRight() {
        return this.right != null;
    }

    public int getKey() {
        return this.key;
    }

    public BTNode(int key) {
        this.key = key;
    }

    public BTNode(){
    }

    public void setLeft(BTNode newLeft) {
        this.left = newLeft;
    }

    public void setRight(BTNode newRight) {
        this.right = newRight;
    }

    public void setParent(BTNode newParent) {
            this.parent = newParent;
    }
}
