class DoublyLinkedNode<E> {
    E value;
    DoublyLinkedNode<E> prev, next;

    DoublyLinkedNode(DoublyLinkedNode<E> prev, E value, DoublyLinkedNode<E> next) {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }
}
