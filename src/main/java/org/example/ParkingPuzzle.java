package org.example;

import java.util.*;

public class ParkingPuzzle {
    public static void solveParkingPuzzle(int[] start, int[] end) {
        Queue<int[]> queue = new LinkedList<>();
        Map<String, String> parentMap = new HashMap<>();
        Set<String> visited = new HashSet<>();

        String startStr = arrayToString(start);
        String endStr = arrayToString(end);

        queue.add(start);
        visited.add(startStr);
        parentMap.put(startStr, null);

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            String currentStr = arrayToString(current);

            if (currentStr.equals(endStr)) {
                printSolution(parentMap, startStr, endStr);
                return;
            }

            int zeroIndex = findZeroIndex(current);

            for (int i = 0; i < current.length; i++) {
                if (i != zeroIndex) {
                    int[] next = Arrays.copyOf(current, current.length);
                    next[zeroIndex] = next[i];
                    next[i] = 0;
                    String nextStr = arrayToString(next);

                    if (!visited.contains(nextStr)) {
                        queue.add(next);
                        visited.add(nextStr);
                        parentMap.put(nextStr, currentStr);
                    }
                }
            }
        }

        System.out.println("No solution found.");
    }

    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int num : array) {
            sb.append(num).append(" ");
        }
        return sb.toString().trim();
    }

    private static int findZeroIndex(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                return i;
            }
        }
        throw new IllegalArgumentException("No zero found in array.");
    }

    private static void printSolution(Map<String, String> parentMap, String startStr, String endStr) {
        List<String> path = new ArrayList<>();
        String current = endStr;
        while (current != null) {
            path.add(current);
            current = parentMap.get(current);
        }
        Collections.reverse(path);

        System.out.println(path.size() - 1);
        for (String state : path) {
            System.out.println(state);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Unesite početno stanje parkinga: ");
        int[] start = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        System.out.println("Unesite završno stanje parkinga: ");
        int[] end = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        System.out.println("Rješenje: ");
        solveParkingPuzzle(start, end);
    }
}
