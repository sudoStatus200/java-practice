package com.project.algorithm;

import com.project.algorithm.exception.*;

import java.util.Objects;

public class DoublyLinkedList<E> {
    private final DoublyLinkedListNode<E> dummyHead;
    private final DoublyLinkedListNode<E> dummyTail;

    public DoublyLinkedList() {
        this.dummyHead = new DoublyLinkedListNode<>(null);

    }
}
