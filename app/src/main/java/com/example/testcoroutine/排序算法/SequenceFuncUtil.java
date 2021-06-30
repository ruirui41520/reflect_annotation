package com.example.testcoroutine.排序算法;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SequenceFuncUtil {

    private static void printSortArray(int[] p){
        for (int i = 0;i < p.length;i++){
            Log.e("****printf index: "+ i," current value:" + p[i]);
        }
    }

    /*插入排序：它的基本思想是将一个记录插入到已经排好序的有序表中，从而一个新的、记录数增 1 的有序表：在其实现过程使用双层循环，外层循环对除了第一个元素之外的所有元素，内层循环对当前元素前面有序表进行待插入位置查找，并进行移动*/
    public static void insertSort(){
        int[] p = {30,28,76,3,90,45};
        printSortArray(p);
        int i,j,temp;
        for (i = 1;i < p.length;i++){
            temp = p[i];
            for(j = i;j >=1 && p[j-1] > temp;j--){
                p[j] = p[j-1];
            }
            p[j] = temp;
        }
        printSortArray(p);
    }

    /*希尔排序(Shell Sort)是插入排序的一种，它是针对直接插入排序算法的改进。它通过比较相距一定间隔的元素来进行，各趟比较所用的距离随着算法的进行而减小，直到只比较相邻元素的最后一趟排序为止。*/
    public static void insertShellSort(){
        int[] p = {30,28,76,3,45};
        printSortArray(p);
        int j;
        for(int gap = p.length / 2; gap > 0; gap /= 2){
            for (int i = gap;i < p.length;i++){
                int temp = p[i];
                for (j = i; j >= gap && temp < p[j-gap]; j -= gap){
                    p[j] = p[j-gap];
                }
                p[j] = temp;
            }
        }
        printSortArray(p);
    }

/*选择排序：首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置。再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。重复第二步，直到所有元素均排序完毕。*/
    public static void selectSort(){
        int[] p = {30,28,76,3,45};
        printSortArray(p);
        int minIndex, temp;
        for(int i = 0; i < p.length - 1; i++){
            minIndex = i;
            for (int j = i+1; j < p.length; j++){
                if (p[j] < p[minIndex]){
                    minIndex = j;
                }
            }
            temp = p[i];
            p[i] = p[minIndex];
            p[minIndex] = temp;
        }
        printSortArray(p);
    }

    //冒泡排序（Bubble Sort）也是一种简单直观的排序算法。它重复地走访过要排序的数列，一次比较两个元素，如果他们的顺序错误就把他们交换过来。走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。这个算法的名字由来是因为越小的元素会经由交换慢慢"浮"到数列的顶端。
    public static void bubbleSort(){
        int[] p = {30,28,76,3,45};
        for (int i = 0; i < p.length-1; i++){
            for (int j = 0; j < p.length-1-i; j++){
                if (p[j] > p[j + 1]){
                    int temp = p[j];
                    p[j] = p[j+1];
                    p[j+1] = temp;
                }
            }
        }
        printSortArray(p);
    }

    //计数排序的核心在于将输入的数据值转化为键存储在额外开辟的数组空间中。作为一种线性时间复杂度的排序，计数排序要求输入的数据必须是有确定范围的整数。
    public static void countingSort(){
        int[] p = {30,28,76,3,45};
        int maxValue = p[0];
        for(int value: p){
            if (maxValue < value){
                maxValue = value;
            }
        }
        int bucketLen = maxValue + 1;
        int[] bucket = new int[bucketLen];
        for (int value : p){
            bucket[value]++;
        }
        int sortedIndex = 0;
        for (int j=0; j < bucketLen; j++){
            while (bucket[j] > 0){
                p[sortedIndex++] = j;
                bucket[j]--;
            }
        }
        printSortArray(p);
    }

    //快速排序：先从数列中取出一个数作为基准数，分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边，再对左右区间重复第二步，直到各区间只有一个数。
    private static void quickSort(int[] p, int left, int right){
        if (left < right){
            int i = left,j = right,x = p[left];
            while (i < j){
                while (i<j && p[j] > x){
                    j--;
                }
                if (i < j){
                    p[i++] = p[j];// equals : p[i] = p[j]; i++;
                }
                while (i<j && p[i]<x){
                    i++;
                }
                if (i<j){
                    p[j--] = p[i];
                }
            }
            p[i] = x;
            quickSort(p,left,i-1);
            quickSort(p,i+1,right);
        }
    }

    public static void quickSort(){
        int[] p = {30,28,76,3,45};
        quickSort(p,0,p.length - 1);
        printSortArray(p);
    }


}
