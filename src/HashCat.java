package ru.gb.jads.hlesson.online;

import java.util.Objects;

public class HashCat {
    public static class Item {
        int data;

        public Item(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return data == item.data;
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
    }

    private Item[] hashArray;
    private int arrSize;
    private Item nullItem;

    public HashCat(int capacity) {
        this.hashArray = new Item[capacity];
        this.arrSize = capacity;
        this.nullItem = new Item(-1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrSize; i++) {
            sb.append((hashArray[i] != null) ? hashArray[i].getData() : "*");
            if (i < arrSize - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private int hashFunc(int key) {
        return key % arrSize;
    }

    private int hashFuncDouble(int key) {
        return 7 - (key % 7);
    }

    private int linearProbe(int hashVal) {
        ++hashVal;
        hashVal %= arrSize;
        return hashVal;
    }

    private int quadProbe(int hashVal, int step) {
        hashVal += step * step;
        hashVal %= arrSize;
        return hashVal;
    }

    private int doubleHash(int hashVal, int key) {
        int step = hashFuncDouble(key);
        hashVal += step;
        hashVal %= arrSize;
        return hashVal;
    }

    public Item find(int key) {
        int hashVal = hashFunc(key);
        int count = 1;
        while (hashArray[hashVal] != null) {
            if (hashArray[hashVal].getData() == key)
                return hashArray[hashVal];

            hashVal = doubleHash(hashVal, key);
        }
        return null;
    }

    public void insert(Item item) {
        int key = item.getData();
        int hashVal = hashFunc(key);
        if (isFull()) increaseCapacity();
        int count = 1;
        while (hashArray[hashVal] != null && hashArray[hashVal] != nullItem) {
            hashVal = doubleHash(hashVal, key);
        }

        hashArray[hashVal] = item;
    }

    public Item delete(int key) {
        int hashVal = hashFunc(key);
        int count = 1;
        while (hashArray[hashVal] != null) {
            if (hashArray[hashVal].getData() == key) {
                Item temp = hashArray[hashVal];
                hashArray[hashVal] = nullItem;
                return temp;
            }
            hashVal = doubleHash(hashVal, key);
        }
        return null;
    }

    private boolean isFull() {
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] == null || hashArray[i] == nullItem)
                return false;
        }
        return true;
    }

    private void increaseCapacity() {
        arrSize *= 2;
        Item[] oldArr = hashArray;
        hashArray = new Item[arrSize];
        for (int i = 0; i < oldArr.length; i++) {
            insert(oldArr[i]);
        }
    }


}
