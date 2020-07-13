import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Recursion {

    public static Random rand = new Random();

    public static void main(String[] args) {
        int kolvo = 4;
        Stack first = new Stack(kolvo);
        first.name = "first";
        Stack second = new Stack(kolvo);
        second.name = "second";
        Stack third = new Stack(kolvo);
        third.name = "third";
        for (int i = kolvo; i > 0; i--) {
            first.push((char) i);
        }

        hanoi(first, second, third, kolvo);

        for (int i = kolvo; i > 0; i--) {
            System.out.println((int) second.pop());
        }

        int n = 8;
        int[] xmas = new int[n];
        for (int i = 0; i < n; i++) {
            xmas[i] = -1;
        }

        queens(n, 0, xmas);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (xmas[i] == j) System.out.print("I ");
                else System.out.print("- ");
            }
            System.out.println();
        }
        System.out.println(Arrays.toString(xmas));
    }

    public static void hanoi(Stack from, Stack to, Stack add, int n) {
        if (n != 0) {
            hanoi(from, add, to, n - 1);
            System.out.println(n + "-й диск: " + from.name + " -> " + to.name);
            to.push(from.pop());
            hanoi(add, to, from, n - 1);
        }
    }

    public static boolean queens(int size, int n, int[] mas) {
        if (n < size) {
            boolean zap = false;
            for (int i = 0; i < size; i++) {
                if (mas[i] == -1) {
                    if (!insert(mas, i)) continue;
                    zap = queens(size, n + 1, mas);
                    if (zap) break;
                    else mas[i] = -1;
                }
            }
            return zap;
        } else return true;
    }

    public static boolean insert(int[] mas, int x) {
        int[][] zap = new int[mas.length][mas.length];
        for (int i = 0; i < mas.length; i++) {
            for (int j = 0; j < mas.length; j++) {
                zap[i][j] = -1;
            }
        }
        for (int i = 0; i < mas.length; i++) {
            for (int j = 0; j < mas.length; j++) {
                if (mas[i] == j) {
                    zap[i][j] = 5;
                    for (int one = 0; one < mas.length; one++) {
                        try {
                            zap[i + one][j] = 3;
                        } catch (Exception ignored) {
                        }
                        try {
                            zap[i][j + one] = 3;
                        } catch (Exception ignored) {
                        }
                        try {
                            zap[i - one][j] = 3;
                        } catch (Exception ignored) {
                        }
                        try {
                            zap[i][j - one] = 3;
                        } catch (Exception ignored) {
                        }
                        try {
                            zap[i + one][j + one] = 3;
                        } catch (Exception ignored) {
                        }
                        try {
                            zap[i + one][j - one] = 3;
                        } catch (Exception ignored) {
                        }
                        try {
                            zap[i - one][j + one] = 3;
                        } catch (Exception ignored) {
                        }
                        try {
                            zap[i - one][j - one] = 3;
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }
        /*for (int i = 0; i < mas.length; i++) {
            for (int j = 0; j < mas.length; j++) {
                System.out.print(zap[i][j]);
            }
            System.out.println();
        }*/
        for (int i = 0; i < mas.length; i++) {
            if (zap[x][i] == -1) {
                mas[x] = i;
                return true;
            }
        }
        return false;
    }

}