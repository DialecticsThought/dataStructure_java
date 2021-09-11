package ljh;

public class InsertSearch {
    public static void main(String[] args) {
        int arr[] =new int[100];
        for(int i=0;i<100;i++){
            arr[i]=i;
        }
        System.out.println(insertSearch(arr,0,arr.length-1,1));
    }
    /*
    int[] arr被查找的数组
    int left 左边的索引
    int right 右边的索引
    int findVal 要找的数字
    * */
    //差值查找算法
    public static int insertSearch(int[] arr,int left,int right,int findVal){

        System.out.println("查找次数");
        //当left > right 说明递归整个数组 但是没有找到
        if(left>right || findVal<arr[0]||findVal>arr[arr.length-1]){
            return -1;
        }
        int mid = left +(right-left)*(findVal -arr[left])/(arr[right]-arr[left]);
        int midVal =arr[mid];
        if(findVal>midVal){
            return insertSearch(arr,mid+1,right,findVal);
        }else if(findVal<midVal){
            return insertSearch(arr,left,mid-1,findVal);
        }else {
            return mid;
        }
    }
}
