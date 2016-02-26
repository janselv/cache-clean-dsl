package com.jvra.ocache.es.script.lru;

import java.util.HashMap;

/**
 * Created by Jansel Valentin on 8/25/2015.
 *
 *
 * This class was taken from http://www.programcreek.com/2013/03/leetcode-lru-cache-java/
 * with a little change.
 */
public class LRUCache<TKey,TValue> {
    private int capacity;
    private HashMap<TKey, Node<TKey,TValue>> map = new HashMap<TKey, Node<TKey,TValue>>();
    private Node head;
    private Node end;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public TValue get(TKey key) {
        if(map.containsKey(key)){
            Node<TKey,TValue> n = map.get(key);
            remove(n);
            setHead(n);
            return n.value;
        }

        return null;
    }

    public void remove(Node<TKey,TValue> n){
        if(n.pre!=null){
            n.pre.next = n.next;
        }else{
            head = n.next;
        }

        if(n.next!=null){
            n.next.pre = n.pre;
        }else{
            end = n.pre;
        }

    }

    public void setHead(Node<TKey,TValue> n){
        n.next = head;
        n.pre = null;

        if(head!=null)
            head.pre = n;

        head = n;

        if(end ==null)
            end = head;
    }

    public void set(TKey key, TValue value) {
        if(map.containsKey(key)){
            Node<TKey,TValue> old = map.get(key);
            old.value = value;
            remove(old);
            setHead(old);
        }else{
            Node<TKey,TValue> created = new Node<TKey,TValue>(key, value);
            if(map.size()>=capacity){
                map.remove(end.key);
                remove(end);
                setHead(created);

            }else{
                setHead(created);
            }

            map.put(key, created);
        }
    }
}