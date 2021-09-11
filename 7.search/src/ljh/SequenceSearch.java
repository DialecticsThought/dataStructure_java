package ljh;

import java.util.ArrayList;

public class SequenceSearch {
    public static void main(String[] args) {
        int arr[] = {1,9,11,-1,34,89,11};
        System.out.println(seqSearch(arr,11));
    }
    public static ArrayList<Integer> seqSearch(int[] arr,int value){
        //线性查找 就是逐一比对 发现有相同的值 就返回下标
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0;i<arr.length;i++){
            if(arr[i] == value){
                list.add(i);
            }
        }
        return list;
    }
}
