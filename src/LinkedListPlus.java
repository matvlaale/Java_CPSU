import com.sun.deploy.security.ruleset.DefaultRule;

public class LinkedListPlus {
    public static int id = 1;

    public static void main(String[] args) {
        DoubleRelatedList mas = new DoubleRelatedList();
        Cat irs = new Cat(11, "Irs");
        for (int i = 1; i <= 5; i++) {
            mas.push(new Cat(i * 2, "Cat_" + i));
        }
        System.out.println(mas.toString());

        DRIterator iterator = new DRIterator(mas);
        for (int i = 0; i < 7; i++) {
            iterator.next();
        }
        System.out.println(iterator.getCurrent().toString() + " end? " + iterator.atEnd());
        iterator.insertAfter(irs);
        iterator.insertBefore(irs);
        System.out.println(mas.toString());

        for (int i = 0; i < 7; i++) {
            iterator.previous();
        }
        System.out.println(iterator.getCurrent().toString() + " end? " + iterator.atEnd());
        iterator.insertAfter(irs);
        iterator.insertBefore(irs);
        System.out.println(mas.toString());

        iterator.next();
        iterator.next();
        iterator.next();
        iterator.insertAfter(irs);
        iterator.insertBefore(irs);
        System.out.println(mas.toString());

        iterator.deleteCurrent();
        System.out.println(mas.toString());
    }
}

class DRIterator {
    private DoubleRelatedList list;
    private DoubleRelatedList.Node cur;

    public DRIterator(DoubleRelatedList list) {
        this.list = list;
        reset();
    }

    public void reset() {
        cur = list.getHead();
    }

    public void next() {
        if (hasNext()) cur = cur.getNext();
    }

    public void previous() {
        if (hasPrev()) cur = cur.getPrev();
    }

    public Cat getCurrent() {
        return cur.getCat();
    }

    public boolean hasNext() {
        return cur.getNext() != null;
    }

    public boolean hasPrev() {
        return cur.getPrev() != null;
    }

    public boolean atEnd() {
        return !hasNext();
    }

    public void insertAfter(Cat cat) {
        if(!hasNext()) list.pushEnd(cat);
        else {
            DoubleRelatedList.Node newNode = new DoubleRelatedList.Node(cat);
            newNode.setNext(cur.getNext());
            newNode.setPrev(cur);
            cur.getNext().setPrev(newNode);
            cur.setNext(newNode);
        }
    }

    public void insertBefore(Cat cat) {
        if (!hasPrev()) list.push(cat);
        else {
            DoubleRelatedList.Node newNode = new DoubleRelatedList.Node(cat);
            newNode.setNext(cur);
            newNode.setPrev(cur.getPrev());
            cur.getPrev().setNext(newNode);
            cur.setPrev(newNode);
        }
    }

    public void deleteCurrent() {
        if (!hasPrev()) {
            list.pop();
            reset();
        } else {
            cur.getNext().setPrev(cur.getPrev());
            cur.getPrev().setNext(cur.getNext());
        }
    }
}

class DoubleRelatedList {

    private Node head;
    private Node tail;

    public DoubleRelatedList() {
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void push(Cat cat) {
        Node node = new Node(cat);
        node.next = head;
        node.prev = null;
        if (head != null) head.prev = node;
        else tail = node;
        head = node;
    }

    public void pushEnd(Cat cat){
        Node node = new Node(cat);
        node.prev = tail;
        node.next = null;
        if (tail != null) tail.next = node;
        else head = node;
        tail = node;
    }

    public Cat pop() {
        if (isEmpty()) return null;
        Cat zap = head.cat;
        head = head.next;
        if (head == null) tail = null;
        else head.prev = null;
        return zap;
    }

    Node getHead() {
        if (isEmpty()) return null;
        else return head;
    }

    public boolean contains(Cat cat) {
        Node nodeHead = head;
        Node nodeTail = tail;
        while (nodeHead != nodeTail && nodeHead.next != nodeTail) {
            if (nodeHead.cat.equals(cat) || nodeTail.cat.equals(cat)) return true;
            nodeHead = nodeHead.next;
            nodeTail = nodeTail.prev;
        }
        return nodeHead.cat.equals(cat) || nodeTail.cat.equals(cat);
    }

    public boolean delete(Cat cat) {
        Node nodeHead = head;
        Node nodeTail = tail;
        if (head.cat.equals(cat)) {
            pop();
            return true;
        }
        while (nodeHead != nodeTail && nodeHead.next != nodeTail) {
            if (nodeHead.cat.equals(cat)) {
                nodeHead.prev.next = nodeHead.next;
                nodeHead.next.prev = nodeHead.prev;
                return true;
            }
            if (nodeTail.cat.equals(cat)) {
                nodeTail.prev.next = nodeTail.next;
                nodeTail.next.prev = nodeTail.prev;
                return true;
            }
            nodeHead = nodeHead.next;
            nodeTail = nodeTail.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        StringBuilder add = new StringBuilder("]");
        Node node = head;
        Node addNode = tail;
        while (node.next != addNode && node != addNode) {
            sb.append(node.toString()).append(", ");
            add.insert(0, ", " + addNode.toString());
            node = node.next;
            addNode = addNode.prev;
        }
        if (node == addNode) sb.append(node.toString());
        else sb.append(node.toString()).append(", ").append(addNode.toString());
        return sb.toString() + add.toString();
    }

    static class Node {
        private Cat cat;
        private Node next;
        private Node prev;

        public Node(Cat cat) {
            this.cat = cat;
        }

        @Override
        public String toString() {
            return cat.toString();
        }

        Node getNext() {
            return next;
        }

        Node getPrev() {
            return prev;
        }

        void setNext(Node next) {
            this.next = next;
        }

        void setPrev(Node prev) {
            this.prev = prev;
        }

        Cat getCat() {
            return cat;
        }
    }
}

class Cat {
    public int id;
    private int age;
    private String name;

    public Cat(int age, String name) {
        id = LinkedListPlus.id++;
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " " + age;
    }

    public boolean equals(Cat cat) {
        return this == cat || cat.age == age && cat.name.equals(name);
    }

    public int getAge() {
        return age;
    }
}