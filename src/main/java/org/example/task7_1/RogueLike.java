package org.example.task7_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RogueLike {

    public static void main(String[] args) {
        String inputFile = "roguelike-input.csv";
        String outputFile = "roguelike-output.txt";

        try {
            int[][] grid = readGrid(inputFile);
            Result result = compute(grid);
            writeOutput(outputFile, result);
            System.out.println("Ответ в " + outputFile);

        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлами: " + e.getMessage());
        }
    }

    private static int[][] readGrid(String filename) throws IOException {
        List<int[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                int[] row = new int[parts.length];

                for (int i = 0; i < parts.length; i++) {
                    row[i] = Integer.parseInt(parts[i]);
                }
                rows.add(row);
            }
        }

        return rows.toArray(new int[0][]);
    }

    private static class Result {
        int maxCoins;
        String path;

        Result(int maxCoins, String path) {
            this.maxCoins = maxCoins;
            this.path = path;
        }
    }

    private static Result compute(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int[][] dp = new int[n][m];

        dp[0][0] = grid[0][0];

        for (int j = 1; j < m; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < n; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        StringBuilder path = new StringBuilder();
        int i = n - 1, j = m - 1;

        while (i > 0 || j > 0) {
            if (i == 0) {
                j--;
                path.append("R");
            } else if (j == 0) {
                i--;
                path.append("D");
            } else {
                if (dp[i - 1][j] > dp[i][j - 1]) {
                    i--;
                    path.append("D");
                } else {
                    j--;
                    path.append("R");
                }
            }
        }

        path.reverse();
        return new Result(dp[n - 1][m - 1], path.toString());
    }

    private static void writeOutput(String filename, Result result) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(String.valueOf(result.maxCoins));
            bw.newLine();
            bw.write(result.path);
        }
    }
}
