package com.reagan.ind.Zerus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* Список вершин-состояний/команд Цепи Маркова
0 - анализ, отсюда мы выходим на три разных состояния: рядом никого, рядом кто-то есть, окружены
1 - состояние "рядом никого"
10 - движение
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

    List<Edge> edgesList;
    int numBioSystem;

    public MarkovChain(int v1, int v2, double weight) {
        edgesList = new ArrayList<>();
        edgesList.add(new Edge(v1, v2, weight));
        numBioSystem = 0;
    }

    public void addEdge(int v1, int v2, double weight) {
        edgesList.add(new Edge(v1, v2, weight));
        if ((v1==10) || (v1 == 20)) {
            numBioSystem++;
        }
    }

    public void checkVertex(int v) { //Уравнивает инстинкты
        List<Edge> res = new ArrayList<>();
        for (Edge edge : edgesList) {
            if (edge.v1 == v) {
                res.add(edge);
            }
        }
        int n = res.size();
        for (Edge edge : res) {
            edgesList.set(edgesList.indexOf(edge), new Edge(edge.v1, edge.v2, 1 / n));
        }
    }

    public List<Edge> searchEdge(int v) {
        List<Edge> res = new ArrayList<>();
        for (Edge edge : edgesList) {
            if ((edge.v1 == v) || (edge.v2 == v)) {
                res.add(edge);
            }
        }
        return res;
    }

    public int branching(int v) { //Решает по какому пути пойти от данной ветки
        double q = Math.random();
        double sum = 0.0;
        for (Edge edge : edgesList) {
            if (edge.v1 == v) {
                sum += edge.weight;
                if (sum>=q) {
                    return edge.v2;
                }
            }
        }
        return -1;
    }

    public MarkovChain mutate() {
        //Добавление нового ребра по номеру команды или изменение инстинктов у уже существующего (состояния отсутствия соседей, наличия соседей и окружения)
        /*
        if (Math.random() < 0.5) {
            int rnd = new Random().nextInt(numBioSystem);
            System.out.println(rnd);
        }
        if (Math.random() < 0.25) {
            int rnd = new Random().nextInt(numBioSystem);
        }
        */
        return this;
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
