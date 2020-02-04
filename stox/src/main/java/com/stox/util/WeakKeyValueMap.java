package com.stox.util;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public class WeakKeyValueMap<K, V> extends WeakHashMap<K, WeakReference<V>> {

	 public V getValue(K key) {
         WeakReference<V> wr = super.get(key);
         if (wr != null) {
             return wr.get();
         }
         return null;
     }

     public V putValue(K key, V value) {
         WeakReference<V> wr = super.put(key, new WeakReference<V>(value));
         if (wr != null) {
             return wr.get();
         }
         return null;
     }
	
}
