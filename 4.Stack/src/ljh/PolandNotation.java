package ljh;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PolandNotation {
    /*
    * 从左至右扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，
    * 用运算符对它们做相应的计算（次顶元素 和 栈顶元素），并将结果入栈；
    * 重复上述过程直到表达式最右端，最后运算得出的值即为表达式的结果
    * */
    public static void main(String[] args) {
        //中缀表达式转换成后缀表达式的功能
        //1. 1+((2+3)4)-5 => 1 2 3 + 4 * + 5 -
        //2.因为直接对str操作 不方便 因此将 “1+((2+3)4)-5” =>中缀表达式对应的Arraylist
        //3.把 ArrayList 里的字符串变成 1 2 3 + 4 * + 5 -
        String  expression = "1+((2+3)*4)-5";
        List<String> list = toInfixExpressionList(expression);
        System.out.println(list);

/*        //定义一个逆波兰表达式
        //(3+4)*5-6 => 3 4 + 5 * 6 -
        //为了方便 逆波兰表达式的数字 和 符号 使用空格隔开
        //4 * 5 - 8 + 60 + 8 / 2 ==> 4 5 * 8 - 60 + 8 2 / +
        String suffixExpression = "3 4 + 5 * 6 - ";
        String suffixExpression2 = "4 5 * 8 - 60 + 8 2 / +";
        //1.先将 表达式 放入ArrayList中 方便扫描遍历
        List<String> listString = getListString(suffixExpression);
        System.out.println(listString);
        //2. 将ArrayList 传给一个方法 这个方法配合栈 完成计算
        int calculate = calculate(listString);
        System.out.println(calculate);*/

    }
    /*
    * 1.初始化两个栈：运算符栈s1和储存中间结果的栈s2；
      2.从左至右扫描中缀表达式；
      3.遇到操作数时，将其压s2；
      4.遇到运算符时，比较其与s1栈顶运算符的优先级：
            (1)如果s1为空，或栈顶运算符为左括号“(”，则直接将此运算符入栈；
            (2)否则，若优先级比栈顶运算符的高，也将运算符压入s1；
            (3)否则，将s1栈顶的运算符弹出并压入到s2中，再次转到(4-1)与s1中新的栈顶运算符相比较；
    * 5.遇到括号时：
    *       (1) 如果是左括号“(”，则直接压入s1
    *       (2) 如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
      6.重复步骤2至5，直到表达式的最右边
      7.将s1中剩余的运算符依次弹出并压入s2
      8.依次弹出s2中的元素并输出，结果的逆序即为中缀表达式对应的后缀表达式

    * */
    public static List<String> parseSuffixExpressionList(List<String> ls){
        //定两个栈
        Stack<String> stack = new Stack<>();//符号栈
        //因为s2这个栈 在整个过程 没有pop操作 后期还要逆序输出
        //stack2 可以 ArrayList替代
        ArrayList<String> stack2 = new ArrayList<>();
        //遍历ls
        for (String item : ls){
            //3.如果是一个数 加入s2
            if(item.matches("\\d+")){
                //3.遇到操作数时，将其压s2
                stack2.add(item);
            }else if(item.equals("(")){
                //5.如果是左括号“(”，则直接压入s1
                stack.push(item);

            }else if(item.equals(")")){
                //5.(2) 如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
                while (!stack.peek().equals("(")){
                    //只要栈顶不是“(” 一直添加s1弹出的数据
                    stack2.add(stack.pop());
                }
                stack.pop();// 将 "(" 弹出 s1 栈 消除一对括号
            }else {
                /* 4.遇到运算符时，比较其与s1栈顶运算符的优先级：
                        item 的优先级 <= s1栈顶 的运算符
                            将s1栈顶的运算符弹出并压入到s2中，再次转到(4-1)与s1中新的栈顶运算符相比较；
                * */
                //问题： 缺少优先级高低的方法
                while (stack.size() != 0 && Operation.getValue(stack.peek()) >= Operation.getValue(item)){
                    stack2.add(stack.pop());
                }
                //还要把item压入栈
                stack.push(item);
            }
        }
        //7.将s1中剩余的运算符依次弹出并压入s2
        while (stack.size() != 0){
            stack2.add(stack.pop());
        }
        //因为存放到ArrayList list是有序的 只有直接输出就可以了
        return stack2;
    }



    //将中最表达式转成对应的list
    // s = "1+((2+3)*4)-5"
    public static List<String> toInfixExpressionList(String s){
        //定义一个list 存放中缀表达式对应的内容
        List<String> ls =new ArrayList<String>();
        //指针 用于遍历 中缀表达式字符串
        int i = 0;
        String str;//用于多位数的拼接
        char c;//每次遍历到的字符 就放到c
        while (i < s.length()){
            //如果c是一个非数字 加入ls
            if((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57){//ASCII
                ls.add(""+c);//变成字符串
                i = i+1;//i后移
            }else {//是数
                str = "";//初始化
                //判断条件 只要这个字符串没有遍历结束 且 遍历到的字符是数字
                while (i < s.length() && (c = s.charAt(i)) < 48 && (c = s.charAt(i)) > 57){
                    str += c;//拼接
                    i++;//向后移一位 继续遍历
                }
                ls.add(str);
            }
        }
        return ls;
    }


    //将 逆波兰表达式 依次将数据和运算符 放入ArrayList
    public static List<String> getListString(String suffixExpression){
        //将表达式分割 通过空格
        String[] split=suffixExpression.split(" ");
        List<String> list =new ArrayList<String>();
        for (String element : split){
            list.add(element);
        }
        return list;
    }
    // 完成对逆波兰表达式的运算
    /*
    *   1.从左至右扫描，将3和4压入堆栈；
        2.遇到+运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈；
        3.将5入栈；
        4.接下来是×运算符，因此弹出5和7，计算出7×5=35，将35入栈；
        5.将6入栈；
        6.最后是-运算符，计算出35-6的值，即29，由此得出最终结果
    * */
    public static int calculate(List<String> arrayList){
        //创建一个栈即可
        Stack<String> stack = new Stack<>();
        //遍历ArrayList
        for (String element : arrayList){
            //通过正则表达式取出 数据
            if(element.matches("\\d+")){//匹配的是多位数
                //入栈
                stack.push(element);
            }else {
                //pop两个数 运算 再入栈
                int num1 = Integer.parseInt(stack.pop());
                int num2 = Integer.parseInt(stack.pop());
                int result = 0;
                if(element.equals("+")){
                    result = num1 + num2;
                }else if(element.equals("-")){
                    result = num2 - num1;
                }else if(element.equals("*")){
                    result = num1 * num2;
                }else if(element.equals("/")){
                    result = num2 / num1;
                }else {
                    throw new RuntimeException("运算符有误");
                }
                //把 结果 入栈
                stack.push(""+result);
            }
        }
        //最后留在stack中的数据是运算结果
        return Integer.parseInt(stack.pop());
    }
}
//编写一个类 可以返回运算符 对应的优先级
class Operation{
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;
    //写一个方法返回对应的优先级数字
    public static int getValue(String operation){
        int result = 0;
        switch (operation){
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                System.out.println("不存在该运算符");
                break;
        }
        return result;
    }
}