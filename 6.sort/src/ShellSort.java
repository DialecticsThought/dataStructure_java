import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ShellSort {
    public static void main(String[] args) {
        int[] arr ={8,9,1,7,2,3,5,4,6,0};
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
        //开始随机数的希尔排序
        shellSort2(arr_random);
        Date date2 = new Date();
        String dateStr2 = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是:"+dateStr2);

    }
    //使用 逐步推导的方式来编写希尔排序
    public static void shellSort(int[] arr){

        int temp = 0;
        int count = 0;
        //增量的gap 逐步缩小增量
        //gap = arr.length / 2向下取整的整数
        //结束的条件 只要gap = arr.length / 2向下取整的整数 = 0 才算结束
        for (int gap = arr.length / 2; gap > 0; gap = gap / 2) {
            for (int i = gap; i < arr.length; i++) {
                //遍历各组中 的所有元素 共gap组 每组[(arr.length+1)/gap]个元素 => 步长是gap
                for (int j = i - gap; j >= 0; j -= gap) {
                    //如果当前元素 大于加上步长后的那个元素 要交换
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
            //System.out.println("第" + (count++) + "轮希尔排序");
            //System.out.println(Arrays.toString(arr));
        }
/*
        *//*
        *   ------------
        *   |         |
        * ----------- |
        * \ |       \ |
        * 8 9 1 7 2 3 5 4 6 0
        *     | \       | \
        *     ----------- \
        *       \         \
        *       -----------
        * *//*
        //int temp = 0;
        //希尔排序的第1轮排序
        //因为第1轮排序 是将10个数据分成了5组
        for(int i = 5; i < arr.length;i++){
            //遍历各组中 的所有元素 共5组 每组2个元素 => 步长是5 =arr.length/2
            for (int j = i -5; j>= 0 ; j -= 5){
                //如果当前元素 大于加上步长后的那个元素 要交换
                if (arr[j] > arr[j + 5]) {
                    temp = arr[j];
                    arr[j] = arr[j + 5];
                    arr[j + 5] = temp;
                }
            }
        }
        System.out.println("第一轮希尔排序");
        System.out.println(Arrays.toString(arr));
        *//*
         * ----------------
         * \   \   \   \   \
         * 3 5 1 6 0 8 9 4 7 2
         *   |   |   |   |   |
         *   -----------------
         * *//*
        for(int i = 2; i < arr.length;i++){
            //遍历各组中 的所有元素 共2组 每组5个元素 => 步长是2 =arr.length/2/2
            for (int j = i -2; j>= 0 ; j -= 2){
                //如果当前元素 大于加上步长后的那个元素 要交换
                if (arr[j] > arr[j + 2]) {
                    temp = arr[j];
                    arr[j] = arr[j + 2];
                    arr[j + 2] = temp;
                }
            }
        }
        System.out.println("第二轮希尔排序");
        System.out.println(Arrays.toString(arr));
        *//*
         * ------------------
         * \ \ \ \ \ \ \ \ \ \
         * 0 2 1 4 3 5 7 6 9 8
         * *//*
        for(int i = 1; i < arr.length;i++){
            //遍历各组中 的所有元素 共1组 每组10个元素 => 步长是1 =arr.length/2/2/2
            for (int j = i -1; j>= 0 ; j -= 1){
                //如果当前元素 大于加上步长后的那个元素 要交换
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println("第三轮希尔排序");
        System.out.println(Arrays.toString(arr));*/
    }

    //对交换式的希尔排序进行优化 -》移位法
    public static  void shellSort2(int[] arr){
        /*
         *   ------------
         *   |         |
         * ----------- |
         * \ |       \ |
         * 8 9 1 7 2 3 5 4 6 0
         *     | \       | \
         *     ----------- \
         *       \         \
         *       -----------
         * 第一次 遍历各组中 的所有元素 共5组 每组2个元素 => 步长是5 =arr.length/2
         * i = 5 只要 i < arr.length i++
         * j = 5 temp =arr[5]
         * 判断arr[5] 和arr[0]大小
         *
         * */

        //增量的gap 逐步缩小增量 免去发现一个就交换一个
        for (int gap = arr.length / 2; gap > 0; gap = gap / 2) {
            //从第gap个元素 逐个对其所在的组  进行直接插入排序
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[i];
                if (arr[j] < arr[j - gap]) {
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        //移动
                        arr[j] = arr[j - gap];
                        j = j - gap;
                    }
                    //当退出while就是给temp找到插入的位置
                    arr[j] = temp;
                }
            }
        }
    }
}
