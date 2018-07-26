package org.training.softwire.kata21;

import java.util.List;
import java.util.Optional;

public interface SimpleList<T extends ListNode> extends Iterable<T> {
    void add(String value);
    Optional<T> find(String value);
    void delete(T node);
    List<String> values();
}
