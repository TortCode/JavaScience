import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TortLinkedList<E> implements List<E> {

	public static void main(String[] args) {
		List<String> list = new TortLinkedList<>();
		list.add("This");
		list.add("sentence");
		list.add("is");
		list.add("backwards");
		list.add(1, "should be after this");
		list.addAll(2, Arrays.asList(new String[]{"hi", "bi", "die"}));
		System.out.println(list + "" + list.size());
	}

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

	public void clear() {
		this.dummy.next = null;
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
	// getter setter
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
		int i = 0;
		int index = -1;
		if (o == null)
			for (E e : this) {
				if (e == null)
					index = i;
				i++;
			}
		else
			for (E e : this) {
				if (o.equals(e))
					index = i;
				i++;
			}
		return index;
	}
	// remove fxns
	public boolean remove(Object o) {
		int i = indexOf(o);
		if (i != -1) {
			remove(i);
			return true;
		}
		return false;
	}
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
	// iterators
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<E>() {
			Node<E> prev = dummy;

			public boolean hasNext() {
				return prev.next != null;
			}
			public E next() {
				prev = prev.next;
				return prev.value;
			}

		};
	}
	public ListIterator<E> listIterator() {
		return listIterator(0);
	}
	public ListIterator<E> listIterator(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		return new ListIterator<E>() {
			// position of next element
			int pos = index;
			Node<E> prev = getNodeAt(index - 1);
			boolean mutable = false;

			// next is supported; previous is not
			public boolean hasNext() {
				return pos < size;
			}
			public boolean hasPrevious() {
				return false;
			}
			public E next() {
				if (pos == size)
					throw new NoSuchElementException();
				prev = prev.next;
				pos++;
				mutable = true;
				return prev.value;
			}
			public E previous() {
				throw new UnsupportedOperationException();
			}
			public int nextIndex() {
				return pos;
			}
			public int previousIndex() {
				return pos - 1;
			}
			// mutator
			public void add(E e) {
				mutable = false;
				if (pos == size) {
					add(e);
					return;
				}
				prev.next = new Node<E>(e, prev.next);
				size++;
				// ensure next() returns same value
				prev = prev.next;
				pos++;
			}
			public void remove() {
				if (!mutable)
					throw new IllegalStateException();
				mutable = false;
				TortLinkedList.this.remove(pos - 1);
				pos--;
				prev = getNodeAt(pos - 1);
			}
			public void set(E e) {
				if (!mutable)
					throw new IllegalStateException();
				prev.value = e;
			}
		};
	}
	// collection manipulations
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
