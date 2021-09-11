package ljh;

public class ArrayBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr={1,2,3,4,5,6,7};
        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(arr);
        arrayBinaryTree.preOrder();
        arrayBinaryTree.infixOrder();
    }

}
//编写一个 顺序存储二叉树的遍历
class ArrayBinaryTree{
    private int[] arr;//存储数据节点的数组

    public ArrayBinaryTree(int[] arr) {
        this.arr = arr;
    }
    //重载 preOrder
    public void preOrder(){
        this.preOrder(0);
    }

    public void infixOrder(){
        this.infixOrder(0);
    }
    //编写方法 完成顺序存储二叉树的前序遍历
    /*
    index是数组的下标
    完全二叉树
    第n个元素的左子节点为2*n+1
    第n个元素的右子节点为2*n+2
    第n个元素的父节点为（n-1）/2 这里向下取整了
    * */
    public void preOrder(int index){
        //如果数组为空 或者arr.lenght=0
        if(arr==null||arr.length==0){
            System.out.println("数组为空 不能按照二叉树的前序遍历");
        }
        //输出当前这个元素（父节点）
        System.out.println(arr[index]);
        //向左递归遍历
        if((index*2+1)<arr.length){//左子节点的索引不能超过数组长度
            preOrder(2*index+1);
        }
        //向右递归
        if((index*2+2)<arr.length){//右子节点的索引不能超过数组长度
            preOrder(2*index+2);
        }
    }
    public void infixOrder(int index){
        //如果数组为空 或者arr.lenght=0
        if(arr==null||arr.length==0){
            System.out.println("数组为空 不能按照二叉树的前序遍历");
        }

        //向左递归遍历
        //左子节点的索引不能超过数组长度
        //如果超过了 说明了是左节点 因为第n个元素的左子节点为2*n+1
        if((index*2+1)<arr.length){
            infixOrder(2*index+1);
        }
        //输出当前这个元素（父节点）
        System.out.println(arr[index]);
        //向右递归
        if((index*2+2)<arr.length){//右子节点的索引不能超过数组长度
            infixOrder(2*index+2);
        }
    }

    public void postOrder(int index){
        //如果数组为空 或者arr.lenght=0
        if(arr==null||arr.length==0){
            System.out.println("数组为空 不能按照二叉树的前序遍历");
        }
        //向左递归遍历
        if((index*2+1)<arr.length){//左子节点的索引不能超过数组长度
            postOrder(2*index+1);
        }
        //向右递归
        if((index*2+2)<arr.length){//右子节点的索引不能超过数组长度
            postOrder(2*index+2);
        }
        //输出当前这个元素（父节点）
        System.out.println(arr[index]);

    }
}