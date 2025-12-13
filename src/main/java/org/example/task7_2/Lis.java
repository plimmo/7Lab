package org.example.task7_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Lis {

    static final int INF = (int)1e9;

    public static void main(String[] args) {
        String inputFile = "lis-input.txt";
        String outputFile = "lis-output.txt";

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            int n = Integer.parseInt(br.readLine().trim());
            int[] a = new int[n];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(st.nextToken());
            }
            br.close();

            int[] d = new int[n + 1];
            int[] pos = new int[n + 1];
            int[] prev = new int[n];

            Arrays.fill(d, INF);
            d[0] = -INF;
            pos[0] = -1;

            int length = 0;

            for (int i = 0; i < n; i++) {
                int j = lowerBound(d, a[i]);
                if (d[j - 1] < a[i] && a[i] < d[j]) {
                    d[j] = a[i];
                    pos[j] = i;
                    prev[i] = pos[j - 1];
                    length = Math.max(length, j);
                }
            }

            int[] answer = new int[length];
            int p = pos[length];
            for (int i = length - 1; i >= 0; i--) {
                answer[i] = a[p];
                p = prev[p];
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
            bw.write(length + "\n");
            for (int i = 0; i < length; i++) {
                bw.write(answer[i] + " ");
            }
            bw.close();

            System.out.println("Ответ в " + outputFile);

        } catch (Exception e) {
            System.out.println("Ошибка при работе с файлами: " + e.getMessage());
        }
    }

    static int lowerBound(int[] d, int x) {
        int l = 0, r = d.length - 1;
        while (l < r) {
            int m = (l + r) / 2;
            if (d[m] >= x) {
                r = m;
            }
            else l = m + 1;
        }
        return l;
    }
}
