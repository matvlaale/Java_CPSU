import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        String[] mas = {"mmm", "nnn"};
        ArrayList<String> al = new ArrayList<>();
        System.out.println(Arrays.toString(mas));
        chgEl(mas, 0, 1);
        System.out.println(Arrays.toString(mas));
        al = arrInList(mas);
        System.out.println(al.toString());

        Apple apple = new Apple();
        Orange orange = new Orange();
        Box<Apple> apples1 = new Box<>(apple);
        Box<Apple> apples2 = new Box<>(apple);
        Box<Orange> oranges = new Box<>(orange);
        for (int i = 0; i < 5; i++) {
            apples1.add(apple);
            oranges.add(orange);
        }
        System.out.println("Яблоки1 = " + apples1.getWeight() + " Апельсины = " + oranges.getWeight() + " Равны? " + apples1.compare(oranges));
        apples1.putInto(apples2);
        System.out.println("Яблоки2 = " + apples2.getWeight() + " Равны яблоки? " + apples1.compare(apples2));
    }
    public static <T> T[] chgEl(T[] mas, int i, int j){
        T zap = mas[i];
        mas[i] = mas[j];
        mas[j] = zap;
        return mas;
    }

    public static <T> ArrayList<T> arrInList(T[] mas) {
        ArrayList<T> al = new ArrayList<>();
        Collections.addAll(al, mas);
        return al;
    }
}

/*<? extends Fruit>
public static <T> void copy(List<? super T> dest, List<? extends T> src)
class MasManip<T> {
    public T[] chgEl(T[] mas, int i, int j) {
        T zap = mas[i];
        mas[i] = mas[j];
        mas[j] = zap;
        return mas;
    }
    public ArrayList<T> arrInList(T[] mas) {
        ArrayList<T> al = new ArrayList<>();
        Collections.addAll(al, mas);
        return al;
    }
}*/

class Fruit { }
class Apple extends Fruit { }
class Orange extends Fruit { }

class Box<T extends Fruit> implements Comparable<Box> {
    private ArrayList<T> boxList;
    private int kol;
    private float weight;

    Box(T fruit) {
        kol = 0;
        weight = (fruit instanceof Apple) ? 1.0f : 1.5f;
        boxList = new ArrayList<>();
    }

    @Override
    public int compareTo(Box other){
        return 1;
    }

    public boolean compare(Box other) {
        return (Math.abs(getWeight() - other.getWeight()) < 0.001);
    }

    public void putInto(Box<T> other) {
        while (kol > 0) {
            T fruit = remove();
            other.add(fruit);
        }
    }

    public float getWeight() {
        return weight * kol;
    }

    public void add(T fruit) {
        boxList.add(fruit);
        kol++;
    }

    public T remove() {
        return boxList.remove(--kol);
    }
}