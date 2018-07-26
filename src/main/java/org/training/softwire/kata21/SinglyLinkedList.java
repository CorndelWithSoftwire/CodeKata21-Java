package org.training.softwire.kata21;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SinglyLinkedList implements
        SimpleList<SinglyLinkedList.SinglyLinkedNode>,
        Iterable<SinglyLinkedList.SinglyLinkedNode> {
    private final SinglyLinkedNode tail = new SinglyLinkedNode(null, null);
    private final SinglyLinkedNode head = new SinglyLinkedNode(null, tail);

    @Override
    public void add(String value) {
        Optional<SinglyLinkedNode> lastNodeO = nodeStream().reduce((x, acc) -> acc);
        lastNodeO.orElse(head).setNext(new SinglyLinkedNode(value, tail));
    }

    @Override
    public Optional<SinglyLinkedNode> find(String value) {
        return nodeStream()
                .filter(node -> node.getValue().equals(value))
                .findFirst();
    }

    @Override
    public void delete(SinglyLinkedNode nodeToDelete) {
        Optional<SinglyLinkedNode> previousNode;

        if (head.getNext() == nodeToDelete) {
            previousNode = Optional.of(head);
        } else {
            previousNode = nodeStream()
                    .filter(node -> node.getNext() == nodeToDelete)
                    .findFirst();
        }

        if (!previousNode.isPresent()) {
            throw new IllegalArgumentException("Node not found in the list");
        }

        previousNode.get().setNext(nodeToDelete.getNext());
    }

    @Override
    public List<String> values() {
        return nodeStream().map(SinglyLinkedNode::getValue).collect(Collectors.toList());
    }

    @Override
    public Iterator<SinglyLinkedList.SinglyLinkedNode> iterator() {
        return new Iterator<SinglyLinkedList.SinglyLinkedNode>() {
            SinglyLinkedList.SinglyLinkedNode currentNode = SinglyLinkedList.this.head;

            @Override
            public boolean hasNext() {
                return currentNode.getNext() != SinglyLinkedList.this.tail;
            }

            @Override
            public SinglyLinkedList.SinglyLinkedNode next() {
                currentNode = currentNode.getNext();
                if (currentNode == SinglyLinkedList.this.tail) {
                    throw new NoSuchElementException();
                }
                return currentNode;
            }
        };
    }

    private Stream<SinglyLinkedList.SinglyLinkedNode> nodeStream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public static class SinglyLinkedNode implements ListNode {
        private final String value;
        private SinglyLinkedNode next;

        private SinglyLinkedNode(String value, SinglyLinkedNode next) {
            this.value = value;
            this.next = next;
        }

        @Override
        public String getValue() {
            return value;
        }

        public SinglyLinkedNode getNext() {
            return next;
        }

        private void setNext(SinglyLinkedNode next) {
            this.next = next;
        }
    }
}
