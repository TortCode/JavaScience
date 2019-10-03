import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DoubleLinkedList<E> implements List<E> {

	int size;
	// dummies
	Node<E> head;
	Node<E> tail;

	private static class Node<E> {
		E value;
		Node<E> next;
		Node<E> prev;

		Node(E val, Node<E> prev, Node<E> next) {
			this.value = val;
			this.next = next;
			this.prev = prev;
		}
	}

	public DoubleLinkedList() {
		this.size = 0;
		this.head = new Node<E>(null, null, null);
		this.tail = new Node<E>(null, null, null);
		this.head.next = tail;
		this.tail.prev = head;
	}

	private Node<E> getNodeAt(int index) {
		Node<E> itr = head;
		for (int i = 0; i <= index; i++)
			itr = itr.next;
		return itr;
	}

	public boolean add(E e) {
		Node<E> node = new Node<E>(e, tail.prev, tail);
		// relink
		tail.prev.next = node;
		tail.prev = node;
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
		Node<E> after = getNodeAt(index);
		Node<E> node = new Node<E>(e, after.prev, after);
		// relink
		after.prev.next = node;
		after.prev = node;
		size++;
	}

	public boolean addAll(Collection<? extends E> c) {
		if (c == null)
			throw new NullPointerException();
		for (E e : c)
			add(e);
		return !c.isEmpty();
	}

	public boolean addAll(int arg0, Collection<? extends E> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public void clear() {
		this.size = 0;
		this.head.next = tail;
		this.tail.prev = head;
	}

	public boolean contains(Object o) {
		if (o == null) {
			for (E e : this)
				if (e == null)
					return true;
		} else {
			for (E e : this)
				if (o.equals(e))
					return true;
		}
		return false;
	}

	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public E get(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		return getNodeAt(index).value;
	}

	public int indexOf(Object o) {
		int i = 0;
		if (o == null)
			for (E e : this) {
				if (e == null)
					return i;
				i++;
			}
		else
			for (E e : this) {
				if (o.equals(e))
					return i;
				i++;
			}
		return -1;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public Iterator<E> iterator() {
		return new Iterator<E>() {
			Node<E> before = head;

			public boolean hasNext() {
				return before.next != tail;
			}

			public E next() {
				if (before.next == tail)
					throw new NoSuchElementException();
				before = before.next;
				return before.value;
			}

		};
	}

	public int lastIndexOf(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ListIterator<E> listIterator() {
		return listIterator(0);
	}

	public ListIterator<E> listIterator(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		return new ListIterator<E>() {
			int nextPos = index;
			Node<E> after = getNodeAt(index);
			Node<E> before = after.prev;
			Node<E> curr = null;
			public void add(E e) {
				curr = null;
				Node<E> node = new Node<E>(e, before, after);
				// relink
				before.next = node;
				after.prev = node;
				// advance one
				after = after.next;
				before = before.next;
				nextPos++;
				size++;
			}

			public boolean hasNext() {
				return after != tail;
			}

			public boolean hasPrevious() {
				return before != head;
			}

			public E next() {
				if (after == tail)
					throw new NoSuchElementException();
				// node acted on by set and remove
				curr = after;
				after = after.next;
				before = before.next;
				nextPos++;
				return curr.value;
			}

			public int nextIndex() {
				return nextPos;
			}

			public E previous() {
				if (before == head)
					throw new NoSuchElementException();
				// node acted on by set and remove
				curr = before;
				after = after.prev;
				before = before.prev;
				nextPos++;
				return curr.value;
			}

			public int previousIndex() {
				return nextPos - 1;
			}

			public void remove() {
				if (curr == null)
					throw new IllegalStateException();
				if (curr == before) {
					nextPos--;
					before = curr.prev;
				} else {
					after = curr.next;
				}
				curr.prev.next = curr.next;
				curr.next.prev = curr.prev;
				curr = null;
				size--;
			}

			public void set(E e) {
				if (curr == null)
					throw new IllegalStateException();
				curr.value = e;
			}
			
		};
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E remove(int arg0) {
		// TODO Auto-generated method stub
		return null;
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
	public int size() {
		return size;
	}

	@Override
	public List<E> subList(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
