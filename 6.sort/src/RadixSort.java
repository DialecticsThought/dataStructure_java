import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class RadixSort {
    public static void main(String[] args) {
        int arr[] = {53, 3, 542, 748, 14, 214};
        //radixSort(arr);
        //80000000 * 11 * 4 / 1024 /1024 /1024 =3.3Gb
        int[] arr_random = new int[80000000];
        for(int i = 0; i< 80000000; i++){
            //生成一个[0,8000000)的随机数
            arr_random[i] = (int)(Math.random()*8000000);
        }
        Date date1 = new Date();
        //格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String dateStr1 = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是:"+dateStr1);
        //开始随机数的基数排序
        radixSort(arr_random);
        Date date2 = new Date();
        String dateStr2 = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是:"+dateStr2);
    }

    //基数排序法
    public static void radixSort(int[] arr) {
        //根据下面的推导过程 可以得到最终的基数排序的代码
        //得到数组中最大的数的为数
        int max = arr[0];//假设第一个数是最大的数
        for(int i = 1;i<arr.length;i++){
                if(arr[i]>max){
                    max =arr[i];
                }
        }
        //得到最大数是几位数
        int maxLength=(max+"").length();
        int[][] bucket = new int[10][arr.length];
        //为了记录每一个桶中 实际存放了多少数据 我们定义一个一维数组 记录每一个桶的每次放入的数据个数
        //bucketElementCounts[0] 记录的是bucket[0]的放入数据的个数
        int[] bucketElementCounts = new int[10];
        //循环 n的步长是10
        for(int i = 0,n=1; i<maxLength;i++,n=n*10){

            // 针对每个元素的对应位进行排序处理  eg:第1轮（针对数组中每个元素的个位进行排序处理）
            for (int j = 0; j < arr.length; j++) {
                //取出每个元素的对应位的值
                //int digitOfElement =arr[j]%10;
                int digitOfElement = arr[j] / n % 10;
                //放入到对应的桶中
                //假设个位数得到是1 那么digitOfElement=1 就是放在1号桶
                //但是桶里面可能已经有其他元素了 根据bucketElementCounts得知已经有多个元素 把当前元素放在第几列
                //比如说 1号桶 之前没有放过东西 那么bucketElementCounts[1]=0
                //就是 bucket[1][0]=arr[j] 在一号桶的最底层
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;//eg:当前0号位置被占 让下一个数放在下一个位置
            }
            //按照这个桶的顺序（一维数组的下标依次取出数据 放入原来的数组）
            int index = 0;
            //遍历每一个桶并将桶中的数据放入到原数组
            for (int k = 0; k < bucketElementCounts.length; k++) {
                //如果桶中 有数据 我们才放入原数组
                if (bucketElementCounts[k] != 0) {
                    //循环该桶 即第k个一维数组(二维数组同一行不同的列 第k列) 放入
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        //取出元素放入arr
                        arr[index++] = bucket[k][l];
                    }
                }
                //第i+1轮处理后 需要将每一个bucketElementCounts[k] = 0 !!!!
                bucketElementCounts[k] = 0;
            }
           // System.out.println("第"+(i+1)+"轮" + Arrays.toString(arr));
        }




        /*//1.第一个二维数组 表示10个桶 每一个桶就是一个一维数组
        //2.一个二维数组包含10个一维数组
        //3.为了防止在放入数的时候 数据溢出 每一个一维数组(桶) 大小定位arr.length
        // 基数排序是用空间换时间的经典算法
        int[][] bucket = new int[10][arr.length];
        //为了记录每一个桶中 实际存放了多少数据 我们定义一个一维数组 记录每一个桶的每次放入的数据个数
        //bucketElementCounts[0] 记录的是bucket[0]的放入数据的个数
        int[] bucketElementCounts = new int[10];
        //第1轮（针对数组中每个元素的个位进行排序处理）
        for (int j = 0; j < arr.length; j++) {
            //取出每个元素的个位的值
            //int digitOfElement =arr[j]%10;
            int digitOfElement = arr[j] / 1 % 10;
            //放入到对应的桶中
            //假设个位数得到是1 那么digitOfElement=1 就是放在1号桶
            //但是桶里面可能已经有其他元素了 根据bucketElementCounts得知已经有多个元素 把当前元素放在第几列
            //比如说 1号桶 之前没有放过东西 那么bucketElementCounts[1]=0
            //就是 bucket[1][0]=arr[j] 在一号桶的最底层
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;//eg:当前0号位置被占 让下一个数放在下一个位置
        }
        //按照这个桶的顺序（一维数组的下标依次取出数据 放入原来的数组）
        int index = 0;
        //遍历每一个桶并将桶中的数据放入到原数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            //如果桶中 有数据 我们才放入原数组
            if (bucketElementCounts[k] != 0) {
                //循环该桶 即第k个一维数组(二维数组同一行不同的列 第k列) 放入
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    //取出元素放入arr
                    arr[index++] = bucket[k][l];
                }
            }
            //第1轮处理后 需要将每一个bucketElementCounts[k] = 0 !!!!
            bucketElementCounts[k] = 0;
        }
        System.out.println("第1轮" + Arrays.toString(arr));


        //第2轮（针对数组中每个元素的十位进行排序处理）
        for (int j = 0; j < arr.length; j++) {
            //取出每个元素的个位的值
            int digitOfElement = arr[j] / 10 % 10;// 889/10=88 88%10=8
            //放入到对应的桶中
            //假设个位数得到是1 那么digitOfElement=1 就是放在1号桶
            //但是桶里面可能已经有其他元素了 根据bucketElementCounts得知已经有多个元素 把当前元素放在第几列
            //比如说 1号桶 之前没有放过东西 那么bucketElementCounts[1]=0
            //就是 bucket[1][0]=arr[j] 在一号桶的最底层
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;//eg:当前0号位置被占 让下一个数放在下一个位置
        }
        //按照这个桶的顺序（一维数组的下标依次取出数据 放入原来的数组）
        index = 0;
        //遍历每一个桶并将桶中的数据放入到原数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            //如果桶中 有数据 我们才放入原数组
            if (bucketElementCounts[k] != 0) {
                //循环该桶 即第k个一维数组 放入
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    //取出元素放入arr
                    arr[index++] = bucket[k][l];
                }
            }
            //第3轮处理后 需要将每一个bucketElementCounts[k] = 0 !!!!
            bucketElementCounts[k] = 0;
        }
        System.out.println("第2轮" + Arrays.toString(arr));

        //第3轮（针对数组中每个元素的百位进行排序处理）
        for (int j = 0; j < arr.length; j++) {
            //取出每个元素的个位的值
            int digitOfElement = arr[j] / 100 % 10;// 889/10=88 88%10=8
            //放入到对应的桶中
            //假设个位数得到是1 那么digitOfElement=1 就是放在1号桶
            //但是桶里面可能已经有其他元素了 根据bucketElementCounts得知已经有多个元素 把当前元素放在第几列
            //比如说 1号桶 之前没有放过东西 那么bucketElementCounts[1]=0
            //就是 bucket[1][0]=arr[j] 在一号桶的最底层
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;//eg:当前0号位置被占 让下一个数放在下一个位置
        }
        //按照这个桶的顺序（一维数组的下标依次取出数据 放入原来的数组）
        index = 0;
        //遍历每一个桶并将桶中的数据放入到原数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            //如果桶中 有数据 我们才放入原数组
            if (bucketElementCounts[k] != 0) {
                //循环该桶 即第k个一维数组 放入
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    //取出元素放入arr
                    arr[index++] = bucket[k][l];
                }
            }
            bucketElementCounts[k] = 0;
        }
        System.out.println("第2轮" + Arrays.toString(arr));*/
    }
}
