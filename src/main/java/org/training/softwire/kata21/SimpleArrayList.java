package org.training.softwire.kata21;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class SimpleArrayList implements
        SimpleList<SimpleArrayList.SimpleListNode>,
        Iterable<SimpleArrayList.SimpleListNode> {
    private static final int DEFAULT_CAPACITY = 1;

    private SimpleListNode[] array = new SimpleListNode[DEFAULT_CAPACITY];
    private int length = 0;

    @Override
    public void add(String value) {
        if (length == array.length) {
            expandArray();
        }

        array[length] = new SimpleListNode(value);
        length++;
    }

    @Override
    public Optional<SimpleListNode> find(String value) {
        Optional<SimpleListNode> result = Optional.empty();
        for (SimpleListNode node : this) {
            if (node.getValue().equals(value)) {
                result = Optional.of(node);
                break;
            }
        }
        return result;
    }

    @Override
    public void delete(SimpleListNode node) {
        int position = -1;
        for (int index = 0; index < length; index++) {
            if (array[index] == node) {
                position = index;
                break;
            }
        }

        if (position == -1) {
            throw new IllegalArgumentException("Node not found in the list");
        }

        System.arraycopy(array, position + 1, array, position, length - 1 - position);

        length--;
    }

    @Override
    public List<String> values() {
        List<String> values = new ArrayList<>(length);
        for (SimpleListNode node : this) {
            values.add(node.getValue());
        }
        return values;
    }

    @Override
    public Iterator<SimpleListNode> iterator() {
        return new Iterator<SimpleListNode>() {
            int position = -1;

            @Override
            public boolean hasNext() {
                return position < length - 1;
            }

            @Override
            public SimpleListNode next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                position += 1;
                return array[position];
            }
        };
    }

    private void expandArray() {
        SimpleListNode[] newArray = new SimpleListNode[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }

    public static class SimpleListNode implements ListNode {
        private final String value;

        SimpleListNode(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }
}
