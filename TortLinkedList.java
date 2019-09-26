import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TortLinkedList<E> implements List<E> {

	// Linked List starts with dummy node w null value
	// Last node points to null
	private int size;
	private Node<E> dummy;
	private Node<E> tail;

	private static class Node<E> {
		E value;
		Node<E> next;
		Node(E e, Node<E> next) {
			this.value = e;
			this.next = next;
		}
	}

	public TortLinkedList() {
		this.dummy = new Node<E>(null, null);
		this.tail = this.dummy;
		this.size = 0;
	}

	// returns dummy if index < 0
	private Node<E> getNodeAt(int index) {
		Node<E> itr = dummy;
		for (int i = 0; i <= index; i++)
			itr = itr.next;
		return itr;
	}

	// size fxns
	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0;
	}

	// add fxns
	public boolean add(E e) {
		this.tail.next = new Node<E>(e, null);
		this.tail = this.tail.next;
		size++;
		return true;
	}
	public void add(int index, E e) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		if (index == size) {
			add(e);
			return;
		}
		Node<E> prev = getNodeAt(index - 1);
		prev.next = new Node<E>(e, prev.next);
		size++;
	}

	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends E> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		this.dummy = new Node<E>(null, null);
		this.tail = this.dummy;
		this.size = 0;
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		return getNodeAt(index).value;
	}

	@Override
	public int indexOf(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<E>() {
			Node<E> curr = dummy;
			@Override
			public boolean hasNext() {
				return curr.next != null;
			}

			@Override
			public E next() {
				curr = curr.next;
				return curr.value;
			}

		};
	}

	@Override
	public int lastIndexOf(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		Node<E> prev = getNodeAt(index - 1);
		if (index == size - 1)
			this.tail = prev;
		E temp = prev.next.value;
		prev.next = prev.next.next;
		size--;
		return temp;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E set(int index, E e) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		Node<E> node = getNodeAt(index);
		E temp = node.value;
		node.value = e;
		return temp;
	}


	@Override
	public List<E> subList(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	// array fxns
	public Object[] toArray() {
		Object[] arr = new Object[size];
		int i = 0;
		for (E e : this) {
			arr[i] = e;
			i++;
		}
		return arr;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
