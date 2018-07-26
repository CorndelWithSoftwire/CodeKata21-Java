package org.training.softwire.kata21;

class SimpleArrayListTest extends AbstractListTest<SimpleArrayList.SimpleListNode, SimpleArrayList> {

    @Override
    SimpleArrayList getList() {
        return new SimpleArrayList();
    }
}

