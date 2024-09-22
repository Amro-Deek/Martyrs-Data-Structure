package application;

public class DisTree {
	private DisNode root;

	public DisNode getRoot() {
		return root;
	}

	public void setRoot(DisNode root) {
		this.root = root;
	}

	public DisNode find(District data) {
		return find(data, getRoot());
	}

	private DisNode find(District data, DisNode node) {
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

	public void insert(District data) {
		if (root == null) {
			root = new DisNode(data);
		} else {
			insert(data, root);
		}
	}

	private void insert(District data, DisNode node) {

		if (data.getName().compareTo(node.getData().getName()) >= 0) { // insert into right subtree
			if (node.getRight() == null)
				node.setRight(new DisNode(data));
			else
				insert(data, node.getRight());
		} else { // insert into left subtree
			if (node.getLeft() == null)
				node.setLeft(new DisNode(data));
			else
				insert(data, node.getLeft());
		}
	}

	public DisNode delete(District data) {
		DisNode current = root;
		DisNode parent = root;
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
		DisNode successor = getSuccessor(current);
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
//	public DisNode delete(District data) {
//		return delete(data, root);
//	}
//
//	private DisNode delete(District data, DisNode node) {
//		if(node==null) return null;
//		if(node.getData().getName().compareTo(data.getName())<0) node.setRight(delete(data, node.getRight()));
//		else if(node.getData().getName().compareTo(data.getName())>0) node.setLeft(delete(data, node.getLeft()));
//		else if(node.getRight()==null && node.getLeft()==null) node=null;
//		else if(node.getLeft()==null) node=node.getRight();
//		else if(node.getRight()==null) node=node.getLeft();
//		else deleteNodeWithTwoChildren(node);
//		return node;
//	}
//	private void deleteNodeWithTwoChildren(DisNode node) {
//		DisNode suc = getMin(node.getRight());
//		node.setData(suc.getData());
//		delete(suc.getData(), node.getRight());
//	}
//	private DisNode getMin(DisNode node) {
//		if(node.getLeft()==null) return node;
//		return getMin(node.getLeft());
//	}

	private DisNode getSuccessor(DisNode node) {
		DisNode parentOfSuccessor = node;
		DisNode successor = node;
		DisNode current = node.getRight();
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

	public int height(DisNode node) {
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

	private void traverseInOrder(DisNode node) {
		if (node != null) {
			traverseInOrder(node.getLeft());
			System.out.print(node + " ");
			traverseInOrder(node.getRight());
		}
		System.out.println();
	}
//	public Stack getNodes () {
//		return getNodes(root,Stack);
//		
//	}
//	private Stack getNodes(DisNode node) {
//		if (node != null) {
//			traverseInOrder(node.getLeft());
//			
//			traverseInOrder(node.getRight());
//		}
//		return null;
//	}

}
