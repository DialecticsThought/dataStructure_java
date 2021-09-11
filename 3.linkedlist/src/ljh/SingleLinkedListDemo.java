package ljh;

import java.util.Stack;

public class SingleLinkedListDemo {
    public static void main(String[] args) {
        //进行测试
        //先创建节点
        HeroNode heroNode = new HeroNode(1, "宋江", "及时雨");
        HeroNode heroNode1 = new HeroNode(2, "汝俊逸", "玉麒麟");
        HeroNode heroNode2 = new HeroNode(3, "吴用", "智多星");
        HeroNode heroNode3 = new HeroNode(4, "林冲", "豹子头");
        //创建一个链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        //加入
/*        singleLinkedList.add(heroNode);
        singleLinkedList.add(heroNode1);
        singleLinkedList.add(heroNode2);
        singleLinkedList.add(heroNode3);*/
        singleLinkedList.addInOrder(heroNode);
        singleLinkedList.addInOrder(heroNode2);
        singleLinkedList.addInOrder(heroNode3);
        singleLinkedList.addInOrder(heroNode1);
        //测试 得到倒数第k个节点
        //System.out.println("测试 得到倒数第k个节点");
        //singleLinkedList.findNode(singleLinkedList.getHead(),2);
        //遍历
/*        singleLinkedList.list();
        //删除一个
        singleLinkedList.delete(1);
        //遍历
        singleLinkedList.list();
        //删除一个
        singleLinkedList.delete(4);
        //遍历
        singleLinkedList.list();*/
        //遍历
        singleLinkedList.list();
        //倒序打印
        System.out.println("倒序打印");
        singleLinkedList.reversePrint(singleLinkedList.getHead());
    }
}


//定义SingleLinkedList 来管理我们的英雄
class SingleLinkedList{
    //初始化一个头节点 头结点不要动 不存放具体的数据
    private HeroNode head = new HeroNode(0,"","");

    public HeroNode getHead() {
        return head;
    }

    public void setHead(HeroNode head) {
        this.head = head;
    }
    //添加节点到单链表
    /*
    思路，当不考虑编号顺序的时候
    1.找到当前链表的最后节点
    2.将最后的这个节点的next指向新的节点
    */
    public void add(HeroNode heroNode){
        //因为head节点不能动 遍历的话需要一个辅助节点
        //让tmp指向head = tmp被赋予head的引用
        HeroNode tmp = head;
        //1.遍历链表 找到最后
        while (true){
            //找到链表的最后 tmp的next域为空
            if(tmp.next == null){
                break;//跳出
            }
            //没有找到最后 tmp后移 继续遍历
            tmp = tmp.next;//tmp的next节点的地址给tmp
        }
        //退出while循环 tmp就指向了链表的最后
        //2.将最后的节点的next域设为新添加的英雄的地址
        tmp.next = heroNode;
    }

    //第二种方式在添加英雄时，根据排名将英雄插入到指定位置
    // (如果有这个排名，则添加失败，并给出提示)
    public void addInOrder(HeroNode heroNode){
        //因为头节点不能动 遍历的话需要一个辅助节点
        //单链表 利用tmp遍历到想加入的位置的前一个节点
        HeroNode tmp = head;
        //标志想要添加的编号是否存在 默认为false 如果要添加的编号已经存在了  就不能添加
        boolean flag = false;
        while (true){
            if (tmp.next == null){
                //说明tmp已经在链表的最后 直接添加
                break;
            }
            //如果遍历到的当前元素的后一个节点编号大于要插入的节点编号
            //说明要插入的节点编号 正处于遍历到的节点和下一个节点之间
            if(tmp.next.no > heroNode.no){
                break;
            }else if(tmp.next.no == heroNode.no){
                //说明heroNode的编号已经存在
                flag = true;
                break;
            }
            //都不成立的话 后移 继续遍历
            tmp = tmp.next;
        }
        //添加前 判断flag的值
        if(flag){//已经存在 不能插入
            System.out.printf("准备插入的英雄的编号%d已经存在 不能加入\n",heroNode.no);
        }else {
            //插入到链表中 tmp的后面
            //遍历到的节点的下一个节点地址赋给要添加的节点的next域
             heroNode.next = tmp.next;
             //再把遍历到的节点的下一个节点地址改成指向要添加的节点
             tmp.next = heroNode;
        }
    }
    //修改节点的信息 根据no编号来修改 no编号不能改 因为等于添加操作
    //1.根据 newHeroNode 的no来修改即可
    public void update(HeroNode newHeroNode){
        //判断是否为空
        if(head.next == null){
            System.out.println("链表为空");
            return;
        }
        //定一个辅助变量
        HeroNode tmp = head.next;
        boolean flag = false;//是否找到该节点
        while (true){
            //说明遍历完链表
            if(tmp == null){
                break;
            }
            if(tmp.no == newHeroNode.no){
                //找到
                flag = true;
                break;
            }
            //继续执行
            tmp = tmp.next;
        }
        //根据flag 判断是否找到要修改的节点
        if(flag){
            tmp.name = newHeroNode.name;
            tmp.nickname = newHeroNode.nickname;
        }else {
            //没有找到
            System.out.printf("没有找到编号%d的节点 不能修改\n",newHeroNode.no);
        }
    }

