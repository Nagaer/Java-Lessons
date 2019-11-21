package com.reagan.ind.Zerus;

import java.util.ArrayList;
import java.util.List;

/* ПРОБЛЕМА С ПЕРЕХОДОМ, ВИДИМО ИЗ-ЗА ИНСТИНКТОВ
Список вершин-состояний/команд Цепи Маркова
0 - анализ, отсюда мы выходим на три разных состояния: рядом никого, рядом кто-то есть, окружены
1 - состояние "рядом никого"
10 - движение
11-18 - движение в конкретную сторону
20 - фотосинтез
 */

public class MarkovChain {
    static class Edge {
        int v1, v2;
        Double weight;
        Edge(int v1, int v2, double weight) {
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }
    }

    final int totalNumSystem = 2;
    List<Edge> edgesList;
    int numBioSystem;

    public MarkovChain(int v1, int v2, double weight) {
        edgesList = new ArrayList<>();
        edgesList.add(new Edge(v1, v2, weight));
        numBioSystem = 0;
    }

    public void addEdge(int v1, int v2, double weight) {
        edgesList.add(new Edge(v1, v2, weight));
        if (v1==1) {
            numBioSystem++;
        }
    }

    public void addDoubleEdge(int v1, int v2, double weight) {
        addEdge(v1, v2, weight);
        addEdge(v2, v1, weight);
    }

    public void printChain() {
        for (Edge edge : edgesList) {
            System.out.print(edge.v1);
            System.out.print("-");
            System.out.print(edge.v2);
            System.out.print("=");
            System.out.println(edge.weight);
        }
    }

    public void checkVertex(int v) { //Уравнивает инстинкты
        List<Edge> res = new ArrayList<>();
        int n = 0;
        for (Edge edge : edgesList) {
            if (edge.v1 == v) {
                res.add(edge);
                n++;
            }
        }
        for (Edge edge : res) {
            edgesList.set(edgesList.indexOf(edge), new Edge(edge.v1, edge.v2, 1.0/n));
            System.out.println(1/n);
        }
    }

    public int branching(int v) { //Решает по какому пути пойти от данной ветки
        /*
        for (Edge edge : edgesList) {
            if (edge.v2==20) {
                System.out.println("У нас всё ещё есть фотосинтез!");
            }
        }
        */
        double q = Math.random();
        double sum = 0.0;
        for (Edge edge : edgesList) {
            if (edge.v1 == v) {
                sum += edge.weight;
                if (sum>=q) {
                    System.out.println(edge.v2);
                    return edge.v2;
                }
            }
        }
        System.out.println(-1);
        return -1;
    }

    public void mutate() {
        //Добавление нового ребра по номеру команды или изменение инстинктов у уже существующего (состояния отсутствия соседей, наличия соседей и окружения)

        if (Math.random() < 0.5) { //Изменение инстинктов у случайной команды
            int numSystem = (int) (Math.random()*numBioSystem); //Адам решил, что эта система важнее
            int change = 1;
            for (Edge edge : edgesList) {
                if (edge.v1 == 10) {
                    double q = Math.random();
                    if (q < 0.5) {
                        change=10;
                    }
                    break;
                }
            }
            List<Integer> listSystem = new ArrayList<>();
            for (Edge edge : edgesList) {
                if (edge.v1 == change) { //будет несколько состояний - это тоже выбирать рандомом
                    listSystem.add(edge.v2);
                }
            }
            int codeSystem = listSystem.get(numSystem);
            for (Edge edge : edgesList) {
                if ((edge.v2 == codeSystem) && (edge.v1 == change)) {
                    edge.weight += (numBioSystem - 1)/(Math.pow(numBioSystem, 2));
                } else if ((listSystem.contains(edge.v2)) && (edge.v1 == change)) {
                    edge.weight *= (numBioSystem - 1)/numBioSystem;
                }
            }
            //Если существо чем-то не пользуется с шансом меньше Eps - удаляем это
        }
        if (Math.random() < 0.25) { //Появление новой команды
            if (totalNumSystem - numBioSystem != 0) { //Если больше нечему появляться, выходим
                int rnd = (int) (Math.random() * (totalNumSystem - numBioSystem));
                List<Integer> listSystem = new ArrayList<>();
                for (int i = 0; i<totalNumSystem; ++i)
                    listSystem.add((i + 1)*10);
                for (Edge edge : edgesList) {
                    if (edge.v1 == 1) {
                        listSystem.remove(Integer.valueOf(edge.v2));
                    }
                }
                int numSystem = listSystem.get(rnd);
                addEdge(1, numSystem, 1.0);
                addEdge(numSystem, 1, 1.0);
                checkVertex(1);
                if (numSystem == 10) { //Много рёбер для движения
                    addEdge(10, 11, 0.125);
                    addEdge(11, 1, 1.0);
                    addEdge(10, 12, 0.125);
                    addEdge(12, 1, 1.0);
                    addEdge(10, 13, 0.125);
                    addEdge(13, 1, 1.0);
                    addEdge(10, 14, 0.125);
                    addEdge(14, 1, 1.0);
                    addEdge(10, 15, 0.125);
                    addEdge(15, 1, 1.0);
                    addEdge(10, 16, 0.125);
                    addEdge(16, 1, 1.0);
                    addEdge(10, 17, 0.125);
                    addEdge(17, 1, 1.0);
                    addEdge(10, 18, 0.125);
                    addEdge(18, 1, 1.0);
                }
            } else {
                System.out.println("Мы добавили все системы!");
            }
        }
    }
    /*
    public static void main(String[] args) {
        MarkovChain mark = new MarkovChain(0, 1, 1.0);
        mark.addEdge(1, 2, 1.0);
        mark.addEdge(2, -1, 1.0);
        int cur = 0;
        while (cur != -1) {
            cur = mark.branching(cur);
        }
    }
    */
}
