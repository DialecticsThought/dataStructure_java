package ljh;

public class Josephu {
    public static void main(String[] args) {
        //测试 构建环形链表和遍历
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(5);
        circleSingleLinkedList.showBoy();
        //测试小孩出圈
        circleSingleLinkedList.countBoy(1,2,5);
    }
}
/*
* 创建单向的环形链表思路：
*   1.创建第一个节点 first指向该节点 并形成环形
*   2.后面当我们没创建一个新的节点 就把节点 加入到已有的环形链表中
* 遍历该链表：
*   1.先让一个辅助指针变量curBoy 方便删除节点和遍历 一开始指向first节点 first节点不能动
*   2.通过while循环遍历该环形链表
*   3.当curBoy = first时 结束
*
* */
//创建环形单向列表
class CircleSingleLinkedList{
    //创建一个first节点 当前没有编号 随便写一个
    private Boy first = new Boy(1);
    //添加小孩节点 构建一个环形链表
    public void addBoy(int nums){
        //nums做一个数据校验
        if(nums < 1){
            System.out.println("nums的值不正确");
            return;
        }
        //辅助指针 帮助构建环形链表
        //curBoy指向当前的最后一个节点
        Boy curBoy = null;
        //使用for来创建我们的环形链表
        for(int i = 1;i <= nums;i++){
            //根据编号 创建小孩节点
            Boy boy = new Boy(i);
            //如果是第一个小孩
            if(i == 1){
                first = boy;
                //构成一个环 i=1 时 自己指向自己
                first.setNext(first);
                //因为first不能动 让curBoy辅助 开始时指向第一个小孩
                curBoy = first;
            }else {
                //让curBoy.next域 原来指向头节点 -->指向 新添加的节点
                curBoy.setNext(boy);
                //让新添加的boy节点的next域指向头节点
                boy.setNext(first);
                //再让curboy指向现在的最后一个节点
                curBoy = boy;
            }
        }
    }
    /*
    * startNo 从第几个小孩开始数数
    * countNum 表示数几下
    * nums 表示最初有多少小孩在圈中
    *
    * */
    //根据用户的输入 计算出小孩的出圈顺序
    public void countBoy(int startNo,int countNum, int nums){
        //先对数据校验
        if(first == null || startNo < 1 || startNo > nums){
            System.out.println("参数输入有误 请重新输入");
            return;
        }
        //创建给辅助指针 帮助完成小孩出圈（遍历和删除）
        Boy helper = first;
        //辅助指针helper 实现应该指向环形链表的最后一个节点
        while (true){
            //说明 helper指向最后一个小孩的节点
            if(helper.getNext() == first){
                break;
            }
            helper = helper.getNext();
        }
        //小孩报数前 先让first和helper 移动k-1次
        for(int j = 0;j < startNo-1;j++){
            first = first.getNext();
            helper = helper.getNext();
        }
        //当小孩报数时 让first和helper 指针同事移动 m-1次 然后出圈
        //这里是一个循环操作 知道圈中只有一个节点
        while (true){
            //圈中只有一个节点
            if(helper == first){
                break;
            }
            //让给first 和helper指针 同时移动countNum-1
            for(int j = 0;j < countNum - 1;j++){
                first = first.getNext();
                helper = helper.getNext();
            }
            //此时first指向的节点就是出圈的小孩的节点
            System.out.printf("小孩%d出圈\n",first.getNo());
            //此时first指向的小孩出圈 就是让first指向下一个节点
            first =first.getNext();
            //helper跟进
            helper.setNext(first);
        }
        System.out.printf("最后留在圈中的小孩编号%d \n",first.getNo());
    }

    //遍历环形链表
    public void showBoy(){
        //链表是否为空
        if(first == null){
            System.out.println("链表里没有小孩");
            return;
        }
        //first不能动 辅助指针来完成遍历
        Boy curBoy = first;
        while (true){
            System.out.printf("小孩的编号%d \n",curBoy.getNo());
            if(curBoy.getNext() == first){
                //说明遍历完毕
                break;
            }
            //辅助指针后移 用于遍历后面的
            curBoy = curBoy.getNext();
        }
    }
}

//创建一个boy类 表示一个节点
class Boy{
    private int no;//编号
    private Boy next;//指向下一个小孩 节点 默认是null
    public Boy(int no){
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}