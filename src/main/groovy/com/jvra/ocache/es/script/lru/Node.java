package com.jvra.ocache.es.script.lru;

/**
 * Created by Jansel Valentin on 8/25/2015.
 *
 * This class was taken from http://www.programcreek.com/2013/03/leetcode-lru-cache-java/
 * with a little change.
 *
 */
public class Node<TKey,TValue> {
    TKey key;
    TValue value;
    Node<TKey,TValue> pre;
    Node<TKey,TValue> next;

    public Node(TKey key, TValue value) {
        this.key = key;
        this.value = value;
    }
}
