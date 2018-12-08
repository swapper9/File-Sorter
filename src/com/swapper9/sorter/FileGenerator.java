package com.swapper9.sorter;

import java.io.FileWriter;
import java.io.IOException;

public class FileGenerator {

    public static void main(String[] args) {
        String file = "D:/tmp/tmprandom.dat";
        try(FileWriter fw = new FileWriter(file)) {
            for (int i = 0; i < 10_000_000; i++) {
                String str = String.valueOf((int)(Math.random()*10000000));
                fw.write(str + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
