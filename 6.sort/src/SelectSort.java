import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class SelectSort {
    public static void main(String[] args) {
        int[] arr ={101,34,119,1};
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
        //开始随机数的选择排序
        selectSort(arr_random);
        Date date2 = new Date();
        String dateStr2 = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是:"+dateStr2);

    }

    //选择排序
    public static void selectSort(int[] arr) {
        //使用逐步推导的方式
        /*
            时间复杂度：O(n^2)
            选择排序（select sorting）也是一种简单的排序方法。
            它的基本思想是：第一次从 arr[0]~arr[n-1]中选取最小值，
            与 arr[0]交换，第二次从 arr[1]~arr[n-1]中选取最小值，
            与 arr[1]交换，第三次从 arr[2]~arr[n-1]中选取最小值，
            与 arr[2] 交换，…，第 i 次从 arr[i-1]~arr[n-1]中选取最小值，
            与arr[i-1]交换，…, 第 n-1 次从 arr[n-2]~arr[n-1]中选取最小值，
            与 arr[n-2]交换，总共通过 n-1 次，得到一个按排序码从小到大排列的有序序列。
         * 原始的数组：  101,34,119,1
         * 第一轮：      1,[34,119,101]
         * 第二轮：      1,34,[119,101]
         * 第三轮：      1,34,101,[119]
         * */
        for (int i = 0;i < arr.length - 1;i++){

            int minIndex = i;//假定最小的数的索引 是0
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                //从大到小排序的话就是把 "<" 改成 ">"
                if (min > arr[j]) {//说明假定的最小值不是最小
                    min = arr[j];//重置最小值
                    minIndex = j;//重置最小值所在的索引
                }
            }
            //将最小值 放在arr[0] 即交换
            if(minIndex != i){
                arr[minIndex] = arr[i];
                arr[i] = min;
            }

            //System.out.println("第"+(i+1)+"轮~~");
            //System.out.println(Arrays.toString(arr));

        }

    }


/*        int minIndex = 0;//假定最小的数的索引 是0
        int min = arr[0];
        for(int j= 0 + 1;j <arr.length;j++){
            if(min > arr[j]){//说明假定的最小值不是最小
                min = arr[j];//重置最小值
                minIndex = j;//重置最小值所在的索引
            }
        }
        //将最小值 放在arr[0] 即交换
        arr[minIndex] = arr[0];
        arr[0] = min;

        System.out.println("第一轮~~");
        System.out.println(Arrays.toString(arr));

        //第二轮
        minIndex = 1;//假定最小的数的索引 是0
        min = arr[1];
        for(int j= 1 + 1;j <arr.length;j++){
            if(min > arr[j]){//说明假定的最小值不是最小
                min = arr[j];//重置最小值
                minIndex = j;//重置最小值所在的索引
            }
        }
        //将最小值 放在arr[0] 即交换
        arr[minIndex] = arr[1];
        arr[1] = min;

        System.out.println("第二轮~~");
        System.out.println(Arrays.toString(arr));

        //第三轮
        minIndex = 2;//假定最小的数的索引 是0
        min = arr[2];
        for(int j= 2 + 1;j <arr.length;j++){
            if(min > arr[j]){//说明假定的最小值不是最小
                min = arr[j];//重置最小值
                minIndex = j;//重置最小值所在的索引
            }
        }
        //将最小值 放在arr[0] 即交换
        arr[minIndex] = arr[2];
        arr[2] = min;

        System.out.println("第三轮~~");
        System.out.println(Arrays.toString(arr));
    }*/
}
