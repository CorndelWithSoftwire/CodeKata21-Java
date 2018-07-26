package org.training.softwire.kata21;

class SinglyLinkedListTest extends AbstractListTest<SinglyLinkedList.SinglyLinkedNode, SinglyLinkedList> {

    @Override
    SinglyLinkedList getList() {
        return new SinglyLinkedList();
    }
}
