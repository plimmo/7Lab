package org.example.task7_3;

import java.io.BufferedReader;
import java.io.FileReader;

public class Lcs {

    public static void main(String[] args) {
        String inputFile = "lcs-input.txt";

        String X, Y;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            X = br.readLine().trim();
            Y = br.readLine().trim();
        } catch (Exception e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }

        int n = X.length();
        int m = Y.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        int result = dp[n][m];
        System.out.println(result);
    }
}
