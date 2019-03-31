import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedNodeTest {

    @Test
    void creation(){
        DoublyLinkedNode<Integer> node = new DoublyLinkedNode<>(null, -9, null);
    }

}