    //删除节点
    //思路
    //head不能动 因此需要一个tmp辅助接点找到带删除节点的前一个节点
    //比较时， tmp.next.no和需要删除的节点的no比较
    public void delete(int no){
        HeroNode tmp =head;
        boolean flag = false;//表示 是否找到删除节点
        while (true){
            //判断是否遍历到最后
            if(tmp.next == null){
                break;
            }
            //找到带删除节点的前一个节点
            if(tmp.next.no == no){
                flag = true;
                break;
            }
            //找不到继续遍历
            tmp = tmp.next;
        }
        //找到
        if(flag){
            //可以删除
            //把要删除的前一个节点的next域指向被删除节点的下一个节点
            tmp.next = tmp.next.next;
        }else {
            System.out.printf("要删除的%d节点不存在\n",no);
        }
    }

    //获取单链表的节点的个数（带头节点的话 需要不统计头结点）
    public static int getLength(HeroNode head){
        //判断空链表
        if(head.next == null){
            return 0;
        }
        //初始化长度
        int length = 0;
        //定义一个辅助的变量 不统计头结点
        HeroNode tmp =head.next;
        while (tmp != null){
            length++;
            //指向下一个节点
            tmp = tmp.next;
        }
        return length;
    }
    /*
      查找单链表的倒数第k个节点

    * 1.编写一个方法 接受head节点 同事接受一个index
    * 2.index 表示倒数第index个节点
    * 3.先把链表从头到尾遍历 得到链表的总的长度调用getLength()
      4.得到size后 我们从链表的第一个开始遍历到(size-index)个 就可以得到
      5.如果找到 就返回节点 否则返回null
    * */
    public static HeroNode findNode(HeroNode head, int index){
        //如果链表为空 就是返回null
        if(head.next == null){
            return null;
        }
        //第一次遍历得到链表的长度(节点个数)
        int size=getLength(head);
        //第二次遍历 size-index位置 就是我们倒数的第k个节点
        //先做一个index校验
        if(index <= 0 || index > size){
            return null;//找不到
        }
        //定义一个辅助变量 用来遍历 不算头节点
        HeroNode tmp = head.next;
        //for循环遍历到倒数的index
        /*
        * 假设有5个节点 找倒数第二个 也就是找第四个
        * index=2 size=5
        * 从0开始第4个就是size-index=3
        * */
        for (int i = 0; i < size - index;i++){
            tmp = tmp.next;
        }
        return tmp;
    }
    //将单链表进行翻转
    public static void reverseList(HeroNode head){
        //如果当前链表为空 只有一个节点 无需翻转 直接返回
        if(head.next == null || head.next.next == null){
            return;
        }
        //定一个辅助的指针变量 帮助我们遍历原来的链表
        HeroNode current = head.next;
        /*
        指向当前节点[current]的下一个节点
        当current指向的节点放入reverse链表的时候 可以通过next指针来回到原先的链表 防止断裂
        * */
        HeroNode next = null;
        HeroNode reverseHead =new HeroNode(0,"","");
        //遍历原来的链表，每遍历一个节点，就将其取出，并放在新的链表 reverseHead 的最前端
        while (current != null){//节点是空说明已经结束了
            next =current.next; //暂存当前节点的下一个节点 因为后面需要使用
            //将current的下一个节点指向新的链表的最前端 因为是从头结点开始的所以current.next
            current.next = reverseHead.next;
            reverseHead.next = current; //将 cur 连接到新的链表上
            current = next;//让 cur 后移
        }
        //将 head.next 指向 reverseHead.next , 实现单链表的反转
        head.next = reverseHead.next;

    }
    //可以利用栈这个数据结构 将各个节点压入栈中 然后利用栈的先进先出 实现逆序打印的效果
    public static void reversePrint(HeroNode head){
        //空链表不打印
        if(head.next == null){
            return;
        }
        //创建要给一个栈 把各个节点压入栈
        Stack<HeroNode> stack = new Stack<>();
        HeroNode current = head.next;
        //将链表的所有节点压入栈
        while (current != null){
            stack.push(current);
            current = current.next;//指针后移 压入下一个节点
        }
        //将栈中的节点进行打印 pop出栈
        while (stack.size() >0){
            System.out.println(stack.pop());
        }
    }
    //显示链表[遍历]
    public void list(){
        //判断链表是否为空
        if(head.next == null){
            System.out.println("链表为空");
            return;//直接结束
        }
        //因为头节点 不能动 需要一个辅助变量指向头结点来遍历
        HeroNode tmp = head.next;
        while (true){
            //判断是否链表到最后
            if(tmp == null){
                break;
            }
            //输出节点信息
            System.out.println(tmp);
            //将tmp后移 为输出下一个节点做准备
            tmp = tmp.next;
        }
    }
}

//定一个HeroNode 每一个HeroNode 对象就是一个节点
class HeroNode{
    public int no;
    public String name;
    public String nickname;
    public HeroNode next;//用来指向下一个节点

    public HeroNode() {
    }

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", next=" + next +
                '}';
    }
}
