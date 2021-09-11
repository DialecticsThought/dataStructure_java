package ljh.threadBinaryTree;



public class ThreadBinaryTreeDemo {
    public static void main(String[] args) {
        HeroNode root = new HeroNode(1, "tom");
        HeroNode node2 = new HeroNode(3, "jack");
        HeroNode node3 = new HeroNode(6, "smith");
        HeroNode node4 = new HeroNode(8, "marry");
        HeroNode node5 = new HeroNode(10, "king");
        HeroNode node6 = new HeroNode(14, "john");

        root.setLeftNode(node2);
        root.setRightNode(node3);
        node2.setLeftNode(node4);
        node2.setRightNode(node5);
        node3.setLeftNode(node6);

        //测试线索化
        ThreadBinaryTree threadBinaryTree = new ThreadBinaryTree();
        threadBinaryTree.setRoot(root);
        threadBinaryTree.threadedNotes();

        //测试 选10号节点 没有执行这2个方法的话 会是null
        HeroNode leftNode = node5.getLeftNode();
        HeroNode rightNode = node5.getRightNode();
        System.out.println("10号节点的前驱节点 = "+leftNode);
        System.out.println("10号节点的后继节点 = "+rightNode);

        HeroNode leftNode4 = node4.getLeftNode();
        HeroNode rightNode4 = node4.getRightNode();
        System.out.println("8号节点的前驱节点 = "+leftNode4);
        System.out.println("8号节点的后继节点 = "+rightNode4);

        System.out.println("使用线索化的遍历方式 线索化二叉树");
        threadBinaryTree.threadedList();
    }
}

class ThreadBinaryTree {
    private HeroNode root;
    //为了是先线索化 需要创建要给指向当前节点的前驱节点的指针
    //在递归进行线索化时 pre总是保留前驱节点
    private HeroNode prior;//线索化时指向前驱节点 但是树是单向的
    public HeroNode getRoot() {
        return root;
    }

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    //重载
    public void threadedNotes(){
        this.threadedNotes(root);
    }
    //遍历线索化二叉树的方法
    /*
    因为线索化后，各个结点指向有变化，因此原来的遍历方式不能使用，
    这时需要使用新的方式遍历 线索化二叉树，各个节点可以通过线型方式遍历，
    因此无需使用递归方式，这样也提高了遍历的效率。
    遍历的次 序应当和中序遍历保持一致。
    * */
    public void threadedList(){
        //定一个变量 存储当前遍历到的节点 从root开始 后续要用
        HeroNode node = root;
        /*
        * 整个循环过程 都是除了最后一句话其他都是在获得前驱节点 或者后继节点
        * */
        while (node !=null){
            //循环到的leftType==1的节点 第一个找到的就是节点num为8 因为中序遍历的结果：{8, 3, 10, 1, 14, 6}
            //后面随着遍历而变化 因为当leftType==1时 说明该节点是线索化的
            while (node.getLeftType()==0){//只要leftType==0说明是不是线索化的 有左子节点 就一直循环
                node=node.getLeftNode();
            }
            //打印当前节点
            System.out.println(node);
            //如果当前节点的右指针是后继节点说明node.getRightType()==1 就一直输出
            while (node.getRightType()==1){
                //获取当前节点的后继节点
                // 对于node.getRightType()==1 的节点 node.getRightNode()得到的是父(前驱)节点
                node=node.getRightNode();
                System.out.println(node);
            }
            //替换这个遍历的节点
            //退出循环后的得到的node getRightType()==0说明有右子树 让当前node的右子节点赋给node 再执行上面的操作
            node=node.getRightNode();
        }
    }

    //编写二叉树进行中序线索化的方法
    //前驱节点 后继节点 都是看中序遍历的结果 eg:{8,3,10,1,14,6}  10的前驱就是3 后继就是1
    public void threadedNotes(HeroNode node){
        //如果node为空 不能线索化
        if(node == null){
            return;
        }
        //先线索化左子树
        //只要该节点有左子节点 就一直递归 直到该节点的左子节点为空node == null 再执行下面的方法
        threadedNotes(node.getLeftNode());
        //线索化当前节点
        //先处理当前节点的前驱节点 如果没有前驱节点的话 就会指向空
        if(node.getLeftNode()==null){
            //让当前节点指向前驱节点
            node.setLeftNode(prior);
            //修改当前节点的左指针的类型
            node.setLeftType(1);
        }
        //处理后继节点 不同于上面 prior指向的是当前节点 后继节点是node
        if(prior!=null&&prior.getRightNode()==null){
            //让当前节点指向后继节点
            prior.setRightNode(node);
            //修改当前节点的右指针的类型
            prior.setRightType(1);
        }
        //每处理一个节点 让当前节点是下一个节点的前驱节点 也就是pre后移
        prior =node;
        //线索化右子树
        threadedNotes(node.getRightNode());
    }



