package ljh;

public class BinarySortTreeDemo {
    public static void main(String[] args) {
    int[] arr={7,3,10,12,5,1,9};
        BinarySortTree binarySortTree = new BinarySortTree();
        //循环添加节点到二叉排序树
        for (int i=0;i<arr.length;i++){
            binarySortTree.add(new Node(arr[i]));
        }
        //中序遍历二叉排序树
        System.out.println("中序遍历二叉排序树");
        binarySortTree.infixOrder();
        binarySortTree.delNode(7);
        binarySortTree.infixOrder();
    }
}
//创建二叉排序树
class BinarySortTree{
    private Node root;
    public void add(Node node){
        if(root==null){
            root =node;//空树的话 直接当做根节点
        }else {
            root.add(node);
        }
    }
    //中序遍历
    public void infixOrder(){
        if(root==null){
            System.out.println("这是一个空数");
        }else {
            root.infixOrder();
        }
    }
    //查找要删除的节点
    public Node search(int value){
        if(root==null){
            return null;
        }else {
            return root.search(value);
        }
    }
    public Node searchParent(int value){
        if(root==null){
            return null;
        }else {
            return root.searchParent(value);
        }
    }
    /*
    * node传入的节点 传入的是目标节点的右子节点 （当做二叉排序树的根节点）<=> targetNode.right
    * 返回的以node为根节点的二叉排序树的最小节点的值
    * */
    public int delRightTreeMin(Node node){
        Node target = node;
        //循环查找右子树的左节点 就会找到最小值（右子树的最左下角）
        while (target.left!=null){
            target=target.left;
        }
        //此时target指向了最小节点
        //删除最小节点(一定是叶子结点 根据二叉排序树的性质)
        delNode(target.value);
        return target.value;
    }


    //删除节点
    public void delNode(int value){
        if(root==null){
            return;
        }else {
            //1.需求先去找到要删除的节点 targetNode
            Node targetNode = search(value);
            //如果没有找到要删除的节点
            if(targetNode==null){
                return;//直接返回
            }
            //如果 targetNode没有左右子节点
            if (root.left==null&&root.right==null){
                root=null;
                return;
            }
            //2.需求先去找到要删除的节点 targetNode的父节点
            Node parent = searchParent(value);
            //如果要删除的节点是叶子结点
            if(targetNode.left==null&&targetNode.right==null){
                //判断targetNode是父节点的左子节点 还是右子节点
                if(parent.left!=null&&parent.left.value==value){
                    parent.left =null;//左子节点置空 <=>删除targetNode
                }else if(parent.right!=null&&parent.right.value==value){
                    parent.right =null;//右子节点置空 <=>删除targetNode
                }
            }else if (targetNode.left!=null&&targetNode.right!=null){//删除有两颗子树的节点
                int minVal = delRightTreeMin(targetNode.right);
                targetNode.value=minVal;//重置targetNode相当于删除
            }else {//删除只有一个子树的节点
                //删除只有一个左子树的节点
                if(targetNode.left!=null){
                    //targetNode是 parent的左子节点
                    if(parent.left.value==value){
                        //targetNode.left是因为targetNode是 删除只有一个左子树的节点
                        //parent.left是因为 targetNode是 parent的左子节点
                        parent.left=targetNode.left;//让被删除节点的左子节点接到父节点的左子节点的位置上
                    }else {//targetNode是 parent的右子节点
                        //targetNode.left是因为targetNode是 删除只有一个左子树的节点
                        //parent.right是因为 targetNode是 parent的右子节点
                        parent.right=targetNode.left;
                    }
                }else { //删除只有一个右子树的节点
                    //targetNode是 parent的左子节点
                    if(parent.left.value==value){
                        //targetNode.right是因为targetNode是 删除只有一个右子树的节点
                        //parent.left是因为 targetNode是 parent的左子节点
                        parent.left=targetNode.right;
                    }else {//targetNode是 parent的右子节点
                        //targetNode.right是因为targetNode是 删除只有一个右子树的节点
                        //parent.right是因为 targetNode是 parent的右子节点
                        parent.right=targetNode.right;
                    }
                }
            }
        }
    }
}


class Node{
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
    //查找要删除的节点
    /*
    * int value希望删除节点的值
    * 返回找到的节点 找不到 返回null
    * */
    public Node search(int value){
        if(value==this.value){
            return this;
        }else if(value<this.value){//要找的值得节点< 现在的节点的值 根据二叉排序树的特性 向左递归
            //判断左子节点是否为空
            if(this.left==null){
                return null;
            }
            return this.left.search(value);//向左递归
            }else {//要找的值得节点> 现在的节点的值 根据二叉排序树的特性 向左递归
            //判断右子节点是否为空
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);//向右递归
        }

    }
    //找到删除节点的父节点
    /*
    * int value希望删除节点的值
    * 返回找到的节点的父节点 找不到 返回null
    * */
    public Node searchParent(int value){
        //如果当前节点 的左子节点不为空 并且 左子节点的值就是要找的值 ||当前节点 的右子节点不为空 并且 右子节点的值就是要找的值
        if((this.left!=null&&this.left.value==value)
                ||(this.right!=null&&this.right.value==value)){
            return this;
        }else {
            //如果查找的值小于当前节点的值 并且当前节点的左子节点不为空
            //向左子树递归
            if(value<this.value&&this.left!=null){
                return this.left.searchParent(value);
            }else if(value>=this.value&&this.right!=null){
                //如果查找的值>=当前节点的值 并且当前节点的右子节点不为空
                //向右子树递归
                return this.right.searchParent(value);
            }else {
                return null;//没有父节点 eg:要找的是root
            }
        }
    }
    //添加节点的方法
    public void add(Node node){
        //递归的形式添加节点 需要满足二叉排序树的要求
        if(node==null){
            return;
        }
        //判断传入的节点的值 和当前子树的根节点的值关系
        if(node.value <this.value){
            //如果当前节点的左子节点为null
            if(this.left == null){
                this.left=node;
            }else {//左子树不为空
                //递归的向左子树添加节点
                //因为左子节点还要判断被加入的节点的大小 判断路径
                this.left.add(node);
            }
        }else {//添加的节点的值大于当前节点的值
            if(this.right ==null){
                this.right=node;
            }else {
                this.right.add(node);
            }
        }
    }
    //中序遍历
    public void infixOrder(){
        if(this.left !=null){
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right!=null){
            this.right.infixOrder();
        }
    }
}