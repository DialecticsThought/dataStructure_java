package ljh;

public class AvlTreeDemo {
    public static void main(String[] args) {
        int[] arr = {4, 3, 6, 5, 7, 8};
        int[] arr2 = {10, 7,11, 6, 8, 9};
        AVLTree avlTree = new AVLTree();
        //添加节点
/*        for (int i = 0; i < arr.length; i++) {
            avlTree.add(new Node(arr[i]));
        }*/
        //添加节点
        for (int i = 0; i < arr2.length; i++) {
            avlTree.add(new Node(arr2[i]));
        }
        //中序遍历
        avlTree.infixOrder();
        System.out.println("没有旋转之前的总高度" + avlTree.getRoot().height());
        System.out.println("没有旋转之前的左子树高度" + avlTree.getRoot().leftHeight());
        System.out.println("没有旋转之前的右子树高度" + avlTree.getRoot().rightHeight());
        System.out.println("当前根节点 = " + avlTree.getRoot());
        System.out.println("当前根节点的左子节点 = " + avlTree.getRoot().getLeft());
        System.out.println("当前根节点的左子节点 = " + avlTree.getRoot().getRight());
        avlTree.infixOrder();
    }
}

//创建avlTree
class AVLTree {
    private Node root;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void add(Node node) {
        if (root == null) {
            root = node;//空树的话 直接当做根节点
        } else {
            root.add(node);
        }
    }

    //中序遍历
    public void infixOrder() {
        if (root == null) {
            System.out.println("这是一个空数");
        } else {
            root.infixOrder();
        }
    }

    //查找要删除的节点
    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    public Node searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }
    }

    /*
     * node传入的节点 传入的是目标节点的右子节点 （当做二叉排序树的根节点）<=> targetNode.right
     * 返回的以node为根节点的二叉排序树的最小节点的值
     * */
    public int delRightTreeMin(Node node) {
        Node target = node;
        //循环查找右子树的左节点 就会找到最小值（右子树的最左下角）
        while (target.left != null) {
            target = target.left;
        }
        //此时target指向了最小节点
        //删除最小节点(一定是叶子结点 根据二叉排序树的性质)
        delNode(target.value);
        return target.value;
    }


    //删除节点
    public void delNode(int value) {
        if (root == null) {
            return;
        } else {
            //1.需求先去找到要删除的节点 targetNode
            Node targetNode = search(value);
            //如果没有找到要删除的节点
            if (targetNode == null) {
                return;//直接返回
            }
            //如果 targetNode没有左右子节点
            if (root.left == null && root.right == null) {
                root = null;
                return;
            }
            //2.需求先去找到要删除的节点 targetNode的父节点
            Node parent = searchParent(value);
            //如果要删除的节点是叶子结点
            if (targetNode.left == null && targetNode.right == null) {
                //判断targetNode是父节点的左子节点 还是右子节点
                if (parent.left != null && parent.left.value == value) {
                    parent.left = null;//左子节点置空 <=>删除targetNode
                } else if (parent.right != null && parent.right.value == value) {
                    parent.right = null;//右子节点置空 <=>删除targetNode
                }
            } else if (targetNode.left != null && targetNode.right != null) {//删除有两颗子树的节点
                int minVal = delRightTreeMin(targetNode.right);
                targetNode.value = minVal;//重置targetNode相当于删除
            } else {//删除只有一个子树的节点
                //删除只有一个左子树的节点
                if (targetNode.left != null) {
                    //targetNode是 parent的左子节点
                    if (parent.left.value == value) {
                        //targetNode.left是因为targetNode是 删除只有一个左子树的节点
                        //parent.left是因为 targetNode是 parent的左子节点
                        parent.left = targetNode.left;//让被删除节点的左子节点接到父节点的左子节点的位置上
                    } else {//targetNode是 parent的右子节点
                        //targetNode.left是因为targetNode是 删除只有一个左子树的节点
                        //parent.right是因为 targetNode是 parent的右子节点
                        parent.right = targetNode.left;
                    }
                } else { //删除只有一个右子树的节点
                    //targetNode是 parent的左子节点
                    if (parent.left.value == value) {
                        //targetNode.right是因为targetNode是 删除只有一个右子树的节点
                        //parent.left是因为 targetNode是 parent的左子节点
                        parent.left = targetNode.right;
                    } else {//targetNode是 parent的右子节点
                        //targetNode.right是因为targetNode是 删除只有一个右子树的节点
                        //parent.right是因为 targetNode是 parent的右子节点
                        parent.right = targetNode.right;
                    }
                }
            }
        }
    }
}

