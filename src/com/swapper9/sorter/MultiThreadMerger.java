package com.swapper9.sorter;

public class MultiThreadMerger extends Thread {

    private int[] unsorted, sorted;
    private static final int MAX_THREADS = 8;

    public MultiThreadMerger(int[] unsorted) {
        this.unsorted = unsorted;
    }

    public void run() {
        int middle;
        int[] left, right;

        if ( unsorted.length <= 1 ) {
            sorted = unsorted;
        } else {
            middle = unsorted.length / 2;

            left = new int[middle];
            right = new int[unsorted.length - middle];
            System.arraycopy( unsorted, 0, left, 0, middle );
            System.arraycopy( unsorted, middle, right, 0, unsorted.length - middle );

            if ( activeCount() < MAX_THREADS ) {
                MultiThreadMerger leftSort = new MultiThreadMerger( left );
                MultiThreadMerger rightSort = new MultiThreadMerger( right );
                leftSort.start();
                rightSort.start();

                try {
                    leftSort.join();
                    rightSort.join();
                    sorted = Merger.merge( leftSort.getSorted(), rightSort.getSorted() );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                Merger leftSort = new Merger( left );
                Merger rightSort = new Merger( right );
                leftSort.sort();
                rightSort.sort();
                sorted = Merger.merge( leftSort.getSorted(), rightSort.getSorted() );
            }
        }
    }

    public int[] getSorted() {
        return sorted;
    }
}