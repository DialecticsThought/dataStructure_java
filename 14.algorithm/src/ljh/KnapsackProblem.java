package ljh;

public class KnapsackProblem {
    public static void main(String[] args) {
        int[] w = {1, 4, 3};//物品的重量
        int[] val = {1500, 3000, 2000};//物品的价值 val[i] 就是v[i]
        int m = 4;//背包的容量
        int n = val.length;//物品的个数
        //为了记录放入商品的情况 定一个二维数组
        int[][] precess = new int[n + 1][m + 1];
        //创建二维数组
        //table[i][j]表示在前 i 个物品中能够装入容量为 j 的背包中的最大价值
        int[][] table = new int[n + 1][m + 1];

        //初始化第一行和第一列 在本程序中 可以不处理 因为默认就是0
        for (int i = 0; i < table.length; i++) {
            table[i][0] = 0;//将第一列设置为0
        }
        for (int i = 0; i < table[0].length; i++) {
            table[0][i] = 0;//将第一行设置为0
        }
        //输出一下v 查看当前情况
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
        //根据前面的公式动态规划处理
        for (int i = 1; i < table.length; i++) {//不处理第一行 保证第一行是0
            for (int j = 1; j < table[0].length; j++) {//不处理第一列 j是从1开始的
                //公式
                if (w[i - 1] > j) {//不同于分析 程序中的i是从1开始的 所以w[i] -> w[i-1]
                    table[i][j] = table[i - 1][j];
                } else {
                    //因为程序中i从1开始 从v[i][j]=Math.max(table[i-1][j],val[i]+table[i][j-w[i]]);调整成
                    //table[i][j] = Math.max(table[i - 1][j], val[i - 1] + table[i - 1][j - w[i - 1]]);
                    //为了解决记录商品存放到背包的情况 不能简单使用Math.max 要用if-else处理
                    if (table[i - 1][j] < val[i - 1] + table[i - 1][j - w[i - 1]]) {
                        table[i][j] = val[i - 1] + table[i - 1][j - w[i - 1]];
                        //记录当前的情况
                        precess[i][j] = 1;
                    } else {
                        table[i][j] = table[i - 1][j];
                    }
                }
            }
        }
        //输出一下v 查看当前情况
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
/*        for (int i = 0; i < precess.length; i++) {
            for (int j = 0; j < precess[i].length; j++) {
                if(precess[i][j]==1){
                    System.out.printf("第%d个商品放入到背包\t",i);
                }

            }
            System.out.println();
        }*/
        int row = precess.length - 1;//行的最大下标
        int column = precess[0].length - 1;//列的最大下标
        while (row > 0 && column > 0) {
            if (precess[row][column] == 1) {
                System.out.printf("第%d个商品放入到背包\t", row);
                column = column - w[row - 1];
            }
            row--;
        }
    }
}