class Node {
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

    //返回当前节点的高度 以该节点为根节点的数的高度
    public int height() {
        //+1的原因要算上当前的节点 Math.max取两个数的最大的数
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    //返回左子树的高度
    public int leftHeight() {
        if (left == null) {
            return 0;
        }
        return left.height();
    }

    //返回右子树的高度
    public int rightHeight() {
        if (right == null) {
            return 0;
        }
        return right.height();
    }

    //左旋转
    public void leftRotate() {
        //创建新的节点 值是当前根节点的值
        Node newNode = new Node(value);
        //把新的节点的左子节点设置成当前根节点的左子节点
        newNode.left = left;
        //把新的节点的右子节点设置成当前根节点的右子节点的左子节点
        newNode.right = right.left;
        //把当前根节点的值替换成当前根节点的右子节点的值
        value = right.value;
        //把当前根节点的右子节点设置成当前根节点的右子树的右子树
        right = right.right;
        //把当前的根节点的左子节点 设置成指向newNode
        left = newNode;
    }

    //右旋转
    private void rightRotate() {
        //创建新的节点 值是当前根节点的值
        Node newNode = new Node(value);
        //把新节点的右子节点设置 当前根节点的右子节点
        newNode.right = right;
        //把当前根节点的左子树设置为当前根节点的左子节点的右子节点
        newNode.left = left.right;
        //当前根节点的值 = 根节点 左子节点的值
        value = left.value;
        //当前根节点的左子节点设置成 左子节点的左子节点
        left = left.left;
        //当前根节点的右子节点设置成新的节点
        right = newNode;
    }

    //查找要删除的节点
    /*
     * int value希望删除节点的值
     * 返回找到的节点 找不到 返回null
     * */
    public Node search(int value) {
        if (value == this.value) {
            return this;
        } else if (value < this.value) {//要找的值得节点< 现在的节点的值 根据二叉排序树的特性 向左递归
            //判断左子节点是否为空
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);//向左递归
        } else {//要找的值得节点> 现在的节点的值 根据二叉排序树的特性 向左递归
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
    public Node searchParent(int value) {
        //如果当前节点 的左子节点不为空 并且 左子节点的值就是要找的值 ||当前节点 的右子节点不为空 并且 右子节点的值就是要找的值
        if ((this.left != null && this.left.value == value)
                || (this.right != null && this.right.value == value)) {
            return this;
        } else {
            //如果查找的值小于当前节点的值 并且当前节点的左子节点不为空
            //向左子树递归
            if (value < this.value && this.left != null) {
                return this.left.searchParent(value);
            } else if (value >= this.value && this.right != null) {
                //如果查找的值>=当前节点的值 并且当前节点的右子节点不为空
                //向右子树递归
                return this.right.searchParent(value);
            } else {
                return null;//没有父节点 eg:要找的是root
            }
        }
    }

    //添加节点的方法
    public void add(Node node) {
        //递归的形式添加节点 需要满足二叉排序树的要求
        if (node == null) {
            return;
        }
        //判断传入的节点的值 和当前子树的根节点的值关系
        if (node.value < this.value) {
            //如果当前节点的左子节点为null
            if (this.left == null) {
                this.left = node;
            } else {//左子树不为空
                //递归的向左子树添加节点
                //因为左子节点还要判断被加入的节点的大小 判断路径
                this.left.add(node);
            }
        } else {//添加的节点的值大于当前节点的值
            if (this.right == null) {
                this.right = node;
            } else {
                this.right.add(node);
            }
        }

        //当添加玩一个节点后 如果 右子树的高度 - 左子树的高度 >1
        if (rightHeight() - leftHeight() > 1) {
            //leftRotate();
            //根节点的右子节点的左子树高度>根节点的右子节点的右子树高度
            if (left != null && right.leftHeight() > right.rightHeight()) {
                //先对根节点的右子节点进行右旋转
                right.rightRotate();
                //对根节点进行坐旋转
                leftRotate();
            } else {
                leftRotate();
            }
            return;//防止执行下面的if语句
        }
        if (leftHeight() - rightHeight() > 1) {
            //rightRotate();
            //根节点的左子节点的右子树高度>根节点的左子节点的左子树高度
            if (right != null && left.rightHeight() > left.leftHeight()) {
                //先对根节点的左子节点所在的子树进行左旋转
                left.leftRotate();
                //再针对根节点右旋转
                rightRotate();
            } else {//直接右旋转
                rightRotate();
            }
        }
    }

    //中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }
}