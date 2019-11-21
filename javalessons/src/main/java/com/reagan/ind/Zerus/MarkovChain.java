package com.reagan.ind.Zerus;

import java.util.ArrayList;
import java.util.List;

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

    public MarkovChain(int v1, int v2, double weight) {
        edgesList = new ArrayList<>();
        edgesList.add(new Edge(v1, v2, weight));
    }

    void addEdge(int v1, int v2, double weight) {
        edgesList.add(new Edge(v1, v2, weight));
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
