import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        newArray mas = new newArray(100);
        newMas(mas);

        long time = System.currentTimeMillis();
        bubbleSort(mas);
        System.out.println("Bubble: " + (System.currentTimeMillis() - time) + " " + isSorted(mas));

        mas.clear();
        newMas(mas);

        time = System.currentTimeMillis();
        selectSort(mas);
        System.out.println("Select: " + (System.currentTimeMillis() - time) + " " + isSorted(mas));

        mas.clear();
        newMas(mas);

        time = System.currentTimeMillis();
        insertSort(mas);
        System.out.println("Insert: " + (System.currentTimeMillis() - time) + " " + isSorted(mas));

        mas.clear();
        newMas(mas);

        time = System.currentTimeMillis();
        countingSort(mas);
        System.out.println("Counting: " + (System.currentTimeMillis() - time) + " " + isSorted(mas));
    }

    public static void newMas(newArray mas) {
        for (int i = 200; i > 0; i--) {
            for (int j = 1; j < 200; j++) {
                mas.add(i * j);
            }
        }
    }

    public static void bubbleSort(newArray mas) {
        for (int j = 1; j < mas.getSize(); j++) {
            for (int i = 1; i < mas.getSize() - j + 1; i++) {
                if (mas.get(i - 1) > mas.get(i)) {
                    mas.swap(i - 1, i);
                }
            }
        }
    }

    public static void selectSort(newArray mas) {
        int min = 0;
        for (int i = 0; i < mas.getSize(); i++) {
            for (int j = i; j < mas.getSize(); j++) {
                if (mas.get(j) < mas.get(min)) min = j;
            }
            if (mas.get(i) > mas.get(min)) {
                mas.insert(i, mas.get(min));
                mas.removeInd(min + 1);
            }
            min = i + 1;
        }
    }

    public static void insertSort(newArray mas) {
        for (int i = 1; i < mas.getSize(); i++) {
            int zap = mas.get(i);
            int j = i;
            while (j > 0 && mas.get(j - 1) >= zap) {
                mas.set(j, mas.get(--j));
            }
            mas.set(j, zap);
        }
    }

    public static void countingSort(newArray mas) {
        int[] maxMin = maxInNew(mas);
        newArray digits = makeAL(maxMin[0] + 1);
        for (int i = 0; i < mas.getSize(); i++) {
            digits.set(mas.get(i) + maxMin[1], digits.get(mas.get(i) + maxMin[1]) + 1);
        }
        mas.clear();
        int index = 0;
        int i = 0;
        while (i != maxMin[0]) {
            while (digits.get(index) == 0) index++;
            while (digits.get(index) != 0) {
                digits.set(index + maxMin[1], digits.get(index + maxMin[1]) - 1);
                mas.add(index - maxMin[1]);
                i++;
            }
        }
    }

    public static int[] maxInNew(newArray mas) {
        int max = mas.get(0);
        int min = mas.get(0);
        for (int i = 0; i < mas.getSize(); i++) {
            if (mas.get(i) > max) max = mas.get(i);
            else if (mas.get(i) < min) min = mas.get(i);
        }
        return new int[]{max - min, -min};
    }

    public static newArray makeAL(int max) {
        newArray mas = new newArray(max);
        for (int i = 0; i < max; i++) {
            mas.add(0);
        }
        return mas;
    }

    public static boolean isSorted(newArray mas) {
        for (int i = 1; i < mas.getSize(); i++) {
            if (mas.get(i - 1) > mas.get(i)) return false;
        }
        return true;
    }
}

class newArray {
    private int[] mas;
    private int size;
    private int capacity;

    public newArray(int capacity) {
        this.capacity = capacity;
        mas = new int[capacity];
        this.size = 0;
    }

    public void removeVal(int value) {
        int i;
        for (i = 0; i < size; i++) {
            if (mas[i] == value) break;
        }
        if (size - 1 - i >= 0)
            removeInd(i);
        size--;
    }

    public void removeInd(int index) {
        System.arraycopy(mas, index + 1, mas, index, size - 1 - index);
        size--;
    }

    public void clear() {
        size = 0;
    }

    public int get(int index) {
        if (index >= size || index < 0) throw new IndexOutOfBoundsException(index + "");
        else return mas[index];
    }

    public void set(int index, int value) {
        if (index >= size || index < 0) throw new IndexOutOfBoundsException(index + "");
        else mas[index] = value;
    }

    public void insert(int index, int value) {
        size++;
        if (size >= capacity) increase();
        System.arraycopy(mas, index, mas, index + 1, size - index);
        mas[index] = value;
    }

    public void add(int value) {
        size++;
        if (size >= capacity) increase();
        mas[size] = value;
    }

    public int search(int value) {
        int i;
        for (i = 0; i < size; i++) {
            if (mas[i] == value) break;
        }
        return i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(mas[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public void increase() {
        int[] temp = mas;
        mas = new int[capacity * 2];
        System.arraycopy(temp, 0, mas, 0, size);
        capacity *= 2;
    }

    public int getSize() {
        return size;
    }

    public void swap(int i1, int i2) {
        int zap = mas[i1];
        mas[i1] = mas[i2];
        mas[i2] = zap;
    }
}