import java.util.ArrayList;

/**
 * Basic hashTable from scratch
 * Requirement 1: Time complexity for search should be O(1)
 * Requirement 2: Handle collisions
 * Requirement 3: Maintain good performance by rehashing
 * @param <K>
 * @param <V>
 */
public class hashTable<K,V> {

    class Entry {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private static final int DEFAULT_CAPACITY = 10;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;
    private ArrayList<ArrayList<Entry>> buckets = new ArrayList<ArrayList<Entry>>(DEFAULT_CAPACITY);
    private int size;


    hashTable() {

        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            buckets.add(new ArrayList<>());
        }
        size = 0;
    }

    int hash(K key) {
        return key.hashCode() % buckets.size();
    }

    /**
     *
     * @param key
     * @param value
     */
    public void insert(K key, V value) {

        if ((double) size / buckets.size() > LOAD_FACTOR_THRESHOLD) {
            rehash();
        }

        int index = hash(key);

        ArrayList<Entry> chain = buckets.get(index);

        if (chain == null) {
            chain = new ArrayList<Entry>();
            buckets.set(index, chain);
        }

        //replacing existing entry
        for (var entry : chain) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        //if no entry, add it
        chain.add(new Entry(key, value));
        size++;
    }

    /**
     *
     */
    private void rehash() {
        ArrayList<ArrayList<Entry>> oldBuckets = buckets;
        int newCapacity = oldBuckets.size() * 2;
        buckets = new ArrayList<>(newCapacity);
        for (int i = 0; i < newCapacity; i++) {
            buckets.add(new ArrayList<>());
        }
        size = 0;

        for (ArrayList<Entry> bucket : oldBuckets) {
            for (var entry : bucket) {
                insert(entry.key, entry.value);
            }
        }

    }

    public void delete(K key) throws Exception {
        int index = hash(key);
        ArrayList<Entry> chain = buckets.get(index);

        if (chain == null || chain.isEmpty()) {
            throw new Exception("Key not found");
        }

        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key.equals(key)) {
                chain.remove(i);
                size--;
                return;
            }
        }

        throw new Exception("Key not found");

    }

    public V lookup (K key) throws Exception {
        int index = hash(key);
        ArrayList<Entry> chain = buckets.get(index);
        if (chain == null) {
            throw new Exception("Key not found");
        }
        for (var e : chain) {
            if (e.key.equals(key)) {

                return e.value;
            }
        }
        throw new Exception("Key not found");
    }

    public int size() {
        return size;
    }

    public double getLoadFactorThreshold() {
        return LOAD_FACTOR_THRESHOLD;
    }

}
