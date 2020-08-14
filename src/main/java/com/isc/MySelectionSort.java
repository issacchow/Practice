package com.isc;

public class MySelectionSort {

    public int[] selectionSort(int[] arr) {
        System.out.println("array length:" + arr.length);
        int temp;
        for (int i = 0; i < arr.length; i++) {
            System.out.println("for :" + i);
            int indexOfMin  = indexOfMinValue(arr,i);

            if(i==indexOfMin) {
                //位置一样表示，当前值是最小值
                continue;
            }else{
                temp = arr[i];
                arr[i] = arr[indexOfMin];
                arr[indexOfMin] = temp;
            }
        }

        return arr;
    }

    public int[] selectionSort2(int[] arr) {
        System.out.println("array length:" + arr.length);
        int temp;
        for (int i = 0; i < arr.length; i++) {
            System.out.println("for :" + i);
            int min = arr[i];

            if(i==arr.length-1)
                break;

            for(int j=i+1;j<arr.length;j++){
                if(arr[j]<min){
                    swap(arr,i,j);
                }
            }
        }

        return arr;
    }

    private void swap(int[] arr,int a,int b){
       int t = arr[a];
       arr[a] = arr[b];
       arr[b] = t;
    }


    /**
     * 返回最小值的下标
     * @param arr
     * @return
     */
    public int indexOfMinValue(int[] arr,int start) {

        System.out.println("find min at position:" + start);

        if(start+1==arr.length)
            return 0;

        int min = arr[start];
        int index = -1;
        for (int i = start+1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
                index = i;
            }
        }

        System.out.println("min value:" + min);
        System.out.println("min index:" + index);
        return index;
    }
}
