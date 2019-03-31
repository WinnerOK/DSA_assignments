import java.util.Comparator;

interface List<E> {


    void sort(Comparator<E> cmp);

    int size();

    boolean isEmpty();

    void add(int i, E el);

    void addFirst(E el);

    void addLast(E el);

    void delete(E el);

    void delete(int i);

    void deleteFirst();

    void deleteLast();

    void set(int i, E el);

    E get(int i);
}
