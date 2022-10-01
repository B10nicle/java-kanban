package kanban.managers.historyManagers.inMemoryHistoryManager;

/**
 * @author Oleg Khilko
 */

class Node<E> {

    E item;
    Node<E> next;
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {

        this.item = element;
        this.next = next;
        this.prev = prev;

    }

}