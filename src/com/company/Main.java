package com.company;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;
    float[] arr = new float[size];

    public static void main(String[] args) {
        Main test = new Main();
        test.oneThread(new float[size]);
        test.twoThread(new float[size]);


    }

    void oneThread(float arr[]) {

        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long start = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("Время работы первого метода: " + time+" ms");
    }

    void twoThread(float arr[]) {
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long start = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr, h, arr2, 0, h);
        long onTwo=System.currentTimeMillis();
        Thread thread1=new Thread(() -> {
            for (int i = 0; i < arr1.length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        long onTwo2=System.currentTimeMillis();
        Thread thread2=new Thread(() -> {
            for (int i = 0; i < arr2.length; i++) {
                arr[i] = (float) (arr[i + arr2.length] * Math.sin(0.2f + (i+h) / 5) * Math.cos(0.2f + (i+h) / 5) * Math.cos(0.4f + (i+h) / 2));
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        long onTwoEnd=System.currentTimeMillis();
        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);
        Long clue=System.currentTimeMillis();
        System.out.println("Время для разбивки массивов: "+(onTwo-start)+" ms");
        System.out.println("Время просчета первого массива arr1: "+(onTwo2-onTwo)+" ms");
        System.out.println("Время просчета второго массива arr2: "+(onTwoEnd-onTwo2)+" ms");
        System.out.println("Время склейки массивов: "+(clue-onTwoEnd)+" ms");
    }
}

