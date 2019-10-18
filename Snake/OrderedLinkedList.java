
public class OrderedLinkedList<E extends Comparable<E>> extends DoubleLinkedList<E> {

	public void insert(E e) {
		Node<E> itr = head;
		while(itr.next != tail) {
			itr = itr.next;
			if (e.compareTo(itr.value) < 0) {
				break;
			}
		}
		//insert before itr
		Node<E> node = new Node<E>(e, itr.prev, itr);
		itr.prev.next = node;
		itr.prev = node;
		size++;
	}

}
