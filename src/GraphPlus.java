public class GraphPlus {
    public static void main(String[] args) {
        Graph graph = new Graph();
        for (int i = 65; i < 75; i++) {
            graph.addVertex((char) i);
        }
        for (int i = 0; i < 9; i++) {
            graph.addEdge(i, i + 1);
        }
        graph.addEdge(0, 4);
        graph.addEdge(5, 7);
        graph.addEdge(3, 8);
        graph.minWay(0, 9);
        graph.widthTraverse();
    }
}

class Graph {
    private class Vertex {
        char label;
        boolean wasVisited;
        int interval = 50;

        public Vertex(char label) {
            this.label = label;
            this.wasVisited = false;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "label=" + label + " inter=" + interval +
                    '}';
        }
    }

    private final int MAX_VERTICES = 32;
    private Vertex[] vertexList;
    private int[][] adjMatrix;
    private int size;

    public Graph() {
        vertexList = new Vertex[MAX_VERTICES];
        adjMatrix = new int[MAX_VERTICES][MAX_VERTICES];
        size = 0;
    }

    public void addVertex(char label) {
        vertexList[size++] = new Vertex(label);
    }

    public void addEdge(int start, int end) {
        adjMatrix[start][end] = 1;
        adjMatrix[end][start] = 1;
    }

    public void displayVertex(int vertex) {
        System.out.println(vertexList[vertex]);
    }

    private int getUnvisitedVertex(int ver) {
        for (int i = 0; i < size; i++) {
            if (adjMatrix[ver][i] == 1 && !vertexList[i].wasVisited)
                return i;
        }
        return -1;
    }

    public void depthTraverse() {
        Stack stack = new Stack(MAX_VERTICES);
        vertexList[0].wasVisited = true;
        displayVertex(0);
        stack.push((char) 0);
        while (!stack.isEmpty()) {
            int v = getUnvisitedVertex(stack.peek());
            if (v == -1) {
                stack.pop();
            } else {
                vertexList[v].wasVisited = true;
                displayVertex(v);
                stack.push((char) v);
            }
        }
        resetFlags();
    }

    public void widthTraverse() {
        Queue queue = new Queue(MAX_VERTICES);
        vertexList[0].wasVisited = true;
        displayVertex(0);
        queue.insert(0);
        while (!queue.isEmpty()) {
            int vCurrent = queue.remove();
            displayVertex(vCurrent);
            int vNext;
            while ((vNext = getUnvisitedVertex(vCurrent)) != -1) {
                vertexList[vNext].wasVisited = true;
                queue.insert(vNext);
            }
        }
        resetFlags();
    }

    public void minWay(int vertex, int end) {
        int n = 0;
        Queue queue = new Queue(MAX_VERTICES);
        vertexList[vertex].wasVisited = true;
        vertexList[vertex].interval = n;
        queue.insert(vertex);
        while (!queue.isEmpty()) {
            int vCurrent = queue.remove();
            int vNext;
            n++;
            while ((vNext = getUnvisitedVertex(vCurrent)) != -1) {
                vertexList[vNext].wasVisited = true;
                if (vertexList[vNext].interval > n) vertexList[vNext].interval = vertexList[vCurrent].interval + 1;
                queue.insert(vNext);
            }
        }
        n = vertexList[end].interval;
        char[] mas = new char[n + 1];
        resetFlags();

        mas[0] = vertexList[end].label;
        int vCurrent = end;
        int vNext;
        for (int i = 1; i < mas.length - 1; i++) {
            while ((vNext = getUnvisitedVertex(vCurrent)) != -1) {
                vertexList[vNext].wasVisited = true;
                if (vertexList[vNext].interval == n - 1) {
                    mas[i] = vertexList[vNext].label;
                    vCurrent = vNext;
                    n--;
                    break;
                }
            }
        }
        mas[mas.length - 1] = vertexList[vertex].label;
        resetFlags();
        reverse(mas);
        for (int i = 0; i < mas.length - 1; i++) {
            System.out.print(mas[i] + "->");
        }
        System.out.println(mas[mas.length - 1]);
    }

    private char[] reverse(char[] mas) {
        for (int i = 0; i < mas.length / 2; i++) {
            char zap = mas[i];
            mas[i] = mas[mas.length - 1 - i];
            mas[mas.length - 1 - i] = zap;
        }
        return mas;
    }

    private void resetFlags() {
        for (int i = 0; i < size; i++) {
            vertexList[i].wasVisited = false;
        }
    }
}