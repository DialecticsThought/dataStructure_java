package ljh;

public class Queen8 {
    /*
    问题：
    在8×8格的国际象棋上摆放八个皇后，使其不能互相攻击，即：任意两个皇后都不能处于同一行、同一列或同一斜线上，问有多少种摆法。
    解法：
    * 第一个皇后先放第一行第一列
      第二个皇后放在第二行第一列、然后判断是否OK， 如果不OK，继续放在第二列、第三列、依次把所有列都放完，找到一个合适
      继续第三个皇后，还是第一列、第二列……直到第8个皇后也能放在一个不冲突的位置，算是找到了一个正确解
      当得到一个正确解时，在栈回退到上一个栈时，就会开始回溯，即将第一个皇后，放到第一列的所有正确解，全部得到.
      然后回头继续第一个皇后放第二列，后面继续循环执行 1,2,3,4的步骤
    * */
    /*
    理论上应该创建一个二维数组来表示棋盘，但是实际上可以通过算法，用一个一维数组即可解决问题. arr[8] = {0 , 4, 7, 5, 2, 6, 1, 3}
    对应 arr 下标 表示第几行，即第几个皇后，arr[i] = val , val 表示第 i+1 个皇后，放在第 i+1 行的第 val+1 列
    举例说明
    0 4 7 5 2 6 1 3
    如果已经得到了第一种解法 要获得第二种解法时 先回溯到上一个栈(列)(就是是第七个皇后)
    把第8个皇后 从第4列继续移动 移动到第8列发现还是没有得到解法
    就继续回溯到上一个列(第6个皇后) 继续把第7个皇后从第2列移动到第8列 。。。。直到回溯到第一列(第一个皇后)
    0 5 7 2 6 3 1 4
    0 6 3 5 7 1 4 2
    0 6 4 7 1 3 5 2
    1 3 5 7 2 0 6 4（第一列）
    ............
    ............
    */
    //定义一个max标识共有多少个皇后
    int max = 8;
    //定一个数组array 保存皇后放置位置的结果
    int[] array = new int[max];
    static int count = 0;
    static int judgeCount = 0;
    public static void main(String[] args) {
        Queen8 queen8 = new Queen8();
         queen8.check(0);
         System.out.println("一共有"+count+"种解法");
        System.out.println("一共有"+judgeCount+"次冲突");
    }
    //编写一个方法 放置第n个皇后
    private void check(int n){
        //说明8个皇后已经放好了 现在是第九个
        if(n == max){//n = 8
            print();
            return;//应该结束
        }
        //依次放入皇后 并判断是否冲突
        //注意: check 每一次递归时 进入check中都有 for (int i = 0;i < max;i++) 因此会有回溯
        for (int i = 0;i < max;i++){
            //先把当前的这个皇后n 放在该行的第1列
            array[n] = i;
            //当放置第n个皇后到i列时 是否冲突
            if(judge(n)){//成立 就是不冲突
                //接着放第n+1个皇后 递归
                check(n+1);//这里 每一个皇后都check了8次
            }
            /*
            如果冲突 继续循环 i++ 然后再执行 array[n] = i 就是将第n个皇后 放置在本行的后移的一个位置
            */
        }
    }
    //查看当我们放置第n个皇后 就去检测该皇后是否和前面已经摆放的皇后冲突
    private boolean judge(int n){
        judgeCount++;
        for (int i = 0; i < n; i++){
            // array[i] == array[n] 如果是同一列
            //Math.abs(n-i) == Math.abs(array[n] - array[i])标识判断第n个皇后和第i个皇后是否在同一个斜线
            //Math.abs(n-i)表示 两个皇后的行差  Math.abs(array[n] - array[i])表示两皇后的列差
            //因为是一条斜线的话就是等腰直角三角形 行差等于列差
            //没有必要判断同一行 因为n每次在自增
            if(array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i])){
                return false;
            }
        }
        return true;
    }
    //写一个方法 可以将皇后摆放的位置输出
    private void print(){
        count++;
        for(int i = 0; i < array.length;i++){
            System.out.print(array[i]+ " ");
        }
        System.out.println();
    }
}
