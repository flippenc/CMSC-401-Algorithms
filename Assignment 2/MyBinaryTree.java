/**
 * Class that implements a binary search tree using the BTNode object
 * Christopher Flippen
 * CMSC 401 - Professor Fung
 */
public class MyBinaryTree
{
    /** the root of this tree */
    BTNode root;

    public MyBinaryTree(){
    }

    public MyBinaryTree(BTNode root){
        this.root = root;
    }

    public BTNode getRoot(){
        return this.root;
    }

    public void setRoot(BTNode newRoot) {
        this.root = newRoot;
    }

    /**
     * method to add a BTNode to the tree, the method creates a BTNode with key newNum and adds it
     * duplicates are stored as left children
     * @param newNum used to specify key added to the tree
     */
    public void add(int newNum) {
        BTNode current = this.getRoot();
        BTNode newNode = new BTNode(newNum);
        if (current == null) {
            this.setRoot(newNode);
            return;
        }
        //loop until the location for the node is found, this will always terminate in O(n) time
        while (true) {
            //duplicates on the left
            if (current.getKey() >= newNum) {
                if (current.hasLeft()) {
                    current = current.getLeft();
                } else {
                    current.setLeft(newNode);
                    newNode.setParent(current);
                    return;
                }
            } else {
                if (current.hasRight()) {
                    current = current.getRight();
                } else {
                    current.setRight(newNode);
                    newNode.setParent(current);
                    return;
                }
            }
        }
    }

