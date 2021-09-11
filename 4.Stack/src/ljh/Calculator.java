package ljh;

public class Calculator {
    public static void main(String[] args) {
        String expression ="70+2*6-4";
        //创建两个栈 一个数栈 一个符号栈
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operationStack = new ArrayStack2(10);
        //定义需要的相关变量
        int index = 0;//用于扫描
        int num1 = 0;
        int num2 = 0;
        int operation = 0;
        double result = 0;
        //每次扫描的到的char保存进来
        char ch = ' ';
        String keepNum = "";//用于拼接 多位数
        //开始while循环扫描expression
        while (true){
            //依次 得到 expression 的每一个字符
            //expression.substring(index,index+1)返回的字符串只有一个字符
            ch = expression.substring(index,index+1).charAt(0);
            //判断ch是什么 做出相应的处理
            if(operationStack.isOperation(ch)){
                //如果是运算符
                //1. 判断当前的符号栈是否为空
                if(!operationStack.isEmpty()){
                /*
                 * 有操作符 比较
                 *     当前的操作符的优先级 <= 栈中的操作符的优先级
                 *           需要从数栈中pop出 2个数 符号栈pop出一个符号 运算后 再放回数栈 再把当前的操作符压入符号栈
                 *      当前的操作符的优先级 >= 栈中的操作符的优先级
                 *           将当前的操作符入符号栈
                * */
                    if(operationStack.priority(ch) <= operationStack.priority(operationStack.peek())){//当前的操作符的优先级 <= 栈中的操作符的优先级
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        operation = operationStack.pop();//从符号栈 得到的运算符号
                        result = numStack.cal(num1,num2,operation);
                        //把运算结果压入栈
                        numStack.push((int) result);
                        //把当前的操作符压入符号栈
                        operationStack.push(ch);
                    }else {//当前的操作符的优先级 >= 栈中的操作符的优先级
                        operationStack.push(ch);
                    }
                }else {//为空 直接入符号栈
                    operationStack.push(ch);
                }
            }else {//如果是数 直接入数栈
               // numStack.push(ch - 48);//因为 是char 里面存放的是ASCII
                /*
                * 当处理多位数时 不能发现是一个数就立即入栈 因为他可能是多位数
                * 在处理数字 需要想expression的表达式的index 后再看一位
                * 如果是数就进行扫描 如果是符号 在入栈
                * 需要定义一个字符串变量 用于拼接
                * */
                keepNum += ch;//拼接
                //如果ch已经是expression表达式的最后一个字符 就直接入栈
                if(index == expression.length() - 1){
                    numStack.push(Integer.parseInt(keepNum));
                }else {
                    //判断下一个字符是不是数字 如果是数字 继续扫描 如果是运算符 则入栈
                    //index本身的值不用变 只是看一下后一位的字符
                    if(operationStack.isOperation(expression.substring(index+1,index+2).charAt(0))){
                        //如果后一位是操作符
                        numStack.push(Integer.parseInt(keepNum));
                        //清空keepNum☆☆☆☆☆☆☆
                        keepNum = "";
                    }
                }
            }
            //让 index + 1 并判断是否扫描到expression到最后了
            index++;
            if( index == expression.length()){
                break;
            }
        }
        //表达式扫描完毕 顺序地从数栈和符号栈中pop出相应的数和符号 并运行
        while (true){
            //如果符号栈 为空 则计算到最后的结果 数栈只有一个数字（结果）
            if(operationStack.isEmpty()){
                break;
            }
            //不空 顺序地从数栈和符号栈中pop出相应的数和符号 并运行
            num1 = numStack.pop();
            num2 = numStack.pop();
            operation = operationStack.pop();//从符号栈 得到的运算符号
            result = numStack.cal(num1,num2,operation);
            //把运算结果压入栈
            numStack.push((int) result);
        }
        System.out.printf("表达式%s = %d",expression,numStack.pop());
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
 *                           需要从数栈中pop出 2个数 符号栈pop出一个符号 运算后 再放回数栈 再把当前的操作符压入符号栈
 *                      当前的操作符的优先级 >= 栈中的操作符的优先级
 *                            将当前的操作符入符号栈
 * 表达式扫描完毕 顺序地从数栈和符号栈中pop出相应的数和符号 并运行
 * 最后在数栈中只有一个数字 就是 表达式的运算结果
 * */
class ArrayStack2{
    private int maxSize;//栈的大小
    private int[] stack;//数组 来模拟战
    private int top = -1;//top表示栈顶

    //构造器
    public ArrayStack2(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    public ArrayStack2() {

    }

    //栈满
    public boolean isFull() {
        //最大的索引 是 maxSize - 1
        return top == maxSize - 1;
    }

    //栈空
    public boolean isEmpty() {
        return top == -1;
    }

    //返回栈顶的值 但是不pop
    public int peek(){
        return stack[top];
    }

    //入栈
    public void push(int value) {
        //判断栈满了吗
        if (isFull()) {
            System.out.println("栈满");
            return;
        }
        //top从-1开始
        top++;
        stack[top] = value;
    }

    //出栈pop 将栈顶的数据返回
    public int pop() {
        //判断栈是否空
        if (isEmpty()) {
            throw new RuntimeException("栈空,没有数据");
        }
        //返回一个值
        int value = stack[top];
        top--;
        return value;
    }

    //遍历栈 从栈顶开始显示数据
    public void list() {
        //判断栈空
        if (isEmpty()) {
            throw new RuntimeException("栈空,没有数据");
        }
        //从栈顶开始显示
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }
    }

    //返回运算符的优先级 优先级有程序员确定 优先级用数字表示
    //数字越大 优先级越高
    //假设 只有加减乘除
    public int priority(int operation) {
        if (operation == '*' || operation == '/') {
            return 1;
        } else if (operation == '+' || operation == '-') {
            return 0;
        } else {
            return -1;
        }
    }

    //判断是不是一个运算符
    public boolean isOperation(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    //计算方法
    public double cal(int num1, int num2, int operation) {
        double result = 0.0;//存储计算结果
        switch (operation) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
            default:
                break;
        }
        return result;
    }
}