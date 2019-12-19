package com.reagan.ind.EkzTasks.TaskOne;

import java.util.List;
import java.util.Scanner;

public class TaskOne {
    class Vector {
        int x, y, z;

        public Vector(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void print() {
            System.out.println("{" + String.valueOf(x) + "; " + String.valueOf(y) + "; " + String.valueOf(z) + "}");
        }

    }

    List<Vector> vectorList;

    public void addVector() {
        System.out.print("Введите координаты: ");
        Scanner scanner = new Scanner(System.in);
        int new_x = scanner.nextInt();
        int new_y = scanner.nextInt();
        int new_z = scanner.nextInt();
        vectorList.add(new Vector(new_x, new_y, new_z));
    }

    public void printAll() {
        for (Vector vector:vectorList) {
            vector.print();
        }
    }

    public Vector summaryVectors(Vector v1, Vector v2) {
        return new Vector(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }

    public Vector subtractionVectors(Vector v1, Vector v2) {
        return new Vector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }

    public double scalarVectors(Vector v1, Vector v2) {
        return (v1.x * v2.x + v1.y * v2.y + v1.z * v2.z);
    }

    public double lengthVector(Vector v1) {
        return Math.sqrt(Math.pow(v1.x, 2) + Math.pow(v1.y, 2) + Math.pow(v1.z, 2));
    }

    public double cosinusVectors(Vector v1, Vector v2) {
        return (scalarVectors(v1, v2) / (lengthVector(v1) * lengthVector(v2)));
    }

    public static void main(String[] args) {
        TaskOne taskOne = new TaskOne();
        Scanner scan = new Scanner(System.in);
        int x = 0;
        String s = "";

        while (!"0".equals(s)) {
            System.out.println("0. Выход");
            System.out.println("1. Вывести все вектора");
            System.out.println("2. Ввести новый вектор");
            System.out.println("3. Сложить два вектора");
            System.out.println("4. Вычесть два вектора");
            System.out.println("5. Скаляр двух векторов");
            System.out.println("6. Длина вектора");
            System.out.println("7. Cos угла между двумя векторами");
            s = scan.next();

            try {
                x = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод");
            }

            switch (x) {
                case 1:
                    if (taskOne.vectorList.size() == 0)
                        System.out.println("Список векторов пуст!");
                    else
                        taskOne.printAll();
                    break;
                case 2:
                    taskOne.addVector();
                case 3:
                    System.out.print("Выберите два номера векторов, которые вы хотите сложить: ");
                    Scanner sum_scanner = new Scanner(System.in);
                    int sum_id1 = sum_scanner.nextInt();
                    int sum_id2 = sum_scanner.nextInt();
                    taskOne.summaryVectors(taskOne.vectorList.get(sum_id1), taskOne.vectorList.get(sum_id2)).print();
                    break;
                case 4:
                    System.out.print("Выберите два номера векторов, которые вы хотите вычесть: ");
                    Scanner sub_scanner = new Scanner(System.in);
                    int sub_id1 = sub_scanner.nextInt();
                    int sub_id2 = sub_scanner.nextInt();
                    taskOne.subtractionVectors(taskOne.vectorList.get(sub_id1), taskOne.vectorList.get(sub_id2)).print();
                    break;
                case 5:
                    System.out.print("Выберите два номера векторов, скаляр которых вы хотите посчитать: ");
                    Scanner scalar_scanner = new Scanner(System.in);
                    int scalar_id1 = scalar_scanner.nextInt();
                    int scalar_id2 = scalar_scanner.nextInt();
                    System.out.println(taskOne.scalarVectors(taskOne.vectorList.get(scalar_id1), taskOne.vectorList.get(scalar_id2)));
                    break;
                case 6:
                    System.out.print("Выберите номер вектора, длину которого вы хотите посчитать: ");
                    Scanner length_scanner = new Scanner(System.in);
                    int length_id = length_scanner.nextInt();
                    System.out.println(taskOne.lengthVector(taskOne.vectorList.get(length_id)));
                    break;
                case 7:
                    System.out.print("Выберите два номера векторов, косинус угла между которыми вы хотите посчитать: ");
                    Scanner cos_scanner = new Scanner(System.in);
                    int cos_id1 = cos_scanner.nextInt();
                    int cos_id2 = cos_scanner.nextInt();
                    System.out.println(taskOne.cosinusVectors(taskOne.vectorList.get(cos_id1), taskOne.vectorList.get(cos_id2)));
                    break;
            }
        }
    }
}
