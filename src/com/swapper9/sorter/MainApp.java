package com.swapper9.sorter;

import java.io.*;
import java.util.PriorityQueue;

public class MainApp {

    private static int arraySize = 100_000;
    private static int[] unsorted = new int[arraySize];
    private static int[][] chunkArray = new int[arraySize/100][arraySize];

    public static void main(String[] args) {

        System.out.println("Reading source unsorted file");
        try(BufferedReader br = new BufferedReader(new FileReader("D:/tmp/tmprandom.dat"))) {
            for (int i = 0; i < 1000; i++) {
                unsorted = null;
                unsorted = new int[arraySize];
                for (int j = 0; j < arraySize; j++) {
                    unsorted[j] = Integer.valueOf(br.readLine());
                }
                System.out.println("Sorting chunk " + i);
                MultiThreadMerger multiThreadMerger = new MultiThreadMerger(unsorted);
                multiThreadMerger.start();
                try {
                    multiThreadMerger.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                chunkArray[i] = multiThreadMerger.getSorted();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] result = mergeKSortedArray(chunkArray);
        System.out.println("Saving result file");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("D:/tmp/sorted_result.dat"))){
            for (int i = 0; i < result.length; i++) {
                bw.write(String.valueOf(result[i])+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] mergeKSortedArray(int[][] arr) {
        PriorityQueue<ArrayContainer> queue = new PriorityQueue<>();
        int total = 0;

        for (int i = 0; i < arr.length; i++) {
            queue.add(new ArrayContainer(arr[i], 0));
            total = total + arr[i].length;
        }
        int m = 0;
        int result[] = new int[total];

        while(!queue.isEmpty()){
            ArrayContainer ac = queue.poll();
            result[m++] = ac.arr[ac.index];
            if(ac.index < ac.arr.length-1){
                queue.add(new ArrayContainer(ac.arr, ac.index+1));
            }
        }
        return result;
    }
}
