package ljh;

public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        //进行测试
        //先创建节点
        HeroNode2 heroNode = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 heroNode1 = new HeroNode2(2, "汝俊逸", "玉麒麟");
        HeroNode2 heroNode2 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 heroNode3 = new HeroNode2(4, "林冲", "豹子头");
        //创建一个链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
/*        doubleLinkedList.add(heroNode);
        doubleLinkedList.add(heroNode1);
        doubleLinkedList.add(heroNode2);
        doubleLinkedList.add(heroNode3);*/
        doubleLinkedList.addInOrder(heroNode3);
        doubleLinkedList.addInOrder(heroNode2);
        doubleLinkedList.addInOrder(heroNode1);
        doubleLinkedList.addInOrder(heroNode);
        //遍历
        doubleLinkedList.list();
        //修改
        HeroNode2 newHeroNode =new HeroNode2(4,"公孙胜","入云龙");
        doubleLinkedList.update(newHeroNode);
        //遍历
        doubleLinkedList.list();
        //删除
        doubleLinkedList.delete(3);
        System.out.println("显示出删除后的list");
        //遍历
        doubleLinkedList.list();
    }
}
//创建一个双向链表
class DoubleLinkedList{
    //初始化一个头节点 头结点不要动 不存放具体的数据
    private HeroNode2 head = new HeroNode2(0,"","");
    public HeroNode2 getHead() {
        return head;
    }

    public void setHead(HeroNode2 head) {
        this.head = head;
    }
    //添加节点到单链表
        /*
    思路，当不考虑编号顺序的时候
    1.找到当前链表的最后节点
    2.将最后的这个节点的next指向新的节点
    */

    public void add(HeroNode2 heroNode){
        //因为head节点不能动 遍历的话需要一个辅助节点
        //让tmp指向head = tmp被赋予head的引用
        HeroNode2 tmp = head;
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
        //因为heroNode是当前最后一个节点 pre是存放前一个节点的地址
        heroNode.pre = tmp;
    }

    //第二种方式在添加英雄时，根据排名将英雄插入到指定位置
    // (如果有这个排名，则添加失败，并给出提示)
    public void addInOrder(HeroNode2 heroNode){
        //因为头节点不能动 遍历的话需要一个辅助节点
        //单链表 利用tmp遍历到想加入的位置的前一个节点
        HeroNode2 tmp = head;
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
            //如果不是最后一个节点的话
            if(tmp.next != null){
                //让当前节点的下一个节点的pre指向被添加的节点
                tmp.next.pre = heroNode;
            }
            //再把遍历到的节点的下一个节点地址改成指向要添加的节点
            tmp.next = heroNode;
            //让被添加的节点pre指向当前的tmp
            heroNode.pre=tmp;
        }
    }

    //修改一个节点的内容 双向链表的节点和单向链表的方式一样
    //修改节点的信息 根据no编号来修改 no编号不能改 因为等于添加操作
    //1.根据 newHeroNode 的no来修改即可
    public void update(HeroNode2 newHeroNode){
        //判断是否为空
        if(head.next == null){
            System.out.println("链表为空");
            return;
        }
        //定一个辅助变量
        HeroNode2 tmp = head.next;
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

    /*单向链表 删除节点 思路
    head不能动 因此需要一个tmp辅助接点找到带删除节点的前一个节点
    比较时， tmp.next.no和需要删除的节点的no比较
    双向链表 删除节点 思路
    对于双向链表 可以直接找到要删除的节点
    找到后 自我删除即可
    */
    public void delete(int no){
        HeroNode2 tmp =head;
        boolean flag = false;//表示 是否找到删除节点
        //说明是空链表
        if(head.next == null){
            return;
        }
        while (true){
            //判断是否遍历到最后
            //如果等于null的话说明已经指向了最后一个节点的next节点了 虽然是空的
            if(tmp == null){
                break;
            }
            //找到带删除节点的前一个节点
            if(tmp.no == no){
                flag = true;
                break;
            }
            //找不到继续遍历
            tmp = tmp.next;
        }
        //找到
        if(flag){
            /*可以删除
            //把要删除的前一个节点的next域指向被删除节点的下一个节点
            tmp.next = tmp.next.next;*/
            //让当前节点的上一个节点的next域指向当前节点的下一个节点
            tmp.pre.next = tmp.next;
            //判断 当前的节点是不是最后一个节点
            if(tmp.next != null){
                //让当前节点的下一个节点的pre域指向当前节点的上一个节点
                tmp.next.pre = tmp.pre;
            }
        }else {
            System.out.printf("要删除的%d节点不存在\n",no);
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
        HeroNode2 tmp = head.next;
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
class HeroNode2 {
    public int no;
    public String name;
    public String nickname;
    public HeroNode2 next;//用来指向下一个节点
    public HeroNode2 pre;//用来指向前一个节点

    public HeroNode2() {
    }

    public HeroNode2(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\''+
                '}';
    }
}