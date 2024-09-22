package application;

public class MLinkList {
	public Node head;

	public void insert(Martyr data) {
		Node newnode = new Node(data);
		if (head == null) {// case 0 insert to empty list.
			head = newnode;
		} else {
			Node curr = head;
			Node prev = null;
			for (; curr != null && ((Martyr) curr.getData()).compareTo(data) < 0; prev = curr, curr = curr.getNext())
				;
			if (prev == null) {// case 1: insert first.
				newnode.setNext(curr);
				head = newnode;
			} else if (curr == null) {
				prev.setNext(newnode);
			} else {
				prev.setNext(newnode);
				newnode.setNext(curr);

			}
		}

	}

	public Node delete(Martyr data) {
		if (head != null) {
			Node prev = null, curr = head;
			for (; curr != null && ((Martyr) curr.getData()).compareTo(data) < 0; prev = curr, curr = curr.getNext())
				;
			if (curr != null && ((Martyr) curr.getData()).compareTo(data) == 0) {
				if (head == curr) {
					head = curr.getNext();
				} else if (curr.getNext() == null) {
					prev.setNext(null);
				} else {
					prev.setNext(curr.getNext());
				}
				return curr;
			}
		}
		return null;
	}

//	public void sort(Node<T> curr) {
//		if (head==null||head.getNext()==null) {
//			return;
//		}
//		if (curr.getData().compareTo(curr.getNext().getData())>0) {
//			T temp =curr.getData();
//			curr.setData(curr.getNext().getData());
//			curr.getNext().setData(temp);
//			
//		}
//		else {
//			sort(curr.getNext());
//		}
//		
//	}

	public boolean find(Martyr data) {// iterative
		if (head != null) {
			Node prev = null, curr = head;
			for (; curr != null && ((Martyr) curr.getData()).compareTo(data) < 0; prev = curr, curr = curr.getNext())
				;
			if (curr != null && ((Martyr) curr.getData()).compareTo(data) == 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	public Node find2(Martyr data) {// iterative
		if (head != null) {
			Node prev = null, curr = head;
			for (; curr != null && ( curr.getData().getName()).compareTo(data.getName()) < 0; prev = curr, curr = curr.getNext())
				;
			if (curr != null && ( curr.getData().getName()).compareTo(data.getName()) == 0) {
				return curr;
			} else {
				return null;
			}
		}
		return null;
	}

	public void traverse(Node head) {
		Node curr = head;
		System.out.print("Head -->");
		while (curr != null) {
			System.out.print(curr + "-->");
			curr = curr.getNext();
		}
		System.out.println(curr);
	}

	public int length() {
		int c = 0;
		Node curr = head;
		while (curr != null) {
			c++;
			curr = curr.getNext();
		}
		return c;
	}

	@Override
	public String toString() {
		Node curr =head;
		String str ="";
		while (curr!=null) {
			str+=curr.getData().toString();
			curr=curr.getNext();
		}
		return str;
	}

}
