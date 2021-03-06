import java.util.Iterator;

public class DoubleLinkedList<E> implements Iterable<E> {

	protected int size;
	// head and tail are both dummy nodes
	// structure of list is head <-> elements <-> tail
	protected Node<E> head, tail;

	protected static class Node<E> {
		E value;
		Node<E> next;
		Node<E> prev;

		Node(E e, Node<E> prev, Node<E> next) {
			this.value = e;
			this.next = next;
			this.prev = prev;
		}
	}

	public DoubleLinkedList() {
		size = 0;
		this.head = new Node<E>(null, null, null);
		this.tail = new Node<E>(null, null, null);
		head.next = tail;
		tail.prev = head;
	}

	public void add(E e) {
		Node<E> last = tail.prev;
		Node<E> node = new Node<E>(e, last, tail);
		// link
		last.next = node;
		tail.prev = node;
		size++;
	}

	public E first() {
		return head.next.value;
	}

	public E last() {
		return tail.prev.value;
	}

	public void remove() {
		head.next = head.next.next;
		// clean up prev ptr
		head.next.prev = head;
		size--;
	}

	public int size() {
		return size;
	}

	// allows for-each loop
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private Node<E> itr = head;

			public boolean hasNext() {
				return itr.next != tail;
			}

			public E next() {
				itr = itr.next;
				return itr.value;
			}

		};
	}
	
	public String toString() {
		String ret = "[";
		boolean first = true;
		for (E e : this) {
			if (!first)
				ret += ", ";
			first = false;
			ret += e.toString();
		}
		return ret + "]";
	}

}