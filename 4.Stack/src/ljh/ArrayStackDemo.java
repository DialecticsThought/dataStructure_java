package ljh;

import java.util.Scanner;

public class ArrayStackDemo {
    public static void main(String[] args) {
        //测试arrayStack是否正确
        //创建一个ArrayStack表示栈
        ArrayStack arrayStack = new ArrayStack(4);
        String key="";
        boolean flag = true;//控制是否控制菜单
        Scanner scanner = new Scanner(System.in);
        while (flag){
            System.out.println("show：表示显示栈");
            System.out.println("exit：表示退出程序");
            System.out.println("push：表示添加数据到栈（入栈）");
            System.out.println("pop：表示从栈中取数据（出栈）");
            System.out.println("请输入您的选择");
            key = scanner.next();
            switch (key){
                case "show":
                    arrayStack.list();
                    break;
                case "push":
                    System.out.println("请输入一个数：");
                    int value = scanner.nextInt();
                    arrayStack.push(value);
                    break;
                case "pop":
                    try {
                        int pop = arrayStack.pop();
                        System.out.printf("出栈的数据是%d\n",pop);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    //防止资源泄露
                    scanner.close();
                    flag = false;
                    break;
                default:
                    break;
            }
        }
    }
}
/*
* 通过一个index（索引） 来便利我们的表达式
* 发现数字---->直接入栈
* 发现符号
*     如果当前的符号栈
*                 为空 ---->直接入栈
*                 有操作符 比较
*                      当前的操作符的优先级 <= 栈中的操作符的优先级
*                           需要从数栈中pop出 2个数 符号栈pop出一个符号 运算后 再放回数栈
*                      当前的操作符的优先级 >= 栈中的操作符的优先级
*                            将当前的操作符入符号栈
* 表达式扫描完毕 顺序地从数栈和符号栈中pop出相应的数和符号 并运行
* 最后在数栈中只有一个数字 就是 表达式的运算结果
* */
class ArrayStack{
    private int maxSize;//栈的大小
    private int[] stack;//数组 来模拟战
    private int top = -1;//top表示栈顶
    //构造器
    public ArrayStack(int maxSize){
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }
    //栈满
    public boolean isFull(){
        //最大的索引 是 maxSize - 1
        return top == maxSize - 1;
    }
    //栈空
    public boolean isEmpty(){
        return top == -1;
    }
    //入栈
    public void push(int value){
        //判断栈满了吗
        if(isFull()){
            System.out.println("栈满");
            return;
        }
        //top从-1开始
        top++;
        stack[top] = value;
    }
    //出栈pop 将栈顶的数据返回
    public int pop(){
        //判断栈是否空
        if(isEmpty()){
            throw new RuntimeException("栈空,没有数据");
        }
        //返回一个值
        int value = stack[top];
        top--;
        return value;
    }
    //遍历栈 从栈顶开始显示数据
    public void list(){
        //判断栈空
        if(isEmpty()){
            throw new RuntimeException("栈空,没有数据");
        }
        //从栈顶开始显示
        for (int i = top; i >= 0 ;i--){
            System.out.printf("stack[%d]=%d\n",i,stack[i]);
        }
    }

}