    /**
     * method to check if a key exists in this tree
     * @param num - the key to be checked
     * @return true or false according to if the key was found or not
     */
    public boolean contains(int num) {
        BTNode currentNode = this.root;
        if (this.root == null) {
            return false;
        }
        while (currentNode.getKey()!= num) {
            if (currentNode.getKey() > num) {
                if (currentNode.hasLeft()) {
                    currentNode = currentNode.getLeft();
                } else {
                    return false;
                }
            } else if (currentNode.getKey() < num) {
                if (currentNode.hasRight()) {
                    currentNode = currentNode.getRight();
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * method to get a copy of a node in the tree, this is used in the delete method
     * if the key appears multiple times, it returns the one highest in the tree
     * @param num - the key of the node to get a copy of
     * @return - a copy of the node with key num
     */
    public BTNode getNode(int num) {
        if (!contains(num)) {
            return null;
        }
        BTNode currentNode = this.root;
        while (currentNode.getKey()!= num) {
            if (currentNode.getKey() > num) {
                if (currentNode.hasLeft()) {
                    currentNode = currentNode.getLeft();
                } else {
                    return null;
                }
            } else if (currentNode.getKey() < num) {
                if (currentNode.hasRight()) {
                    currentNode = currentNode.getRight();
                } else {
                    return null;
                }
            }
        }
        return currentNode;
    }

    /**
     * Method to perform an inorder traversal of this tree and return the traversal as a string
     * Recursive implementation of inorder, preorder, and postorder using strings based on code from:
     * https://stackoverflow.com/questions/5077216/returning-an-inorder-string-of-a-tree
     * @return - the string representation of the inorder traversal
     */
    public String inOrder() {
        //left and right are the left and right subtrees of this tree
        if (this.getRoot() == null) {
            return "";
        }
        MyBinaryTree left = new MyBinaryTree(this.getRoot().getLeft());
        MyBinaryTree right = new MyBinaryTree(this.getRoot().getRight());
        //leftStr and rightStr are the inOrder representations of the left and right subtrees
        String leftStr;
        String rightStr;
        if (left.getRoot() != null) {
            leftStr = left.inOrder();
        } else { //if lefts root is null, then there is no left subtree
            leftStr = "";
        }

        if (right.getRoot() != null) {
            rightStr = right.inOrder();
        } else { //if rights root is null, then there is no right subtree
            rightStr = "";
        }
        return (leftStr+" "+this.getRoot().getKey()+" "+rightStr).trim();
    }

    /**
     * Method to perform a preorder traversal of this tree and return the traversal as a string
     * This functions the same way as inorder but with a different order of traversal
     * @return - the preorder traversal represented as a string
     */
    public String preOrder() {
        if (this.getRoot() == null) {
            return "";
        }

        MyBinaryTree left = new MyBinaryTree(this.getRoot().getLeft());
        MyBinaryTree right = new MyBinaryTree(this.getRoot().getRight());
        String leftStr;
        String rightStr;

        if (left.getRoot() != null) {
            leftStr = left.preOrder();
        }   else {
            leftStr = "";
        }

        if (right.getRoot() != null) {
            rightStr = right.preOrder();
        } else {
            rightStr = "";
        } if (!leftStr.trim().equals("")) {
            return (this.getRoot().getKey() + " " + leftStr + " " + rightStr).trim();
        } else {
            return (this.getRoot().getKey()+" "+rightStr).trim();
        }
    }

    /**
     * method to preform a postorder traversal of this tree and return the traversal as a string
     * @return - a string representation of the post order traversal
     */
    public String postOrder() {
        if (this.getRoot() == null) {
            return "";
        }

        MyBinaryTree left = new MyBinaryTree(this.getRoot().getLeft());
        MyBinaryTree right = new MyBinaryTree(this.getRoot().getRight());
        String leftStr;
        String rightStr;

        if (left.getRoot() != null) {
            leftStr = left.postOrder();
        } else {
            leftStr = "";
        }

        if (right.getRoot() != null) {
            rightStr = right.postOrder();
        } else {
            rightStr = "";
        }

        if (!rightStr.trim().equals("")) {
            return (leftStr+" "+rightStr+" "+this.getRoot().getKey()).trim();
        } else {
            return (leftStr+" "+this.getRoot().getKey()).trim();
        }
    }

    /**
     * method to get the smallest node in the tree with root minRoot
     * @param minRoot - the root of the tree to be searched
     * @return - the minimum node in the tree
     */
    public BTNode minimum(BTNode minRoot) {
        if (!minRoot.hasLeft()) {
            return minRoot;
        }
        BTNode current = minRoot;
        while (current.hasLeft()) {
            current = current.getLeft();
        }
        return current;
    }

    /**
     * method to get the largest node in the tree with root root
     * @param maxRoot - the root of the tree to be searched
     * @return  - the maximum node in the tree
     */
    public BTNode maximum(BTNode maxRoot) {
        if (!maxRoot.hasRight()) {
            return maxRoot;
        }
        BTNode current = maxRoot;
        while (current.hasRight()) {
            current = current.getRight();
        }
        return current;
    }

    /**
     * method to replace oldNode with newNode, this process is used in deletion
     * @param tree - the tree to replace nodes in
     * @param oldNode - the node to be replaced
     * @param newNode - the node that will replace oldNode
     */
    public void replace(MyBinaryTree tree, BTNode oldNode, BTNode newNode) {
        //if newNode is the root of the tree, then make newNode the new root
        if (oldNode.getParent() == null) {
            tree.setRoot(newNode);
            if (newNode != null) {
                //since newNode is a root now, it doesn't have a parent
                newNode.setParent(null);
            }
        } else { //oldNode is either a left child or right child
            if (oldNode.equals(oldNode.getParent().getLeft())) {
                oldNode.getParent().setLeft(newNode);
            } else {
                    oldNode.getParent().setRight(newNode);
            }
            //set the newNode's parent
            if (newNode != null) {
                newNode.setParent(oldNode.getParent());
            }
        }
    }

    /**
     * a method to delete all instances of the key delNum from the tree
     * @param delNum - the key of the node to delete
     * @return - true if the node was deleted correctly, false otherwise
     */
    public boolean delete(int delNum) {
        MyBinaryTree T = this;
        BTNode newNode;
        if (!T.contains(delNum)) {
            return false;
        }
        while (T.contains(delNum)) {
            newNode = getNode(delNum);
            treeDelete(T, newNode);
        }
        return true;
    }

    /**
     * method to recursively delete the node delete from tree tree
     * @param tree - the tree to delete from
     * @param delete - the node to delete
     * @return - true or false depending on if the node was deleted correctly
     */
    private boolean treeDelete(MyBinaryTree tree, BTNode delete) {
        if (tree == null) {
            return false;
        }

        if (delete.getLeft()==null) {
            //replace delete with its right child
            replace(tree,delete,delete.getRight());
        } else if (delete.getRight() == null) { //if delete has just left child
            //replace delete with its left child
            replace(tree,delete,delete.getLeft());
        } else {
            BTNode successor = minimum(delete.getRight());

            //if the successor isn't a child of delete:
            //since successor is a minimum, it has no left child
            if (!successor.getParent().equals(delete)) {
                //if successor has a right subtree, replace successor with it
                replace(tree,successor,successor.getRight());
                //set successor's right tree to be delete's right tree
                successor.setRight(delete.getRight());
                //set the successor's right tree to have the correct parent
                successor.getRight().setParent(successor);
                //now, successor is the right child of delete
            }
            //if the successor is the right child of delete:
            //replace delete with successor
            replace(tree,delete,successor);
            //fix successor's left tree
            successor.setLeft(delete.getLeft());
            successor.getLeft().setParent(successor);
        }
        return true;
    }

    /**
     * a main method that takes an array of keys from the command line, adds them to a tree, then prints the
     * inorder traversal of the tree (the sorted order of the elements)
     * If args contains any keys that are not ints, the program prints error and stops
     * @param args - the array of command line arguments
     */
    public static void main(String[] args) {
        MyBinaryTree tree = new MyBinaryTree();
        for (String arg : args) {
            try {
                tree.add(Integer.parseInt(arg));
            } catch (NumberFormatException E) {
                System.out.println("error");
                return;
            }
        }
        System.out.println(tree.inOrder());
    }
}
