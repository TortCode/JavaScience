
public class OrderedLinkedList<E extends Comparable<E>> extends DoubleLinkedList<E> {

	public void insert(E e) {
		Node<E> itr = head.next;
		while(itr != tail) {
			if (e.compareTo(itr.value) < 0) {
				break;
			}
			itr = itr.next;
		}
		//insert before itr
		Node<E> node = new Node<E>(e, itr.prev, itr);
		itr.prev.next = node;
		itr.prev = node;
		size++;
	}

	/*public static void main(String args[]) {
		OrderedLinkedList<ScoreRecord> oll = new OrderedLinkedList<>();
		oll.insert(new ScoreRecord("BoB", 15, 60));
		oll.insert(new ScoreRecord("BoB", 15, 15));
		oll.insert(new ScoreRecord("BoB", 15, 20));
		oll.insert(new ScoreRecord("BoB", 15, 40));
		oll.insert(new ScoreRecord("BoB", 15, 60));
		System.out.println(oll);
	}*/
}
