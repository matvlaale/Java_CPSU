import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.Method;

public class TreesPlus {

    public static Random rand = new Random();

    public static void main(String[] args) {
        Tree[] one = new Tree[20000];
        for (int i = 0; i < 20000; i++) {
            one[i] = new Tree();
        }
        for (Tree tree : one) {
            while (tree.level() != 6)
                tree.insert(new Cat(rand.nextInt(200) - 100, "Cat"));
        }
        int balance = 0;
        for (Tree tree : one) {
            if (tree.isBalanced()) balance++;
        }
        if (balance != 0) System.out.println("Несбалансированных деревьев: " + (100 - (20000.0 - balance) / 20000) + "%");
        else System.out.println("Сбалансированых = " + 0);
    }
}

class Tree {
    // travers
    // delete

    private static class TreeNode implements Comparable {
        private Cat c;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(Cat c) {
            this.c = c;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "c=" + c.toString() +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            if (!(o instanceof Cat))
                throw new ClassCastException("Not a cat");
            return c.getAge() - ((Cat) o).getAge();
        }
    }

    TreeNode root;

    public void insert(Cat c) {
        TreeNode node = new TreeNode(c);
        if (root == null) {
            root = node;
        } else {
            TreeNode current = root;
            TreeNode parent = null;
            while (true) {
                parent = current;
                if (c.getAge() < current.c.getAge()) {
                    current = current.left;
                    if (current == null) {
                        parent.left = node;
                        return;
                    }
                } else if (c.getAge() > current.c.getAge()) {
                    current = current.right;
                    if (current == null) {
                        parent.right = node;
                        return;
                    }
                } else {
                    return;
                }
            }
        }
    }

    public Cat find(int age) {
        TreeNode current = root;
        while (current.c.getAge() != age) {
            current = (age < current.c.getAge()) ? current.left : current.right;
            if (current == null) return null;
        }
        return current.c;
    }

    private void preOrderTraverse(TreeNode current) {
        if (current != null) {
            System.out.print(current.c.getAge() + " ");
            preOrderTraverse(current.left);
            preOrderTraverse(current.right);
        }
    }

    public void displayTree() {
        preOrderTraverse(root);
    }

    public boolean delete(int age) {
        TreeNode current = root;
        TreeNode parent = root;
        boolean isLeftChild = true;

        while (current.c.getAge() != age) {
            parent = current;
            if (age < current.c.getAge()) {
                current = current.left;
                isLeftChild = true;
            } else {
                current = current.right;
                isLeftChild = false;
            }
            if (current == null) {
                return false;
            }
        }

        if (current.left == null && current.right == null) {
            if (current == root)
                root = null;
            else if (isLeftChild)
                parent.left = null;
            else
                parent.right = null;
        } else if (current.right == null) {
            if (isLeftChild)
                parent.left = current.left;
            else
                parent.right = current.left;
        } else if (current.left == null) {
            if (isLeftChild)
                parent.left = current.right;
            else
                parent.right = current.right;
        } else {
            TreeNode successor = getSuccessor(current);
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
            successor.left = current.left;
        }
        return true;
    }

    private TreeNode getSuccessor(TreeNode node) {
        TreeNode parent = node;
        TreeNode s = node;
        TreeNode curr = node.right;
        while (curr != null) {
            parent = s;
            s = curr;
            curr = curr.left;
        }

        if (s != node.right) {
            parent.left = s.right;
            s.right = node.right;
        }
        return s;
    }

    private int level(TreeNode current, int n) {
        if (current != null) {
            int one = level(current.left, ++n);
            int two = level(current.right, n);
            return Math.max(one, two);
        } else return --n;
    }

    public int level() {
        return level(root, 1);
    }

    public int isBalanced(TreeNode current, int n, Checker checker) {
        if (current != null) {
            int one = isBalanced(current.left, ++n, checker);
            int two = isBalanced(current.right, n, checker);
            if (Math.abs(one - two) >= 2) {
                checker.check = false;
            }
            return Math.max(one, two);
        } else return --n;
    }

    public boolean isBalanced() {
        Checker checker = new Checker();
        isBalanced(root, 1, checker);
        return checker.check;
    }

    private static class Checker {
        public boolean check;

        public Checker() {
            check = true;
        }
    }
}