package ljh;

import java.util.Scanner;

public class CircleArrayQueueDemo {
    public static void main(String[] args) {

        //最大空间是4个 实际有效数据空间是3 因为预留一个空间来防止越界
        CircleArrayQueue circleArrayQueue = new CircleArrayQueue(4);
        char key=' ';//接收用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        //输出一个菜单
        while (loop){
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据到队列");
            System.out.println("g(get):从队取出数据");
            System.out.println("h(head):查看队列头的数据");
            //接受一个字符串的第一个字符
            key = scanner.next().charAt(0);
            switch (key){
                case 's':
                    circleArrayQueue.showQueue();
                    break;
                case 'a':
                    System.out.println("输出一个数");
                    int value = scanner.nextInt();
                    circleArrayQueue.addQueue(value);
                    break;
                case 'g':
                    try {//因为有可能是空队列会发出异常所以try-catch
                        int res = circleArrayQueue.getQueue();
                        System.out.printf("取出的数据是%d\n",res);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try{
                        int head = circleArrayQueue.getHead();
                        System.out.printf("取出的数据是%d\n",head);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e'://退出程序
                    scanner.close();
                    loop = false;//跳出循环
                default:
                    break;
            }
        }
        System.out.println("程序退出");
    }

}
//使用数组模拟队列
class CircleArrayQueue{
    private int maxSize;//队列数组的最大容量
    //初始时 指向队列头部 不同于普通队列 不是头部的前一个位置
    private int front = 0;
    //初始时 rear 指向队列的最后一个元素的后一个位置. 因为希望空出一个空间做为约定（不是指向队列最后一个数据）
    private int rear = 0;
    private int[] arr;//队列

    public CircleArrayQueue() {
    }

    //创建队列类的构造器
    //arrMaxSize是传入的
    public CircleArrayQueue(int arrMaxSize){
        maxSize = arrMaxSize;
        arr = new int[maxSize];
    }
    //判断队列是否满
    public boolean isFull(){
        //false是没满 true是满了
        return (rear + 1) % maxSize == front;
    }
    //判断队列是否为空
    public boolean isEmpty(){
        return rear == front;
    }
    //添加数据到队列
    public void addQueue(int n){
        //1 判断队列是否满
        if (isFull()){
            System.out.println("队列满 不能加入数据");
            return;
        }
        //直接赋值 不同于普通的队列
        arr[rear] = n;
        //将rear后移一位时 必须考虑取模 防止越界并且rear是队尾的之后一个位置 可以通过画图更清晰
        rear = (rear + 1) % maxSize;
    }
    //获取队列的数据 出队列
    public int getQueue(){
        //判断队列是否为空
        if(isEmpty()){
            throw new RuntimeException("队列为空 不能取数据");
        }
        /*
        普通队列
        *front++;//1.先让头指针后移 因为front本身指向对猎头的前一个位置
         return arr[front];//取出头指针指向的数据
        * */
        //需要分析出front是指向队列元素的第一个元素
        //1.先把front对应的值保留在临时变量
        int value = arr[front];
        //2.将front后移 考虑取模 防止越界
        front = (front + 1) % maxSize;
        //3.将临时保存的变量返回
        return value;

    }
    //显示队列的所有数据
    public void showQueue(){
        //判断队列是否为空
        if(isEmpty()){
            throw new RuntimeException("队列为空 不能取数据");
        }
        //从front开始遍历 遍历多少个元素
        for(int i = front;i < front + size();i++){
            //i % maxSize 是因为有可能超过数组的大小 防止越界
            System.out.printf("arr[%d]%d\n",i % maxSize,arr[i % maxSize]);
        }
    }
    //求出当前队列的有效数据的个数
    public int size(){
        /*
        * rear=2
        * front=1
        * maxSize=3
        * */
        return (rear + maxSize - front) % maxSize;
    }
    //显示队列的头数据 注意不是取数据
    public int getHead(){
        //判断队列是否为空
        if(!isEmpty()){
            throw new RuntimeException("队列为空 不能取数据");
        }
        return arr[front];//因为front本身指向队列的第一个位置
    }
}