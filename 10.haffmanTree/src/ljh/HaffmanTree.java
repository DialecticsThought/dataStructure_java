package ljh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HaffmanTree {
    public static void main(String[] args) {
        int arr[] ={13,7,8,3,29,6,1};
        Node huffmanTree = createHuffmanTree(arr);
        //测试一把
        preOrder(huffmanTree);
    }
    //编写一个前序遍历的方法
    public static void preOrder(Node root){
        if(root!=null){
            root.preOrder();
        }else {
            System.out.println("这个是空数 不能遍历");
        }
    }

    //创建huffman树的方法 返回的是root节点
    public static Node createHuffmanTree(int[] arr){
        //1.遍历arr数组
        //2.将arr的每一个元素构成一个node
        //把Node放在集合中
        ArrayList<Node> nodes = new ArrayList<>();
        for (int value:arr){
            nodes.add(new Node(value));
        }
        //处理的过程使循环的过程
        //处理的结果就是集合里面只有一个节点
        while (nodes.size()>1){
            //排序从小到大
            //node实现Comparable接口+调用Collections.sort方法
            Collections.sort(nodes);

            System.out.println("nodes = "+nodes);

            //取出根节点 权值最小的两颗二叉树
            //1.取出权值最小的节点（二叉树）
            Node left = nodes.get(0);
            //2.取出权值第二小的节点（二叉树）
            Node right = nodes.get(1);
            //3.构建一个新的二叉树
            Node parent = new Node(left.value + right.value);
            parent.left=left;
            parent.right=right;
            //4.从ArrayList中删除处理过的二叉树
            nodes.remove(left);
            nodes.remove(right);
            //5.将parent加入到集合中
            nodes.add(parent);
        }
        //返回huffman数的root节点
        return nodes.get(0);
/*        //排序从小到大
        //node实现Comparable接口+调用Collections.sort方法
        Collections.sort(nodes);

        System.out.println("nodes = "+nodes);

        //取出根节点 权值最小的两颗二叉树
        //1.取出权值最小的节点（二叉树）
        Node left = nodes.get(0);
        //2.取出权值第二小的节点（二叉树）
        Node right = nodes.get(1);
        //3.构建一个新的二叉树
        Node parent = new Node(left.value + right.value);
        parent.left=left;
        parent.right=right;
        //4.从ArrayList中删除处理过的二叉树
        nodes.remove(left);
        nodes.remove(right);
        //5.将parent加入到集合中
        nodes.add(parent);*/
    }
}
//创建节点类
//为了让node对象持续排序 ==>实现Comparable接口
class Node implements Comparable<Node>{
    int value;//节点权值
    Node left;//左子节点
    Node right;//右子节点
    //前序遍历
    public void preOrder(){
        System.out.println(this);
        if(this.left!=null){
            this.left.preOrder();
        }
        if (this.right!=null){
            this.right.preOrder();
        }
    }
    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
          //从小到大排序 java基础
        return this.value-o.value;
    }
}
