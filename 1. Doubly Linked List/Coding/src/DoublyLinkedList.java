import java.util.Comparator;
import java.util.NoSuchElementException;

class DoublyLinkedList<E> implements List<E> {

    private int size = 0; // actual size
    private DoublyLinkedNode<E> head, tail;

    public DoublyLinkedList() {
        head = null;
        tail = null;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        DoublyLinkedNode<E> node = head;
        if (node == null) return "";
        while (node != null) { // iterate over all nodes, add their values to string
            res.append(node.value).append(" ");
            node = node.next;
        }
        return res.toString();

    }

    /**
     * is index i valid for an operation?
     *
     * @param i   index to check
     * @param max maximum valid index for an operation
     */
    private void isIndexValid(int i, int max) {
        if (i < 0 || i >= max) {
            throw new IndexOutOfBoundsException(String.format("Index must be within range [0, %d), not %d", max, i));
        }
    }

    /**
     * Returns node with index i
     */
    private DoublyLinkedNode<E> getIthNode(int i) {
        if (i < (size / 2)) { // node is closer to the beginning or to the end ?
            DoublyLinkedNode<E> res = head;
            for (int j = 0; j < i; j++) {
                res = res.next;
            }
            return res;
        } else {
            DoublyLinkedNode<E> res = tail;
            for (int j = size - 1; j > i; j--) {
                res = res.prev;
            }
            return res;
        }
    }

    /**
     * Insterts element el after non-null node prec
     */
    private void addAfter(DoublyLinkedNode<E> prec, E el) {
        DoublyLinkedNode<E> next = prec.next;
        DoublyLinkedNode<E> node = new DoublyLinkedNode<>(prec, el, prec.next);
        if (prec.next == null) { // link after last element
            prec.next = node; // make node new last element
            tail = node;
        } else {
            prec.next = node; // insert node between 2 other
            next.prev = node;
        }
        size++;
    }

    /**
     * Find node with value el
     *
     * @return found node or null
     */
    private DoublyLinkedNode<E> findNode(E el) {
        DoublyLinkedNode<E> node = head;
        while (node != null) {
            if (node.value.equals(el)) return node;
            node = node.next;
        }
        return null;
    }

    private void deleteNode(DoublyLinkedNode<E> node) {
        DoublyLinkedNode<E> next = node.next;
        DoublyLinkedNode<E> prev = node.prev;

        if (prev == null) { // If deleting the first node, head pointer must point to the next node;
            head = next;
        } else {
            prev.next = next; // Change linking
            node.prev = null;
        }

        if (next == null) { // If deleting the last - reassign the tail pointer
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.value = null; //in order to delete this object from memory
        size--;
    }

    /**
     * Sort the list in order described by @code cmp
     *
     * @param cmp comparator for given data type
     */
    @Override
    public void sort(Comparator<E> cmp) {
        sortTop(cmp, size);
    }

    public void sortTop(Comparator<E> cmp, int n){
        /*
        Selection sort
        The main idea is to find the greatest item on each step
        and swap it with element_i

        Bonus task: It sorts top n elements
         */
        DoublyLinkedNode<E> min, cur = head, find_min;

        for (int i = 0; i <= n - 1; i++) {
            min = cur;
            find_min = min.next;
            for (int j = i + 1; j < size; j++) {
                if (cmp.compare(find_min.value, min.value) > 0) {
                    min = find_min;
                }
                find_min = find_min.next;
            }
            E tmp = cur.value;
            cur.value = min.value;
            min.value = tmp;
            cur = cur.next;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Add element el at index i
     */
    @Override
    public void add(int i, E el) {
        isIndexValid(i, size + 1);
        if (i == 0) { // All add operations but addFirst can be interpreted as addAfter op in order to reuse code
            addFirst(el);
        } else {
            addAfter(getIthNode(i - 1), el);
        }
    }

    /**
     * Add element el to the first position in a list
     */
    @Override
    public void addFirst(E el) {
        DoublyLinkedNode<E> node = new DoublyLinkedNode<>(null, el, head);
        if (head == null) { // If new element is the only one, then both head and tail will point on it
            tail = node;
        } else {
            head.prev = node;
        }
        head = node;
        size++;
    }

    /**
     * Add element el to the last position in a list
     */
    @Override
    public void addLast(E el) {
        DoublyLinkedNode<E> node = new DoublyLinkedNode<>(tail, el, null);
        if (tail == null) { // If new element is the only one, then both head and tail will point on it
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;

    }

    @Override
    public void delete(E el) {
        DoublyLinkedNode<E> node = findNode(el);
        if (node != null) deleteNode(node);
    }

    /**
     * delete item at index i
     */
    @Override
    public void delete(int i) {
        isIndexValid(i, size);
        deleteNode(getIthNode(i));
    }


    /**
     * Remove element from the first position
     */
    @Override
    public void deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException("There is no element at index 0");
        } else {
            // It is possible to use deleteNode(head), but this function performs less operations.
            if (head.next == null) { // there was only one element
                head = null; // therefore, list is empty
                tail = null;
            } else {
                head = head.next; //relink elements
                head.prev = null;
            }

            size--;
        }
    }

    /**
     * Remove element from the last position
     */
    @Override
    public void deleteLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        } else {
            // It is possible to use deleteNode(tail), but this function performs less operations.
            if (tail.prev == null) { // there was only one element
                head = null; // therefore, list is empty
                tail = null;
            } else {
                tail = tail.prev;
                tail.next = null;
            }
            size--;
        }
    }

    @Override
    public void set(int i, E el) {
        isIndexValid(i, size);
        getIthNode(i).value = el;
    }

    @Override
    public E get(int i) {
        isIndexValid(i, size);
        return getIthNode(i).value;
    }
}
