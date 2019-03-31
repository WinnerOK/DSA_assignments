import java.util.Comparator;

class ArrayedList<E> implements List<E> {

    // it shows how many times the size will increase if needed
    private static final int SIZE_INCREASING_MULTIPLIER = 2;
    private int size = 0; // actual size
    private E[] data; // data storage

    @SuppressWarnings("unchecked")
        // tells compiler that cast from Object to E[] is on purpose
    ArrayedList(int capacity) {
        if (capacity <= 0) {
            throw new IndexOutOfBoundsException(String.format("Capacity must be greater that 0, not %d", capacity));
        }
        data = (E[]) new Object[capacity];
        /*
        since array contains only references to E,
        such creation won't produce Tany problems,
        because Object is also just a ref.
        */
    }

    private int getIndex(E el) {
        for (int i = 0; i < size; i++) { // linear search for el
            if (data[i].equals(el)) return i;
        }
        return -1;
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
     * Create new data storage, which size is SIZE_INCREASING_MULTIPLIER times bigger than original one
     * and contains all data.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        E[] new_arr = (E[]) new Object[size * SIZE_INCREASING_MULTIPLIER];
        //copy data to new_arr
        System.arraycopy(data, 0, new_arr, 0, size);
        data = new_arr;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            res.append(" ");
        }
        return res.toString();
    }

    /**
     * Sort the list in order described by @code cmp
     *
     * @param cmp comparator for given type
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
        int min_index;
        for (int i = 0; i <= n - 1; i++) {
            min_index = i;
            for (int j = i + 1; j < size; j++) {
                if (cmp.compare(data[j], data[min_index]) > 0) {
                    min_index = j;
                }
            }
            E tmp = data[min_index];
            data[min_index] = data[i];
            data[i] = tmp;
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
     * Add element el to index i
     */
    @Override
    public void add(int i, E el) {
        isIndexValid(i, size + 1); // possible to addLast implicitly
        if (size == data.length) { // if data storage is ran out, make it bigger
            resize();
        }
        // copy from data[i] to data[i + 1] (size - i) elements = move 1 forward
        System.arraycopy(data, i, data, i + 1, size - i);
        data[i] = el;
        ++size;
    }

    /**
     * Add element el to the first position in a list
     */
    @Override
    public void addFirst(E el) {
        add(0, el);
    }

    /**
     * Add element el to the last position in a list
     */
    @Override
    public void addLast(E el) {
        add(size, el);
    }

    /**
     * Delete element el from the list
     */
    @Override
    public void delete(E el) {
        int i = getIndex(el); //find an element and delete it by index
        if (i >= 0) delete(i);
    }

    /**
     * Delete element at index i
     */
    @Override
    public void delete(int i) {
        isIndexValid(i, size);
        //copy from data[i+1] to [i+1] (size - i - 1) elements = move 1 backwards
        System.arraycopy(data, i + 1, data, i, size - 1 - i);
        data[size - 1] = null; //explicitly delete last element in order not to confuse while debugging
        size--;
    }

    /**
     * Delete the first element in the list
     */
    @Override
    public void deleteFirst() {
        delete(0);
    }

    /**
     * Delete the last element in the list
     */
    @Override
    public void deleteLast() {
        delete(size - 1);
    }

    /**
     * Set element at index i to be equal to el
     */
    @Override
    public void set(int i, E el) {
        isIndexValid(i, size);
        data[i] = el;
    }

    /**
     * Get element at index i from the list
     */
    @Override
    public E get(int i) {
        isIndexValid(i, size);
        return data[i];
    }
}
