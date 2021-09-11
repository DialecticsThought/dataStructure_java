package ljh;

import java.util.Scanner;

public class ArrayQueueDemo {
    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(4);
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
                    arrayQueue.getAll();
                    break;
                case 'a':
                    System.out.println("输出一个数");
                    int value = scanner.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g':
                    try {//因为有可能是空队列会发出异常所以try-catch
                        int res = arrayQueue.getQueue();
                        System.out.printf("取出的数据是%d\n",res);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try{
                        int head = arrayQueue.getHead();
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
class ArrayQueue{
    private int maxSize;//队列数组的最大容量
    private int front;//队头
    private int rear;//队尾
    private int[] arr;//队列

    public ArrayQueue() {
    }

    //创建队列类的构造器
    //arrMaxSize是传入的
    public ArrayQueue(int arrMaxSize){
        maxSize = arrMaxSize;
        arr = new int[arrMaxSize];
        front = -1;//初始时 指向队列头部的前一个位置
        rear = -1;//初始时 指向队列尾部（指向队列最后一个数据）
    }
    //判断队列是否满
    public boolean isFull(){
        //当尾部（的索引）等于最大长度-1时
        return rear == maxSize - 1;
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
        rear++;//尾部（索引）后移
        arr[rear] = n;//在尾部赋值
    }
    //获取队列的数据 出队列
    public int getQueue(){
        //判断队列是否为空
        if(isEmpty()){
            throw new RuntimeException("队列为空 不能取数据");
        }
        front++;//1.先让头指针后移 因为front本身指向对猎头的前一个位置
        return arr[front];//取出头指针指向的数据
    }
    //显示队列的所有数据
    public void getAll(){
        //判断队列是否为空
        if(isEmpty()){
            throw new RuntimeException("队列为空 不能取数据");
        }
        for(int i = 0;i < arr.length;i++){
            System.out.printf("arr[%d]%d\n",i,arr[i]);
        }
    }
    //显示队列的头数据 注意不是取数据
    public int getHead(){
        //判断队列是否为空
        if(!isEmpty()){
            throw new RuntimeException("队列为空 不能取数据");
        }
        return arr[front+1];//因为front本身指向对猎头的前一个位置
    }
}