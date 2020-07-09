import java.util.Arrays;
import java.util.NoSuchElementException;

public class StackPlus {
    public static void main(String[] args) {
        String mas = "321GFEDCBA";
        System.out.println(turn(turn(mas)) + "->" + turn(mas));
        Deque dek = new Deque(5);
        PriorityQueue pqu = new PriorityQueue(5);
        checkDeque(dek);
        System.out.println();
        checkPQueue(pqu);
    }

    public static String turn(String base) {
        StringBuilder sb = new StringBuilder();
        Stack mas = new Stack(base.length());
        for (int i = 0; i < base.length(); i++) {
            mas.push(base.charAt(i));
        }
        for (int i = 0; i < base.length(); i++) {
            sb.append(mas.pop());
        }
        return sb.toString();
    }

    public static void checkPQueue(PriorityQueue pqueue) {
        pqueue.insert(1, 20);
        pqueue.info();
        pqueue.insert(2, 40);
        pqueue.info();
        pqueue.insert(3, 10);
        pqueue.info();
        pqueue.insert(4, 30);
        System.out.print("pque: ");
        pqueue.info();
        System.out.print("real: ");
        pqueue.simpleInfo();
        pqueue.remove();
        pqueue.info();
        pqueue.insert(5, 30);
        pqueue.info();
        pqueue.insert(6, 30);
        pqueue.insert(7, 30);
        System.out.print("pque: ");
        pqueue.info();
        System.out.print("real: ");
        pqueue.simpleInfo();
    }

    public static void checkDeque(Deque deque) {
        deque.insertHead(5);
        deque.info();
        deque.insertHead(6);
        deque.info();
        deque.insertTail(7);
        deque.info();
        deque.insertHead(8);
        deque.insertHead(9);
        deque.insertHead(10);
        System.out.print("deque: ");
        deque.info();
        System.out.print("real: ");
        deque.simpleInfo();
        deque.removeHead();
        deque.removeHead();
        deque.info();
        deque.insertTail(9);
        deque.info();
        deque.insertTail(10);
        deque.info();
        deque.insertTail(11);
        System.out.print("deque: ");
        deque.info();
        System.out.print("real: ");
        deque.simpleInfo();
        deque.removeTail();
        deque.removeTail();
        deque.info();
    }
}

class Stack {
    private int capacity;
    private char[] stack;
    private int head;

    public String name;

    public Stack(int capacity) {
        this.capacity = capacity;
        stack = new char[capacity];
        head = -1;
    }

    public boolean isFull() {
        return head == capacity - 1;
    }

    public boolean isEmpty() {
        return head == -1;
    }

    public void push(char elem) {
        stack[++head] = elem;
    }

    public char pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty");
        return stack[head--];
    }

    public char peak() {
        return stack[head];
    }
}

class PQEl {
    private int value;
    private int priority;

    public PQEl(int element, int priority) {
        this.value = element;
        this.priority = priority;
    }

    public int getValue() {
        return value;
    }

    public int getPriority() {
        return priority;
    }
}