    //前序遍历
    public void preOrder() {
        //如果当前树的根节点不为空
        if (this.root != null) {
            //调用preOrder();
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空 无法遍历");
        }
    }

    public void infixOrder() {
        //如果当前树的根节点不为空
        if (this.root != null) {
            //调用preOrder();
            this.root.infixOrder();
        } else {
            System.out.println("二叉树为空 无法遍历");
        }
    }

    public void postOrder() {
        //如果当前树的根节点不为空
        if (this.root != null) {
            //调用preOrder();
            this.root.postOrder();
        } else {
            System.out.println("二叉树为空 无法遍历");
        }
    }

    //前序遍历查找
    public HeroNode preOrderSearch(int num) {
        if (root != null) {
            return root.preOrderSearch(num);
        } else {
            return null;
        }
    }

    //中序遍历查找
    public HeroNode infixOrderSearch(int num) {
        if (root != null) {
            return root.infixOrderSearch(num);
        } else {
            return null;
        }
    }

    //后序遍历查找
    public HeroNode postOrderSearch(int num) {
        if (root != null) {
            return root.postOrderSearch(num);
        } else {
            return null;
        }
    }
}

//创建HeroNode节点
class HeroNode {
    private int num;
    private String name;
    private HeroNode leftNode;//左节点 默认为null
    private HeroNode rightNode;//右节点 默认为null
    /*
    * 如果leftType=0 说明指向的是左子树 1表示的指向前驱节点
    * 如果rightType=0 说明指向的是右子树 1表示的指向后继节点
    * */
    private int leftType;
    private int rightType;

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public HeroNode() {
    }

