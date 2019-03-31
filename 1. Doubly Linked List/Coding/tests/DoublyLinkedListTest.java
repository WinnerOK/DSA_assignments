import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest {
    private DoublyLinkedList<Integer> arr;

    @BeforeEach
    void setUp() {
        arr = new DoublyLinkedList<>();
    }

    @Test
    void constructorWithException() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> new ArrayedList<Integer>(0));
    }


    @Test
    void testAddWithinRange() {
        arr.add(0, 2);
        arr.add(1, 3);
        arr.add(2, 4);
        arr.add(1, 5);
        assertEquals(4, arr.size());
        assertEquals(2, arr.get(0));
        assertEquals(5, arr.get(1));
    }

    @Test
    void testAddOutsideRange() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> arr.add(-1, 5));
    }

    @Test
    void testAddWithResize() {
        arr.add(0, 0);
        arr.add(1, 1);
        arr.add(2, 2);
        arr.add(3, 3);
        arr.add(4, 4);
        arr.add(5, 5);
    }

    @Test
    void testAddFirst() {
        arr.addLast(5);
        arr.addFirst(1);
        assertEquals(1, arr.get(0));
    }

    @Test
    void testAddLast() {
        arr.addLast(1);
        arr.addFirst(3);
        assertEquals(1, arr.get(arr.size() - 1));
    }

    @Test
    void testToString() {
        arr.addLast(1);
        arr.addLast(-1);
        arr.addLast(0);
        assertEquals("1 -1 0 ", arr.toString());
    }

    @Test
    void testSort() {
        arr.addLast(55555);
        arr.addLast(-5555);
        arr.addLast(0);
        arr.sort(Comparator.comparingInt(o -> o));
        assertEquals("55555 0 -5555 ", arr.toString());
    }

    @Test
    void testSortTop(){
        arr.addLast(-999);
        arr.addLast(-888);
        arr.addLast(-777);
        arr.addLast(2);
        arr.addLast(1);
        arr.addLast(0);
        arr.sortTop(Comparator.comparingInt(o -> o), 2);
        assertEquals(2, arr.get(0));
        assertEquals(1, arr.get(1));
        assertNotEquals(0, arr.get(2));
    }

    @Test
    void testSize() {
        assertEquals(0, arr.size());
        arr.addLast(4);
        assertEquals(1, arr.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(arr.isEmpty());
        arr.addLast(0);
        assertFalse(arr.isEmpty());
    }


    @Test
    void testDeleteByIndex() {
        arr.addLast(1);
        arr.addLast(2);
        arr.addLast(3);
        arr.delete(0);
        assertFalse(arr.toString().contains("1"));
    }

    @Test
    void testDeleteLastImplicitly() {
        arr.addLast(3);
        arr.delete(arr.size() - 1);
        assertEquals(0, arr.size());
    }

    @Test
    void testDeleteByValueExists() {
        arr.addLast(1);
        arr.addLast(2);
        arr.addLast(3);
        arr.delete(new Integer(2));
        assertFalse(arr.toString().contains("2"));
    }

    @Test
    void testDeleteByValueDNExists() {
        arr.addLast(1);
        arr.addLast(2);
        arr.addLast(3);
        arr.delete(new Integer(4));
        assertEquals(3, arr.size());
    }

    @Test
    void testDeleteFirst() {
        arr.addLast(1);
        arr.addLast(2);
        arr.addLast(3);
        arr.deleteFirst();
        assertFalse(arr.toString().contains("1"));
    }

    @Test
    void testDeleteFirstInEmptyList() {
        assertThrows(NoSuchElementException.class,
                () -> arr.deleteFirst());
    }

    @Test
    void testDeleteFirstTheOnlyElement() {
        arr.addFirst(7);
        arr.deleteFirst();
        assertTrue(arr.isEmpty());
    }

    @Test
    void testDeleteLast() {
        arr.addLast(1);
        arr.addLast(2);
        arr.addLast(3);
        arr.deleteLast();
        assertFalse(arr.toString().contains("3"));
    }

    @Test
    void testDeleteLastInEmptyList() {
        assertThrows(NoSuchElementException.class,
                () -> arr.deleteLast());
    }

    @Test
    void testDeleteTheOnlyElement() {
        arr.addLast(2);
        arr.deleteLast();
        assertEquals(0, arr.size());
    }

    @Test
    void testSet() {
        arr.addLast(1);
        arr.addLast(2);
        arr.addLast(3);
        arr.set(2, 5);
        assertFalse(arr.toString().contains("3"));
        assertTrue(arr.toString().contains("5"));
    }

    @Test
    void testGetFromTheBeginning() {
        arr.addLast(100);
        arr.addLast(2);
        arr.addLast(3);
        arr.addLast(4);
        assertEquals(2, arr.get(1));
    }

    @Test
    void testGetWithinRange() {
        arr.addLast(1);
        arr.addLast(2);
        arr.addLast(3);
        assertEquals(2, arr.get(1));
    }

    @Test
    void testGetOutsideRange() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> arr.get(-1));
    }
}