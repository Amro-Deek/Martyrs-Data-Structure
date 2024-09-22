package application;

public class LocTree {
	private LocNode root;

	public LocNode getRoot() {
	    return root;
	}

	public void setRoot(LocNode root) {
	    this.root = root;
	}

	public LocNode find(Location data) {
	    return find(data, getRoot());
	}

	private LocNode find(Location data, LocNode node) {
	    if (node != null) {
	        int comp = node.getData().getName().compareTo(data.getName());
	        if (comp == 0) {
	            return node;
	        } else if (comp > 0) {
	            return find(data, node.getLeft());
	        } else {
	            return find(data, node.getRight());
	        }
	    }
	    return null;
	}

	public void insert(Location data) {
	    if (root == null) {
	        root = new LocNode(data);
	    } else {
	        insert(data, root);
	    }
	}

	private void insert(Location data, LocNode node) {
	    int comp = node.getData().getName().compareTo(data.getName());
	    if (comp <= 0) {
	        // Insert into the right subtree
	        if (node.getRight() == null) {
	            node.setRight(new LocNode(data));
	        } else {
	            insert(data, node.getRight());
	        }
	    } else {
	        // Insert into the left subtree
	        if (node.getLeft() == null) {
	            node.setLeft(new LocNode(data));
	        } else {
	            insert(data, node.getLeft());
	        }
	    }
	}

	public LocNode delete(Location data) {
	    LocNode current = root;
	    LocNode parent = root;
	    boolean isLeftChild = false;

	    if (root == null) {
	        return null; // Tree is empty
	    }

	    while (current != null && !current.getData().getName().equals(data.getName())) {
	        parent = current;
	        int comp = current.getData().getName().compareTo(data.getName());
	        if (comp > 0) {
	            current = current.getLeft();
	            isLeftChild = true;
	        } else {
	            current = current.getRight();
	            isLeftChild = false;
	        }
	    }

	    if (current == null) {
	        return null; // Node to be deleted not found
	    }

	    // Case 1: Node is a leaf
	    if (current.getLeft() == null && current.getRight() == null) {
	        if (current == root) {
	            root = null;
	        } else {
	            if (isLeftChild) {
	                parent.setLeft(null);
	            } else {
	                parent.setRight(null);
	            }
	        }
	        return current;
	    }

	    // Case 2: Node has left child only
	    if (current.getLeft() != null && current.getRight() == null) {
	        if (current == root) {
	            root = current.getLeft();
	        } else if (isLeftChild) {
	            parent.setLeft(current.getLeft());
	        } else {
	            parent.setRight(current.getLeft());
	        }
	        return current;
	    }

	    // Case 3: Node has right child only
	    if (current.getRight() != null && current.getLeft() == null) {
	        if (current == root) {
	            root = current.getRight();
	        } else if (isLeftChild) {
	            parent.setRight(current.getRight());
	        } else {
	            parent.setRight(current.getRight());
	        }
	        return current;
	    }

	    // Case 4: Node has both left and right children
	    LocNode successor = getSuccessor(current);
	    if (current == root) {
	        root = successor;
	    } else if (isLeftChild) {
	        parent.setLeft(successor);
	    } else {
	        parent.setRight(successor);
	    }
	    successor.setLeft(current.getLeft());
	    return current;
	}

	private LocNode getSuccessor(LocNode node) {
	    LocNode parentOfSuccessor = node;
	    LocNode successor = node;
	    LocNode current = node.getRight();
	    while (current != null) {
	        parentOfSuccessor = successor;
	        successor = current;
	        current = current.getLeft();
	    }
	    if (successor != node.getRight()) {
	        // Fix successor connections
	        parentOfSuccessor.setLeft(successor.getRight());
	        successor.setRight(node.getRight());
	    }
	    return successor;
	}

	public int height(LocNode node) {
	    if (node == null) {
	        return 0;
	    }
	    if (node.getLeft() == null && node.getRight() == null) { // Is a leaf
	        return 1;
	    }
	    int leftHeight = height(node.getLeft());
	    int rightHeight = height(node.getRight());
	    return Math.max(leftHeight, rightHeight) + 1;
	}

	public void traverseInOrder() {
	    traverseInOrder(getRoot());
	}

	private void traverseInOrder(LocNode node) {
	    if (node != null) {
	        traverseInOrder(node.getLeft());
	        System.out.print(node + " ");
	        traverseInOrder(node.getRight());
	    }
	    System.out.println();
	}

	
}