    public HeroNode(int num, String name) {
        this.num = num;
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(HeroNode leftNode) {
        this.leftNode = leftNode;
    }

    public HeroNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(HeroNode rightNode) {
        this.rightNode = rightNode;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }

    /*
     * 如果删除的节点是叶子节点 删除该节点
     * 如果删除的节点是非叶子结点 删除该子树
     *
     * 1.先向左递归 都会判断当前节点的左子节点 再判断当前的右子节点
     * 2.再向右递归 都会判断当前节点的左子节点 再判断当前的右子节点（如果发现的右子树也有向左的分支的话 也要优先向左递归）
     * */
    public void deleteNode(int num) {
        //如果只有一个节点（root） 则将二叉树置空
        //二叉树是单向 判断当前的节点的子节点是否是被删除的节点
        //1.当前节点的左子节点不为空 并且左子节点就是要删除的节点 =>this.left=null 结束递归 返回被删节点
        if (this.leftNode != null && this.leftNode.num == num) {
            this.leftNode = null;
            return;
        }
        //2.当前节点的右子节点不为空 并且右子节点就是要删除的节点 =>this.right=null 结束递归 返回被删节点
        if (this.rightNode != null && this.rightNode.num == num) {
            this.rightNode = null;
            return;
        }
        //3.没有删除节点的话 一直向左递归 直到无法递归(尽头) 再向右递归
        if (this.leftNode != null) {
            this.leftNode.deleteNode(num);
        }
        //3.没有删除节点的话 一直向左递归 直到无法递归(尽头) 再向右递归
        if (this.rightNode != null) {
            this.rightNode.deleteNode(num);
        }
    }


    /*编写前序遍历的方法
    先输出父节点(初始的时候是root节点)，
    再遍历左子树 左子节点不为空 继续前序遍历
    最后遍历右子树 右子节点不为空 继续递归前序遍历
    */
    public void preOrder() {
        System.out.println(this);//1.输出父节点
        //2.再遍历左子树 当前对象的左子节点不为空 继续前序遍历
        if (this.leftNode != null) {
            this.leftNode.preOrder();//左子节点的前序遍历
        }
        //3.遍历右子树 当前对象的右子节点不为空 继续递归前序遍历
        if (this.rightNode != null) {
            this.rightNode.preOrder();//右子节点的前序遍历
        }
    }

    /*中序遍历:
    先遍历左子树，左子节点不为空 继续中序遍历
    输出当前节点，
    再遍历右子树 右子节点不为空 继续中序遍历
    */
    public void infixOrder() {
        //1.再遍历左子树 当前对象的左子节点不为空 继续中序遍历
        if (this.leftNode != null) {
            this.leftNode.infixOrder();//左子节点的中序遍历
        }
        System.out.println(this);//2.输出父节点
        //3.遍历右子树 当前对象的右子节点不为空 继续递归中序遍历
        if (this.rightNode != null) {
            this.rightNode.infixOrder();//右子节点的中序遍历
        }
    }

    //后序遍历: 先遍历左子树，再遍历右子树，最后输出父节点
    public void postOrder() {
        //1.再遍历左子树 当前对象的左子节点不为空 继续后序遍历
        if (this.leftNode != null) {
            this.leftNode.postOrder();//左子节点的后序遍历
        }
        //2.遍历右子树 当前对象的右子节点不为空 继续递归后序遍历
        if (this.rightNode != null) {
            this.rightNode.postOrder();//右子节点的后序遍历
        }
        System.out.println(this);//3.输出父节点
    }

    //前序遍历查找
    public HeroNode preOrderSearch(int num) {
        /*
         * 先判断当前节点的num是不是等于要找的
         *   相等 返回当前节点
         *   不相同 判断当前节点的左子节点是否为空 为空 递归前序查找
         *   如果左递归前序查找 找到节点 就返回 否则继续判断 当前的节点的右子节点是否为空
         *   不空 则继续右递归前序查找
         * */
        System.out.println("进入前序遍历");
        //比较当前节点是不是要找的节点
        if (this.num == num) {
            return this;
        }
        //1.判断当前节点的左子节点是否为空 不为空 则递归前序查找
        //2.如果左递归前序查找 找到节点 赋给resultNode  就返回
        HeroNode resultNode = null;
        if (this.leftNode != null) {
            //当前对象的左子节点调用该方法
            resultNode = this.leftNode.preOrderSearch(num);
        }
        if (resultNode != null) {
            return resultNode;//说明找到了
        }
        //3.否则继续判断 当前的节点的右子节点是否为空 不空 则继续右递归前序查找
        if (this.rightNode != null) {
            //当前对象的右子节点调用该方法
            resultNode = this.rightNode.preOrderSearch(num);
        }
        return resultNode;//不管找不找到 都会返回
    }

    /*
     * 先判断当前节点的左子节点是否为空 不为空 递归中序查找
     *   如果找到 则返回 如果没有找到 就和当前节点比较 如果是 返回当前节点 否则继续进行 右递归的中序查找
     *   如果右递归终须查找 找到就返回 否则返回null
     * */
    public HeroNode infixOrderSearch(int num) {
        //1.先判断当前节点的左子节点是否为空 不为空 向左递归中序查找
        HeroNode resultNode = null;
        if (this.leftNode != null) {
            resultNode = this.leftNode.infixOrderSearch(num);
        }
        //经过左递归中序遍历 如果找到 则直接返回
        if (resultNode != null) {
            return resultNode;
        }
        System.out.println("进入中序遍历");
        //如果没有找到 就和当前节点比较 如果是返回当前节点
        if (this.num == num) {
            return this;
        }
        //就和当前节点比较 如果不是 继续进行 右递归的中序查找
        if (this.rightNode != null) {
            resultNode = this.rightNode.infixOrderSearch(num);
        }
        return resultNode;
    }

    //后序遍历
    public HeroNode postOrderSearch(int num) {
        HeroNode resultNode = null;
        //判断当前节点的左子节点是否为空 如果不为空 左递归后序查找
        if (this.leftNode != null) {
            resultNode = this.leftNode.postOrderSearch(num);
        }

        if (resultNode != null) {//说明左子树遍历找到了
            return resultNode;
        }
        //如果左子树没有找到 则向右子树递归 后序遍历查找
        if (this.rightNode != null) {
            resultNode = this.rightNode.postOrderSearch(num);
        }
        if (resultNode != null) {//说明右子树遍历找到了
            return resultNode;
        }
        System.out.println("进入后序遍历");
        //如果左右子树都没有找到 则比较当前节点
        if (this.num == num) {
            return this;
        }
        return resultNode;
    }
}
