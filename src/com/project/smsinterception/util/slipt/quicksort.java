package com.project.smsinterception.util.slipt;


public class quicksort {
	public static void quickSort(int a[], int start, int end , char c[])
	{        int i,j;
	         i = start;
	         j = end;
	         if((a==null)||(a.length==0))
	             return;
	         while(i<j){
	             while(i<j&&a[i]>=a[j]){     //以数组start下标的数据为key，右侧扫描
	                 j--;
	             }
	             if(i<j){                   //右侧扫描，找出第一个比key小的，交换位置
	                 int temp = a[i];
	                 a[i] = a[j];
	                 a[j] = temp;
	                 char ctemp = c[i];
	                 c[i] = c[j];
	                 c[j] = ctemp;
	             }
	              while(i<j&&a[i]>a[j]){    //左侧扫描（此时a[j]中存储着key值）
	                 i++;
	               }
	             if(i<j){                 //找出第一个比key大的，交换位置
	                 int temp = a[i];
	                 a[i] = a[j];
	                 a[j] = temp;
	                 char ctemp = c[i];
	                 c[i] = c[j];
	                 c[j] = ctemp;
	             }
	        }
	        if(i-start>1){
	             //递归调用，把key前面的完成排序
	            quickSort(a,start,i-1 ,c);
	        }
	        if(end-i>1){
	            quickSort(a,i+1,end ,c);    //递归调用，把key后面的完成排序
	        }
	}
}
