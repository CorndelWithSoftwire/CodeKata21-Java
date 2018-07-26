package org.training.softwire.kata21;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class DoublyLinkedList implements
        SimpleList<DoublyLinkedList.DoublyLinkedNode>,
        Iterable<DoublyLinkedList.DoublyLinkedNode> {
    private final DoublyLinkedNode head = new DoublyLinkedNode(null, null, null);
    private final DoublyLinkedNode tail = new DoublyLinkedNode(null, null, null);

    public DoublyLinkedList() {
        head.setNext(tail);
        tail.setPrev(head);
    }

    @Override
    public void add(String value) {
        DoublyLinkedNode oldTail = tail.getPrev();
        DoublyLinkedNode newTail = new DoublyLinkedNode(value, oldTail, tail);
        oldTail.setNext(newTail);
        tail.setPrev(newTail);
    }

    @Override
    public Optional<DoublyLinkedNode> find(String value) {
        return nodeStream().filter(node -> node.getValue().equals(value)).findFirst();
    }

    @Override
    public void delete(DoublyLinkedNode nodeToDelete) {
        if (nodeStream().noneMatch(node -> node.equals(nodeToDelete))) {
            throw new IllegalArgumentException("Node not found in the list");
        }

        nodeToDelete.getPrev().setNext(nodeToDelete.getNext());
        nodeToDelete.getNext().setPrev(nodeToDelete.getPrev());
    }

    @Override
    public List<String> values() {
        return nodeStream().map(DoublyLinkedNode::getValue).collect(Collectors.toList());
    }

    @Override
    public Iterator<DoublyLinkedNode> iterator() {
        return new Iterator<DoublyLinkedNode>() {
            DoublyLinkedNode currentNode = DoublyLinkedList.this.head;

            @Override
            public boolean hasNext() {
                return currentNode.getNext() != DoublyLinkedList.this.tail;
            }

            @Override
            public DoublyLinkedNode next() {
                currentNode = currentNode.getNext();
                if (currentNode == DoublyLinkedList.this.tail) {
                    throw new NoSuchElementException();
                }
                return currentNode;
            }
        };
    }

    private Stream<DoublyLinkedNode> nodeStream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public static class DoublyLinkedNode implements ListNode {
        private final String value;
        private DoublyLinkedNode next;
        private DoublyLinkedNode prev;

        private DoublyLinkedNode(String value, DoublyLinkedNode prev, DoublyLinkedNode next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String getValue() {
            return value;
        }

        public DoublyLinkedNode getNext() {
            return next;
        }

        public DoublyLinkedNode getPrev() {
            return prev;
        }

        private void setNext(DoublyLinkedNode next) {
            this.next = next;
        }

        private void setPrev(DoublyLinkedNode prev) {
            this.prev = prev;
        }
    }
}
