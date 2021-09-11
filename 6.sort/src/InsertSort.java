import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class InsertSort {
    public static void main(String[] args) {
        /*
        *插入排序（Insertion Sorting）的基本思想是：把 n 个待排序的元素看成为一个有序表和一个无序表，开始时有 序表中只包含一个元素，
        * 无序表中包含有 n-1 个元素，排序过程中每次从无序表中取出第一个元素，把它的排 序码依次与有序表元素的排序码进行比较，
        * 将它插入到有序表中的适当位置，使之成为新的有序表
        *
        * */
        int[] arr = {17, 3, 25, 14, 20, 9};
        int[] arr_random = new int[80000];
        for(int i = 0; i< 80000; i++){
            //生成一个[0,8000000)的随机数
            arr_random[i] = (int)(Math.random()*8000000);
        }
        Date date1 = new Date();
        //格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String dateStr1 = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是:"+dateStr1);
        //开始随机数的排序
        insertSort(arr_random);

        Date date2 = new Date();
        String dateStr2 = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是:"+dateStr2);
    }
    //插入排序
    public static void insertSort(int[] arr){

        for(int i = 1; i < arr.length;i++){
            int insertVal =arr[i];//定义待插入的数
            int insertIndex = i - 1;//给insertVal 找到插入的位置 也就是假定要给insertVal插入的位置
            // insertIndex >= 0 保证给insertVal找 插入位置 不越界
            //insertVal < arr[insertIndex] 待插入的数 还没有找到插入位置
            while (insertIndex >= 0 && insertVal < arr[insertIndex]){
                arr[insertIndex+1] = arr[insertIndex];
                insertIndex--;
            }
            //这里判断是否需要赋值
            if(insertIndex+1 == i){
                //当退出while循环时 说明插入的位置找到 insertIndex+1
                arr[insertIndex + 1] = insertVal;
            }
            //当退出while循环时 说明插入的位置找到 insertIndex+1
            //arr[insertIndex + 1] = insertVal;

            //System.out.println("第"+(i)+"轮插入");
            //System.out.println(Arrays.toString(arr));
        }

        //使用逐步推导的方法 便于理解
        /*
        * 原始数据: [17]  [3 25 14 20 9] 以17为基准
        * 第一轮:  [3 17]   [25 14 20 9]
        * 第二轮   [3 17 25]   [14 20 9]
        * 第三轮   [3 14 17 25]  [20 9]
        * */
/*        //第一轮 定义待插入的数
        int insertVal =arr[1];
        // 第一轮 即 arr[1]的前面的这个数的下标
        int insertIndex = 1-1;
        //给insertVal 找到插入的位置*/
        /*
        * insertIndex >= 0 保证给insertVal找 插入位置 不越界
        *insertVal < arr[insertIndex] 待插入的数 还没有找到插入位置
        * 循环的目的是:
        * eg:假设现在是第三轮和第二轮之间 要把14插入到某一个位置
        * 则 初始化 insertVal = arr[3] insertIndex=2;
        * 只要14（insertVal）<25arr[insertIndex] &&insertIndex >= 0
        * 则arr[insertVal+1] = arr[insertIndex] =>arr[3] = arr[2] =>[3 17 25]   [25 20 9]
        *  14已经保存在中间变量了 不用担心 => insertIndex--;
        * 继续比较：
        *   insertVal = arr[3] && insertIndex=1
        *   只要14（insertVal）<17(arr[insertIndex]) &&insertIndex >= 0
        *   则arr[insertVal+1] = arr[insertIndex] =>arr[3] = arr[2] =>[3 17 17]   [25 20 9]
        *   14已经保存在中间变量了 不用担心 => insertIndex--;
        * 此时while结束
        *   [3 17 17]   [25 20 9] =》[3 14 17 25]  [20 9]
        * */
/*        while (insertIndex >= 0 && insertVal < arr[insertIndex]){
            *//*这一步[17] [3 25 14 20 9] => [17] [17   25 14 20 9]
            因为 第一轮中3已经保存了 => insertVal =arr[1];
            * *//*
            arr[insertIndex+1] = arr[insertIndex];
            insertIndex--;
        }
        //当退出while循环时 说明插入的位置找到 insertIndex+1
        arr[insertIndex + 1] = insertVal;

        System.out.println("第一轮插入");
        System.out.println(Arrays.toString(arr));


        insertVal =arr[2];
        insertIndex = 2-1;
        while (insertIndex >= 0 && insertVal < arr[insertIndex]){
            *//*这一步[17] [3 25 14 20 9] => [17] [17   25 14 20 9]
            因为 第一轮中3已经保存了 => insertVal =arr[1];
            * *//*
            arr[insertIndex+1] = arr[insertIndex];
            insertIndex--;
        }
        //当退出while循环时 说明插入的位置找到 insertIndex+1
        arr[insertIndex + 1] = insertVal;

        System.out.println("第二轮插入");
        System.out.println(Arrays.toString(arr));

        insertVal =arr[3];
        insertIndex = 3-1;
        while (insertIndex >= 0 && insertVal < arr[insertIndex]){
            *//*这一步[17] [3 25 14 20 9] => [17] [17   25 14 20 9]
            因为 第一轮中3已经保存了 => insertVal =arr[1];
            * *//*
            arr[insertIndex+1] = arr[insertIndex];
            insertIndex--;
        }
        //当退出while循环时 说明插入的位置找到 insertIndex+1
        arr[insertIndex + 1] = insertVal;

        System.out.println("第三轮插入");
        System.out.println(Arrays.toString(arr));*/
    }
}
