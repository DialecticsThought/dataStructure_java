import java.sql.Array;
import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        /*
          归并排序（MERGE-SORT）是利用归并的思想实现的排序方法，
          该算法采用经典的分治（divide-and-conquer） 策略（分治法将问题分(divide)成一些小的问题然后递归求解
          ，而治(conquer)的阶段则将分的阶段得到的各答案"修 补"在一起，即分而治之)

        */
        int arr[] = {8,4,5,7,1,3,6,2};//8个数据合并7次
        int temp[]=new int[arr.length];//创建一个和arr一样大小的临时数组
        mergeSort(arr,0,arr.length-1,temp);
        System.out.println("归并排序后="+ Arrays.toString(arr));
    }
    /*int[] arr 原始数组
      int left 左边 有序序列的初始索引（遍历期间  左边组的第一个元素（最左边）索引）
      int mid 中间索引
    * int right 最后一个元素（最右边）索引（遍历期间  右边组的最后一个元素（最右边）索引）
      int[] temp 做中转的数组
    * */
    public static void mergeSort(int[] arr, int left,int right,int[] temp){
        //只要左边的索引 < 右边的索引 就不断递归
        if(left<right){
            int mid =(left+right)/2;//中间索引 向下取整
            //向左递归进行分解
            //向左的话 上一轮的mid就是这一轮的right
            /*
            * 一开始 left=0 right=7  因为 left<right 继续执行mergeSort(arr,left,mid,temp); 得到mid =3 开始递归
            * 第二轮 left=0 right=mid=3  因为left<right 继续执行mergeSort(arr,left,mid,temp); 得到mid=1 开始递归
            * 第三轮 left=0 right=mid=1 因为left<right 继续执行mergeSort(arr,left,mid,temp); 得到mid=0 开始递归
            * 第四轮 left=0 right=mid=0 因为不满足left<right 向下执行
            * */
            mergeSort(arr,left,mid,temp);
            /*
            * 上面的向左递归的整个流程结束后 mid=0
            *  第一轮 left=mid+1=1 不满足left<right 遍历结束
            * */
            //向右递归进行分解
            mergeSort(arr,mid+1,right,temp);
            //合并
          /*
          最初的数据 [8] [4] [5] [7] [1] [3] [6] [2]
          第一次合并  tempLeft = 0 right = 1 发生在 [8] 和[4]之间 把它们合并成[4,8]
          第二次合并  tempLeft = 2 right = 3 发生在 [5] 和[7]之间 把它们合并成[5,7]
          第三次合并  tempLeft = 0 right = 3 发生在[4,8]和[5,7]之间 把它们合并成[4,5,7,8]
          第四次合并  tempLeft = 4 right = 5 发生在 [1] 和[3]之间 把它们合并成[1,3]
          第五次合并  tempLeft = 6 right = 7 发生在 [6] 和[2]之间 把它们合并成[2,6]
          第六次合并  tempLeft = 4 right = 7 发生在[1,3]和[2,6]之间 把它们合并成[4,5,7,8]
          最后一次    tempLeft=0 right=7
          */
            merge(arr,left,mid,right,temp);
        }
    }
    //合并
    public static void merge(int[] arr, int left,int mid,int right,int[] temp){
        int i =left; //初始化i 左边有序序列的初始索引
        int j =mid +1;//初始化j 右边有序序列初始索引
        int t=0;//指向temp数组的当前索引
        /*1:

        1<4 将1填入temp数组 右移j
        [4 5 7 8][1  2  3  6]  [1        ]
         |        |->|
        i         j右移
        2<4 将2填入temp数组 右移j
        [4 5 7 8][1  2  3  6]  [1 2        ]
         |           |->|
         i           j右移
        3<4 将3填入temp数组 右移j
        [4 5 7 8][1  2  3  6]  [1 2 3       ]
         |              |->|
         i              j右移
        4<6 将4填入temp数组 右移i
        [4  5  7  8][1  2  3  6]  [1 2 3 4      ]
         |->|                 |
         i右移                j
        5<6 将5填入temp数组 右移i
        [4  5  7  8][1  2  3  6]  [1 2 3 4 5     ]
            |->|              |
            i右移             j
        6<7 将6填入temp数组 右移i(移动不了了)
        [4  5  7  8][1  2  3  6]  [1 2 3 4 5 6    ]
               |              |
               i              j
        右子序列全部结束 把左子序列的数全部放入temp


        先把左右两边（有序）的数据按照规则填充到temp数组
        知道左右两边的有序序列 有一边处理完毕为止*/
        while (i <= mid && j <= right) {
            //如果左边的有序序列的当前元素小于等于右边有序序列的当前元素
            if (arr[i] <= arr[j]) {
                //1.即 将左边的当前元素 拷贝到temp数组
                //2.t和i后移
                temp[t] = arr[i];
                t = t + 1;
                i = i + 1;
            } else {//如果左边的有序序列的当前元素大于右边有序序列的当前元素
                temp[t] = arr[j];
                t = t + 1;
                j = j + 1;
            }
        }

        /*
        * 2:把有剩余数据的一边的数据依次全部填充到temp
        * [4  5  7  8][1  2  3  6]  [1 2 3 4 5 6 7 8]
                 |  |--------------------------->| |
        * */
        while (i <= mid) {//如果左边的有序序列还有剩余的元素 全扔到temp
            temp[t] = arr[i];
            t = t + 1;
            i = i + 1;
        }
        while (j <= right) {//如果有边的有序序列还有剩余的元素 全扔到temp
            temp[t] = arr[j];
            t = t + 1;
            j = j + 1;
        }
        /*
        * 3:把temp数组的元素拷贝到arr
        * */
        t = 0;
        int tempLeft = left;
        /*第一次合并  tempLeft=0 right = 1
          第二次合并  tempLeft=2 right=3
          第三次合并  tempLeft = 0 right = 3
          第四次合并  tempLeft = 4 right = 5
          第五次合并  tempLeft = 6 right = 7
          第六次合并  tempLeft = 4 right = 7
          最后一次    tempLeft=0 right=7*/
        System.out.println("tempLeft = "+tempLeft+" right = "+right);
        while (tempLeft <= right){
            arr[tempLeft] =temp[t];
            t =t+1;
            tempLeft=tempLeft+1;
        }
    }
}
