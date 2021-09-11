package ljh;

import java.util.Scanner;

public class HashTableDemo {
    public static void main(String[] args) {
        //传建一个哈希表
        HashTab hashTab = new HashTab(7);
        //写一个简单的菜单
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("add:添加雇员");
            System.out.println("list:列出");
            System.out.println("find:查找雇员");
            System.out.println("exit:退出系统");
            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("输入id");
                    int id = scanner.nextInt();
                    System.out.println("输入名字");
                    String name = scanner.next();
                    //创建雇员
                    Emp emp = new Emp(id, name);
                    hashTab.add(emp);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("请输入要找的id");
                    id = scanner.nextInt();
                    hashTab.findEmpById(id);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    /*
     * HastTab是一个数组 每个值都是存放（emp对象的链表的）头指针
     *
     * */
    //创建HashTable管理多条链表
    static class HashTab {
        EmpLinkedList[] empLinkedListArray;
        private int size;//表示有多少条链表

        public HashTab() {
        }

        //构造器
        public HashTab(int size) {
            this.size = size;
            //初始化这个数组
            empLinkedListArray = new EmpLinkedList[size];
            //数组的元素还要初始化 因为是一个对象
            for (int i = 0; i < size; i++) {
                empLinkedListArray[i] = new EmpLinkedList();
            }
        }

        //添加员工
        public void add(Emp emp) {
            //根据员工的id 通过散列函数 得到 该员工应该添加到哪个接口
            int empLinkedListNum = hashFunction(emp.id);
            //将emp添加到对应的链表中
            //EmpLinkedList的add方法在HashTab类里面调用
            //如果是相同的关键字 则 会形成一个链表
            empLinkedListArray[empLinkedListNum].add(emp);
        }

        //遍历所有的链表 遍历hashTab
        public void list() {
            for (int i = 0; i < size; i++) {
                empLinkedListArray[i].list(i);
            }
        }

        //根据输入的id找到雇员
        public void findEmpById(int id) {
            //使用散列函数确定是那一条链表
            int i = hashFunction(id);
            Emp emp = empLinkedListArray[i].findEmpById(id);
            if (emp != null) {//找到
                System.out.printf("在第%d条链表找到雇员 id=%d\n", (i + 1), id);
            } else {
                System.out.println("没有找到");
            }
        }

        //编写散列函数 使用一个除留余数法
        public int hashFunction(int id) {
            return id % size;
        }
    }

    //雇员
    static class Emp {
        public int id;
        public String name;
        public Emp next;//存放下一个雇员的引用地址

        public Emp() {
        }

        public Emp(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    //链表类
    static class EmpLinkedList {
        //头指针 指向第一个Emp 不是指向一个头指针 没有像链表那样有头指针方便操作
        private Emp head;

        //添加雇员到链表
        //假定 当添加雇员时 id是自增长
        //因此把新的雇员放入链表的最后即可
        public void add(Emp emp) {
            //如果是添加第一个雇员
            if (head == null) {
                head = emp;
                return;
            } else {
                //如果不是第一个雇员 要一个辅助的指针定位到最后
                Emp curEmp = head;
                while (true) {
                    //如果辅助指针的next域没有引用变量 说明是最后一个
                    if (curEmp.next == null) {
                        break;
                    }
                    //指针后移
                    curEmp = curEmp.next;
                }
                //退出时 直接将emp加入链表
                curEmp.next = emp;
            }
        }

        //遍历链表的雇员信息
        public void list(int num) {
            //如果链表为空
            if (head == null) {
                System.out.println("当前" + (num + 1) + "链表为空");
                return;
            }
            System.out.print("当前第" + (num + 1) + "链表的信息为：");
            Emp curEmp = head;
            while (true) {
                System.out.printf("=> id = %d name = %s\t", curEmp.id, curEmp.name);
                if (curEmp.next == null) {//链表为空
                    break;
                }
                //后移一位
                curEmp = curEmp.next;
            }
        }

        //根据id查找雇员 就返回Emp 如果没有找到 就返回null
        public Emp findEmpById(int id) {
            //判断链表是否为空
            if (head == null) {
                System.out.println("链表为空");
                return null;
            }
            //辅助指针
            Emp curEmp = head;
            while (true) {
                if (curEmp.id == id) {//找到
                    break;//此时 curEmp指向的是要找的雇员
                }
                //退出
                if (curEmp.next == null) {//遍历结束 还是没有找到
                    curEmp = null;
                    break;
                }
                curEmp = curEmp.next;//后移一位
            }
            return curEmp;
        }
    }
}
