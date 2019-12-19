import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CircularLinkedList<E> implements List<E> {
	public static void main(String[] args) {
		List<String> list = new DoubleLinkedList<>();
		list.add("This");
		list.add("sentence");
		list.add("is");
		list.add("backwards");
		list.add(1, "should be after this");
		list.addAll(2, Arrays.asList(new String[]{"hi", "bi", "die"}));
		System.out.println(list + "" + list.size());
	}

	private int size;
	// dummies
	private Node<E> head;
	private Node<E> tail;

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

	public CircularLinkedList() {
		this.size = 0;
		this.head = new Node<E>(null, null, null);
		this.tail = head;
		this.head.next = tail;
		this.tail.prev = head;
	}

	public void clear() {
		this.size = 0;
		this.head.next = tail;
		this.tail.prev = head;
	}

	private Node<E> getNodeAt(int index) {
		Node<E> itr = head;
		for (int i = 0; i <= index; i++)
			itr = itr.next;
		return itr;
	}

	// size fxns
	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	// add fxns
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
		Node<E> before = after.prev;
		Node<E> node = new Node<E>(e, before, after);
		// relink
		after.prev = node;
		before.next = node;
		size++;
	}

	public boolean addAll(Collection<? extends E> c) {
		if (c == null)
			throw new NullPointerException();
		for (E e : c)
			add(e);
		return !c.isEmpty();
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		if (c == null)
			throw new NullPointerException();
		ListIterator<E> itr = this.listIterator(index);
		for (E e : c)
			itr.add(e);
		return !c.isEmpty();
	}

	// contains fxns
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

	public boolean containsAll(Collection<?> c) {
		if (c == null)
			throw new NullPointerException();
		for (Object o : c)
			if (!this.contains(o))
				return false;
		return true;
	}

	// setter/getter
	public E get(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		return getNodeAt(index).value;
	}

	public E set(int index, E e) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		Node<E> node = getNodeAt(index);
		E temp = node.value;
		node.value = e;
		return temp;
	}

	// indexOf fxns
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

	public int lastIndexOf(Object o) {
		int i = size - 1;
		// last element
		Node<E> itr = this.tail.prev;
		if (o == null)
			while (itr != head) {
				if (itr.value == null)
					return i;
				itr = itr.prev;
				i--;
			}
		else
			while (itr != head) {
				if (o.equals(itr.value))
					return i;
				itr = itr.prev;
				i--;
			}
		return -1;
	}

	// iterator fxns
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
				before = node;
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
				nextPos--;
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
				before.next = curr.next;
				after.prev = curr.prev;
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

	// remove fxns
	public boolean remove(Object o) {
		ListIterator<E> itr = this.listIterator();
		if (o == null)
			while (itr.hasNext()) {
				if (itr.next() == null) {
					itr.remove();
					return true;
				}
			}
		else
			while (itr.hasNext()) {
				if (o.equals(itr.next())) {
					itr.remove();
					return true;
				}
			}
		return false;
	}

	public E remove(int index) {
		Node<E> node = getNodeAt(index);
		E temp = node.value;
		node.prev.next = node.next;
		node.next.prev = node.prev;
		size--;
		return temp;
	}

	public boolean removeAll(Collection<?> c) {
		boolean changed = false;
		for (Object o : c)
			while (remove(o))
				changed = true;
		return changed;
	}

	public boolean retainAll(Collection<?> c) {
		boolean changed = false;
		for (E e : this) {
			if (!c.contains(e)) {
				remove(e);
				changed = true;
			}
		}
		return changed;
	}

	// collection manip
	public List<E> subList(int from, int to) {
		if (from < 0 || to > size || from > to)
			throw new IndexOutOfBoundsException();
		TortLinkedList<E> list = new TortLinkedList<E>();
		ListIterator<E> itr = this.listIterator(from);
		for (int i = from; i < to; i++)
			list.add(itr.next());
		return list;
	}

	public Object[] toArray() {
		Object[] arr = new Object[size];
		int i = 0;
		for (E e : this) {
			arr[i] = e;
			i++;
		}
		return arr;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		if (a == null)
			throw new NullPointerException();
		if (this.size <= a.length) {
			int i = 0;
			for (E e : this) {
				a[i] = (T) e;
				i++;
			}
			return a;
		}
		return (T[]) this.toArray();
	}

	public String toString() {
		String s = "[";
		int i = 0;
		for (E e : this) {
			s += (e == null) ? "null" : e;
			i++;
			if (i != size)
				s += ", ";
		}
		s += "]";
		return s;
	}
}