class PriorityQueue {
    private int capacity;
    private PQEl[] queue;
    private int tail, items;

    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        queue = new PQEl[capacity];
        tail = -1;
        items = 0;
    }

    public void simpleInfo() {
        for (PQEl pqel : queue) {
            if (pqel == null) System.out.print(null + " ");
            else System.out.print(pqel.getValue() + " ");
        }
        System.out.println();
    }

    public void info() {
        if (isEmpty()) throw new NoSuchElementException("P. Queue is empty");
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i <= tail; i++) {
            sb.append(queue[i].getValue());
            if (i != tail) sb.append(", ");
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    public boolean isEmpty() {
        return items == 0;
    }

    public boolean isFull() {
        return items == capacity;
    }

    public int size() {
        return items;
    }

    public void insert(int value, int priority) {
        if (isFull()) {
            capacity *= 2;
            PQEl[] newQ = new PQEl[capacity];
            System.arraycopy(queue, 0, newQ, 0, queue.length);
            queue = newQ;
        }
        PQEl elem = new PQEl(value, priority);
        if (isEmpty()) {
            queue[++tail] = elem;
            items++;
        } else if (items == 1) {
            if (queue[tail].getPriority() > priority) {
                queue[++tail] = elem;
            } else {
                PQEl zap = queue[tail];
                queue[tail] = elem;
                queue[++tail] = zap;
            }
            items++;
        } else {
            if (queue[0].getPriority() < priority) {
                System.arraycopy(queue, 0, queue, 1, items);
                tail++;
                queue[0] = elem;
                items++;
                return;
            } else if (queue[tail].getPriority() >= priority) {
                queue[++tail] = elem;
                items++;
                return;
            }
            for (int i = 0; i < tail; i++) {
                if (queue[i].getPriority() < priority) {
                    System.arraycopy(queue, i, queue, i + 1, tail - i + 1);
                    queue[i] = elem;
                    tail++;
                    items++;
                    return;
                }
            }
        }
    }

    public int remove() {
        if (isEmpty()) throw new NoSuchElementException("P. Queue is empty");
        PQEl temp = queue[0];
        System.arraycopy(queue, 1, queue, 0, items);
        items--;
        tail--;
        return temp.getValue();
    }

    public int peak() {
        return queue[0].getValue();
    }
}

class Deque {
    private int capacity;
    private int[] deque;
    private int head, tail, items;

    public Deque(int capacity) {
        this.capacity = capacity;
        deque = new int[capacity];
        head = capacity / 2;
        tail = capacity / 2 - 1;
        items = 0;
    }

    public void simpleInfo() {
        System.out.println(Arrays.toString(deque));
    }

    public void info() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        StringBuilder sb = new StringBuilder("[");
        if (tail < head) {
            for (int i = head; i < capacity; i++) {
                sb.append(deque[i] + ", ");
            }
            for (int i = 0; i <= tail; i++) {
                sb.append(deque[i]);
                if (i != tail) sb.append(", ");
            }
        } else {
            for (int i = head; i <= tail; i++) {
                sb.append(deque[i]);
                if (i != tail) sb.append(", ");
            }
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    public boolean isEmpty() {
        return items == 0;
    }

    public boolean isFull() {
        return items == capacity;
    }

    public int size() {
        return items;
    }

    public void insertTail(int value) {
        if (isFull()) {
            capacity *= 2;
            int[] newQ = new int[capacity];
            if (tail >= head) {
                System.arraycopy(deque, 0, newQ, 0, deque.length);
            } else {
                System.arraycopy(deque, 0, newQ, 0, tail + 1);
                System.arraycopy(deque, head,
                        newQ, capacity - (deque.length - head),
                        deque.length - head);
            }
            head += capacity / 2;
            deque = newQ;
        }
        if (tail == capacity - 1) tail = -1;
        deque[++tail] = value;
        items++;
    }

    public void insertHead(int value) {
        if (isFull()) {
            capacity *= 2;
            int[] newQ = new int[capacity];
            if (tail >= head) {
                System.arraycopy(deque, 0, newQ, 0, deque.length);
            } else {
                System.arraycopy(deque, 0, newQ, 0, tail + 1);
                System.arraycopy(deque, head,
                        newQ, capacity - (deque.length - head),
                        deque.length - head);
            }
            head += capacity / 2;
            deque = newQ;
        }
        if (head == 0) head = capacity;
        deque[--head] = value;
        items++;
    }

    public int removeTail() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        deque[tail] = 0;
        int temp = deque[tail--];
        tail = (tail + capacity) % capacity;
        items--;
        return temp;
    }

    public int removeHead() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        deque[head] = 0;
        int temp = deque[head++];
        head %= capacity;
        items--;
        return temp;
    }

    public int peakHead() {
        return deque[head];
    }

    public int peakTail() {
        return deque[tail];
    }
}