package org.training.softwire.kata21;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.equalTo;

abstract class AbstractListTest<N extends ListNode, L extends SimpleList<N>> {

    abstract L getList();

    /**
     *  Translated from:
     * list = List.new
     * assert_nil(list.find("fred"))
     * list.add("fred")
     * assert_equal("fred", list.find("fred").value())
     * assert_nil(list.find("wilma"))
     * list.add("wilma")
     * assert_equal("fred",  list.find("fred").value())
     * assert_equal("wilma", list.find("wilma").value())
     * assert_equal(["fred", "wilma"], list.values())
     *
     * It would be cleaner to write separate Arrange-Act-Assert tests for each part of this!
     */
    @Test
    void addingItemsToTheList() {
        L list = getList();
        assertThat(list.find("fred").map(ListNode::getValue), equalTo(Optional.empty()));
        list.add("fred");
        assertThat(list.find("fred").map(ListNode::getValue), equalTo(Optional.of("fred")));
        assertThat(list.find("wilma").map(ListNode::getValue), equalTo(Optional.empty()));
        list.add("wilma");
        assertThat(list.find("fred").map(ListNode::getValue), equalTo(Optional.of("fred")));
        assertThat(list.find("wilma").map(ListNode::getValue), equalTo(Optional.of("wilma")));
        assertThat(list.values(), equalTo(Arrays.asList("fred", "wilma")));
    }

     /**
      *  Translated from:
      *  list = List.new
      *  list.add("fred")
      *  list.add("wilma")
      *  list.add("betty")
      *  list.add("barney")
      *  assert_equal(["fred", "wilma", "betty", "barney"], list.values())
      *  list.delete(list.find("wilma"))
      *  assert_equal(["fred", "betty", "barney"], list.values())
      *  list.delete(list.find("barney"))
      *  assert_equal(["fred", "betty"], list.values())
      *  list.delete(list.find("fred"))
      *  assert_equal(["betty"], list.values())
      *  list.delete(list.find("betty"))
      *  assert_equal([], list.values())
      *
      *  It would be cleaner to write separate Arrange-Act-Assert tests for each part of this!*/
    @Test
    void deletingItemsFromTheList() {
        L list = getList();

        list.add("fred");
        list.add("wilma");
        list.add("betty");
        list.add("barney");
        assertThat(list.values(), equalTo(Arrays.asList("fred", "wilma", "betty", "barney")));
        list.delete(list.find("wilma").get());

        assertThat(list.values(), equalTo(Arrays.asList("fred", "betty", "barney")));
        list.delete(list.find("barney").get());

        assertThat(list.values(), equalTo(Arrays.asList("fred", "betty")));
        list.delete(list.find("fred").get());

        assertThat(list.values(), equalTo(Collections.singletonList("betty")));

        list.delete(list.find("betty").get());
        assertThat(list.values(), emptyIterable());
    }

}
