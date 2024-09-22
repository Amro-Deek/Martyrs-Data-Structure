package application;

public class MartyrDateTree {
	private MDateNode root;

	public MDateNode getRoot() {
		return root;
	}

	public void setRoot(MDateNode root) {
		this.root = root;
	}

	public MDateNode find(DateData data) {
		return find(data, getRoot());
	}

	private MDateNode find(DateData data, MDateNode node) {
		
		if (node != null) {
			String date1 = data.getDate().trim();  
			String[] demo1 = date1.split("/");
			String day1 = demo1[1];
			String month1 = demo1[0];
			String year1 = demo1[2];
			String formattedDate1 = year1 + "/" + month1 + "/" + day1;
			String date2 = node.getData().getDate().trim();  
			String[] demo2 = date2.split("/");
			String day2 = demo2[1];
			String month2 = demo2[0];
			String year2 = demo2[2];
			String formattedDate2 = year2 + "/" + month2 + "/" + day2;
			formattedDate1=formattedDate1.trim();
			formattedDate2=formattedDate2.trim();
			int comp = formattedDate2.compareTo(formattedDate1);
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

	public void insert(DateData data) {
		if (root == null) {
			root = new MDateNode(data);
		} else {
			insert(data, root);
		}
	}

	private void insert(DateData data, MDateNode node) {
		String date1 = data.getDate().trim();  
		String[] demo1 = date1.split("/");
		String day1 = demo1[1];
		String month1 = demo1[0];
		String year1 = demo1[2];
		String formattedDate1 = year1 + "/" + month1 + "/" + day1;
		String date2 = node.getData().getDate().trim();  
		String[] demo2 = date2.split("/");
		String day2 = demo2[1];
		String month2 = demo2[0];
		String year2 = demo2[2];
		String formattedDate2 = year2 + "/" + month2 + "/" + day2;
		formattedDate1=formattedDate1.trim();
		formattedDate2=formattedDate2.trim();
		
		
		if (formattedDate1.compareTo(formattedDate2)>=0) { // insert into right subtree
			if (node.getRight() == null)
				node.setRight(new MDateNode(data));
			else
				insert(data, node.getRight());
		} else { // insert into left subtree
			if (node.getLeft() == null)
				node.setLeft(new MDateNode(data));
			else
				insert(data, node.getLeft());
		}
	}

	public MDateNode delete(DateData data) {
		MDateNode current = root;
		MDateNode parent = root;
		boolean isLeftChild = false;

		if (root == null) {
			return null; // Tree is empty
		}

		while (current != null && !current.getData().getDate().equals(data.getDate())) {
			parent = current;
			int comp = current.getData().getDate().compareTo(data.getDate());
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
		MDateNode successor = getSuccessor(current);
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

	private MDateNode getSuccessor(MDateNode node) {
		MDateNode parentOfSuccessor = node;
		MDateNode successor = node;
		MDateNode current = node.getRight();
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

	public void traverseInOrder() {
		traverseInOrder(getRoot());
	}

	private void traverseInOrder(MDateNode node) {
		if (node != null) {
			traverseInOrder(node.getLeft());
			System.out.print(node + " ");
			traverseInOrder(node.getRight());
		}
		System.out.println();
	}

//	@Override
//	public String toString() {
//		
//		return "MartyrDateTree [root=" + root + "]";
//	}
	
}
