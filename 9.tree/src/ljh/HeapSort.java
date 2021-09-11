package ljh;

import java.sql.Array;
import java.util.Arrays;

public class HeapSort {
    public static void main(String[] args) {
        //要求将数组进行升序
        int[] arr = {4,6,8,5,9};
        heapSort(arr);
    }
    public static void heapSort(int arr[]){
        int temp;
        //分步完成
/*        adjustToHeap(arr,1,arr.length);

        System.out.println(Arrays.toString(arr));//[4, 9, 8, 5, 6]

        adjustToHeap(arr,0,arr.length);

        System.out.println(Arrays.toString(arr));//[9, 6, 8, 5, 4]*/
        //1).将无序序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆;
        //大顶堆的特点 arr[i] >= arr[2*i+1] && arr[i]>=arr[2*i+2] 2*i+1指的是左子节点 2*i+2是右子节点
        //i=arr.length/2-1是因为完全2叉树 最后一个非叶子节点的所在索引
        for(int i=arr.length/2-1;i>=0;i--){
            adjustToHeap(arr,i,arr.length);
        }
        /*
        2).将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端;
        3).重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤， 直到整个序列有序。
        */
        //调整的个数是 arr.length-1 因为最后一个是最大的 "沉"到数组末端
        for(int j = arr.length-1;j>0;j--){
            //交换
            temp =arr[j];
            arr[j]=arr[0];
            arr[0]=temp;
            adjustToHeap(arr,0,j);
        }
        System.out.println(Arrays.toString(arr));
    }

    /*
      作用： 把索引i对应的非叶子节点为跟的子树调整成大顶堆
        eg: int arr[]={4,6,8,5,9} =>arr.length/2-1=1 =>adjustHeap =>{4,9,8,5,6}
        再次调整 i=0 {4,9,8,5,6} 得到 {9,6,8,5,4}
    * 讲一个数组（二叉树） 调整成一个大顶堆
    * int arr[]待调整的数组
    * int i 非叶子结点在数组中的索引
    * int length 表示对多少元素进行调整 length在逐渐减少
    * */
    public static void adjustToHeap(int arr[] ,int i,int length){
        //先取出当前元素的值 保存在临时变量
        int temp=arr[i];
        //开始调整
        //大顶堆的特点 arr[i] >= arr[2*i+1] && arr[i]>=arr[2*i+2] 2*i+1指的是左子节点 2*i+2是右子节点
        for(int k =i*2+1;k<length;k=k*2+1){
            if (k+1<length&& arr[k] < arr[k+1]) {//说明左子节点的值小于右子节点的值
                k++;//指向右子节点 因为要指向最大的
            }
            if(arr[k]>temp){//arr[k]是左右子节点中最大的 如果最大的>父节点
                arr[i] =arr[k];//最大的子节点 赋给父节点所在的索引
                i=k;// 变成k 再循环比较
            }else {
                break;
            }
        }
        //for循环后 已经将以i为父节点的树的最大值 放在最顶端（局部）
        arr[i]=temp;//将temp值放在调整后的位置 i因为不断在变
    }
}
