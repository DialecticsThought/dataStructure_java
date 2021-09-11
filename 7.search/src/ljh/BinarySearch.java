package ljh;

import java.util.ArrayList;

public class BinarySearch {
    //二分查找必须是有序的
    public static void main(String[] args) {
        int arr[] = {1, 8, 10, 89, 100, 100, 100, 1234};
        int arr2[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        int index = binarySearch(arr, 0, arr.length - 1, 1234);
        System.out.println(index);
        ArrayList<Integer> integers = binarySearch2(arr, 0, arr.length - 1, 100);
        System.out.println(integers);
    }

    /*
    int[] arr被查找的数组
    int left 左边的索引
    int right 右边的索引
    int findVal 要找的数字
    * */
    public static int binarySearch(int[] arr, int left, int right, int findVal) {
        //当left > right 说明递归整个数组 但是没有找到
        if (left > right) {
            return -1;//退出
        }
        int mid = (left + right) / 2;
        //int mid = left +(right-left)*(findVal -arr[left])/(arr[right]-arr[left]);
        int midVal = arr[mid];
        if (findVal > arr[mid]) {//向右递归
            //此时left=mid+1
            return binarySearch(arr, mid + 1, right, findVal);
        } else if (findVal < arr[mid]) {//向左递归
            return binarySearch(arr, left, mid - 1, findVal);
        } else {//findVal = mid说明找到
            return mid;
        }
    }

    /*
     **课后思考题： {1,8, 10, 89, 1000, 1000，1234} 当一个有序数组中，
     *  有多个相同的数值时，如何将所有的数值都查找到，比如这里的 1000
     *  思路分析
     * 1. 在找到 mid 索引值，不要马上返回
     * 2. 向 mid 索引值的左边扫描，将所有满足 1000， 的元素的下标，加入到集合 ArrayList
     * 3. 向 mid 索引值的右边扫描，将所有满足 1000， 的元素的下标，加入到集合 ArrayList
     *
     * */
    public static ArrayList<Integer> binarySearch2(int[] arr, int left, int right, int findVal) {
        System.out.println("hello~~");
        //当left > right 说明递归整个数组 但是没有找到
        if (left > right) {
            return new ArrayList<Integer>();//退出
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        if (findVal > arr[mid]) {//向右递归
            //此时left=mid+1
            return binarySearch2(arr, mid + 1, right, findVal);
        } else if (findVal < arr[mid]) {//向左递归
            return binarySearch2(arr, left, mid - 1, findVal);
        } else {//findVal = mid说明找到
            /*
             * 1. 在找到 mid 索引值，不要马上返回
             * 2. 向 mid 索引值的左边扫描，将所有满足 1000， 的元素的下标，加入到集合 ArrayList
             * 3. 向 mid 索引值的右边扫描，将所有满足 1000， 的元素的下标，加入到集合 ArrayList
             * */
            ArrayList<Integer> integers = new ArrayList<>();
            //向 mid 索引值的左边扫描，将所有满足 1000， 的元素的下标，加入到集合 ArrayList
            int temp = mid - 1;//向左遍历的话 temp就是right 把找到的值的左半边再扫描一遍
            while (true) {
                //temp < 0说明遍历结束 扫描到了最左边
                //想找到的值和当前的值 不相同 说明左边已经不可能有相同的值了
                if (temp < 0 || arr[temp] != findVal) {
                    break;
                }
                //否则 把temp放入到集合里面
                integers.add(temp);
                //temp左移
                temp = temp - 1;
            }
            integers.add(mid);
            //向 mid 索引值的左边扫描，将所有满足 1000， 的元素的下标，加入到集合 ArrayList
            temp = mid + 1;//相当于left 把找到的值的右半边再扫描一遍
            while (true) {
                //temp >arr.length-1 说明遍历结束 扫描到了整个数组的最右边
                if (temp > arr.length - 1 || arr[temp] != findVal) {
                    break;
                }
                //否则 把temp放入到集合里面
                integers.add(temp);
                //temp左移
                temp = temp + 1;
            }
            return integers;
        }
    }
}
