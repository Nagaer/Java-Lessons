package com.reagan.ind.EkzTasks.TaskFive;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskFive {
    private int[][] matrix;

    private TaskFive(int[][] matrix) {
        this.matrix = matrix;
    }

    private void printMatrix() {
        for (int[] row : matrix) {
            for (int column : row) {
                System.out.print(column + " ");
            }
            System.out.println();
        }
    }

    private int[][] generateRandomMatrix(int n, int m, int a, int b) {
        int[][] randMatrix = new int[n][m];
        for (int row=0; row<n; row++) {
            for (int column=0; column<m; column++) {
                randMatrix[row][column] = (int) (Math.random()*(b-a) + a);
            }
        }
        return randMatrix;
    }

    private int maxElement() {
        int max = matrix[0][0];
        for (int[] row : matrix) {
            for (int x : row) {
                if (x > max) {
                    max = x;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        TaskFive example = new TaskFive(null);
        new Thread(()->{
            example.matrix = example.generateRandomMatrix(3, 4, 0, 10);
            example.printMatrix();
            System.out.println(example.maxElement());
        }
        ).start();
    }
}
