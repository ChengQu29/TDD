import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {
    private hashTable<Integer, Integer> hashTable;

    @BeforeEach
    public void setUp() {
        hashTable = new hashTable<>();
    }

    @Test
    public void firstTestNaiveInsert() throws Exception {

        for (int i = 0; i < 5; i++) {
            hashTable.insert(i,i);
        }

        for (int i =0; i < 5; i++) {
            System.out.printf("key is %d, value is %d\n", i, hashTable.lookup(i));
            assertEquals(i, hashTable.lookup(i));
        }
    }

    @Test
    public void testHandleCollisions() throws Exception {
        hashTable.insert(1, 1);
        hashTable.insert(11, 11); // Assuming DEFAULT_CAPACITY = 10, this should collide with 1

        assertEquals(1, hashTable.lookup(1));
        assertEquals(11, hashTable.lookup(11));
    }

    @Test
    public void secondTestCollision() throws Exception {

        for (int i = 0; i < 30; i++) {
            hashTable.insert(i,i);
        }

        for (int i =0; i < 30; i++) {
            //System.out.printf("key is %d, value is %d\n", i, hashTable.lookup(i));
            assertEquals(i, hashTable.lookup(i));
        }
    }

    @Test
    public void testDelete() throws Exception {
        for (int i = 0; i < 5; i++) {
            hashTable.insert(i, i);
        }

        hashTable.delete(2);
        assertThrows(Exception.class, () -> hashTable.lookup(2));

        for (int i = 0; i < 5; i++) {
            if (i != 2) {
                //System.out.printf("key is %d, value is %d\n", i, hashTable.lookup(i));
                assertEquals(i, hashTable.lookup(i));
            }
        }
    }

    @Test
    public void testUpdateValue() throws Exception {
        hashTable.insert(1, 1);
        hashTable.insert(1, 10);

        assertEquals(10, hashTable.lookup(1));
    }

    @Test
    public void testRehashing() throws Exception {
        hashTable<Integer, String> hashTable = new hashTable<>();

        // Insert elements to trigger rehashing
        int initialCapacity = hashTable.size();
        int elementsToAdd = (int) (initialCapacity * hashTable.getLoadFactorThreshold()) + 1;

        for (int i = 0; i < elementsToAdd; i++) {
            hashTable.insert(i, "Value" + i);
        }

        assertTrue(hashTable.size() > initialCapacity);

        for (int i = 0; i < elementsToAdd; i++) {
            assertEquals("Value" + i, hashTable.lookup(i));
        }

        assertEquals(elementsToAdd, hashTable.size());
    }
}