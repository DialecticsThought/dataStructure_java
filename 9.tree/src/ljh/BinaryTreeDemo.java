package ljh;

public class BinaryTreeDemo {
    public static void main(String[] args) {
        //先创建一个二叉树
        BinaryTree binaryTree = new BinaryTree();
        //创建所需要的节点
        HeroNode heroNode1 = new HeroNode(1, "宋江");
        HeroNode heroNode2 = new HeroNode(2, "吴用");
        HeroNode heroNode3 = new HeroNode(3, "卢俊义");
        HeroNode heroNode4 = new HeroNode(4, "林冲");
        HeroNode heroNode5 = new HeroNode(5, "关胜");
        binaryTree.setRoot(heroNode1);

        heroNode1.setLeftNode(heroNode2);

        heroNode1.setRightNode(heroNode3);

        heroNode3.setRightNode(heroNode4);

        heroNode3.setLeftNode(heroNode5);
        //测试
        System.out.println("前序遍历");
        binaryTree.preOrder();

/*        System.out.println("中序遍历");
        binaryTree.infixOrder();

        System.out.println("后序遍历");
        binaryTree.postOrder();*/
        //应该是4次
/*        System.out.println("前序遍历查找");
        HeroNode heroNode = binaryTree.preOrderSearch(5);
        if(heroNode!=null){
            System.out.printf("找到了 信息为 num=%d name=%s",heroNode.getNum(),heroNode.getName());
        }else {
            System.out.println("没有找到");
        }*/
        //3次遍历
/*        System.out.println("中序遍历查找");
        HeroNode resultNode = binaryTree.infixOrderSearch(5);
        if(resultNode!=null){
            System.out.printf("找到了 信息为 num=%d name=%s",resultNode.getNum(),resultNode.getName());
        }else {
            System.out.println("没有找到");
        }*/
/*        //2次遍历
        System.out.println("后序遍历查找");
        HeroNode resultNode1 = binaryTree.postOrderSearch(5);
        if (resultNode1 != null) {
            System.out.printf("找到了 信息为 num=%d name=%s", resultNode1.getNum(), resultNode1.getName());
        } else {
            System.out.println("没有找到");
        }*/
        binaryTree.deleteNode(5);
        System.out.println("删除后 前序遍历");
        binaryTree.preOrder();
    }
}

//定义二叉树
class BinaryTree {
    private HeroNode root;

    public HeroNode getRoot() {
        return root;
    }

    public void setRoot(HeroNode root) {
        this.root = root;
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

    //删除节点
    public void deleteNode(int num) {
        if (root != null) {
            //判断root就是被删的节点
            if (root.getNum() == num) {
                root = null;//置空
            } else {
                root.deleteNode(num);
            }
        } else {
            System.out.println("空数不能删除");
        }
    }
}

//创建HeroNode节点
class HeroNode {
    private int num;
    private String name;
    private HeroNode leftNode;//左节点 默认为null
    private HeroNode rightNode;//右节点 默认为null

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
