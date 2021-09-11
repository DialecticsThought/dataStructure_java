import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class BubbleSort {
    public static void main(String[] args) {
        //int arr[] = {3,9,-1,10,-2};
        //bubbleSort(arr);
        //测试一下冒泡排序的速度O(n^2) 给80000个数据 测试
        int[] arr_random = new int[80000];
        for(int i = 0; i< 80000; i++){
            //生成一个[0,8000000)的随机数
            arr_random[i] = (int)(Math.random()*8000000);
        }
        Date date1 = new Date();
        //格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr1 = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是:"+dateStr1);
        //开始随机数的冒泡排序
        bubbleSort(arr_random);

        Date date2 = new Date();
        String dateStr2 = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是:"+dateStr2);


/*        //每一次排序都会找到最大的数/最小的数
        int arr[] = {3,9,-1,10,-2};
        int temp = 0;//交换的时候用
        boolean flag =false;//表示变量 表示 是否进行过交换
        //为了容易理解 我们把冒泡排序的演变过程
        //第一趟排序 将最大的数排在最后

        *//*
        (1) 一共进行 数组的大小-1 次 大的循环
        (2)每一趟排序的次数在逐渐的减少
        (3) 如果我们发现在某趟排序中，没有发生一次交换， 可以提前结束冒泡排序。这个就是优化
        * 时间复杂度O(n^2)因为两个for循环
        * *//*
        //外层的for循环 简化下面3个for循环
        for(int i =0;i < arr.length -1; i++){

            for(int j = 0;j <arr.length - 1 - i;j++){
                //从第一个数开始 如果前一个数>后一个数 交换
                if(arr[j] > arr[j+1]){
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
            System.out.println("第"+(i+1)+"趟排序后的数组");
            System.out.println(Arrays.toString(arr));
            if(!flag){//一次排序中 一次交换都没有的话 提前结束冒泡排序
                break;
            }else {
                flag = false;//重置flag 不妨碍下一次循环的判断
            }
        }*/

/*        //冒泡排序的特点 第二趟排序 就是将第二大的数排在倒数第二位
        for(int j = 0;j <arr.length - 1 - 1;j++){
            //如果前一个数>后一个数 交换
            if(arr[j] > arr[j+1]){
                temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;
            }
        }
        System.out.println("第二趟排序后的数组");
        System.out.println(Arrays.toString(arr));


        //冒泡排序的特点 第二趟排序 就是将第二大的数排在倒数第二位
        for(int j = 0;j <arr.length - 1 - 2;j++){
            //如果前一个数>后一个数 交换
            if(arr[j] > arr[j+1]){
                temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;
            }
        }
        System.out.println("第三趟排序后的数组");
        System.out.println(Arrays.toString(arr));

        //冒泡排序的特点 第二趟排序 就是将第二大的数排在倒数第二位
        for(int j = 0;j <arr.length - 1 - 3;j++){
            //如果前一个数>后一个数 交换
            if(arr[j] > arr[j+1]){
                temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;
            }
        }
        System.out.println("第四趟排序后的数组");
        System.out.println(Arrays.toString(arr));*/


    }
    public static void bubbleSort(int[] arr){
        int temp = 0;//交换的时候用
        boolean flag =false;//表示变量 表示 是否进行过交换
        //为了容易理解 我们把冒泡排序的演变过程
        //第一趟排序 将最大的数排在最后

        /*
        (1) 一共进行 数组的大小-1 次 大的循环
        (2)每一趟排序的次数在逐渐的减少
        (3) 如果我们发现在某趟排序中，没有发生一次交换， 可以提前结束冒泡排序。这个就是优化
        * 时间复杂度O(n^2)因为两个for循环
        * */
        //外层的for循环 简化下面3个for循环
        for(int i =0;i < arr.length -1; i++){

            for(int j = 0;j <arr.length - 1 - i;j++){
                //从第一个数开始 如果前一个数>后一个数 交换
                if(arr[j] > arr[j+1]){
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
            //System.out.println("第"+(i+1)+"趟排序后的数组");
            //System.out.println(Arrays.toString(arr));
            if(!flag){//一次排序中 一次交换都没有的话 提前结束冒泡排序
                break;
            }else {
                flag = false;//重置flag 不妨碍下一次循环的判断
            }
        }
    }
}
