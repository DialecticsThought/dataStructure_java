package ljh;

import java.sql.Array;
import java.util.Arrays;

public class PrimAlgorithm {
    public static void main(String[] args) {
        //测试图的创建
        char[] data = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
        int verxs = data.length;//节点个数就是定点长度
        //邻接矩阵的关系使用二维数组
        //10000表示权值很大 也就是不连通
        int[][] weight = new int[][]{
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000}
        };
        //创建MGraph对象
        MGraph mGraph = new MGraph(verxs);
        //创建minTree对象
        MinTree minTree = new MinTree();
        minTree.createGraph(mGraph, verxs, data, weight);
        //输出
        minTree.showGraph(mGraph);

        minTree.prim(mGraph, 1);
    }
}

//创建最小生成树
class MinTree {
    //创建图的邻接矩阵
    public void createGraph(MGraph graph, int verxs, char data[], int[][] weight) {
        int i, j;
        for (i = 0; i < verxs; i++) {//定点的值
            graph.data[i] = data[i];
            for (j = 0; j < verxs; j++) {
                graph.weight[i][j] = weight[i][j];//初始化邻接矩阵
            }
        }
    }

    //显示图的邻接矩阵
    public void showGraph(MGraph graph) {
        for (int[] link : graph.weight) {
            System.out.println(Arrays.toString(link));
        }
    }

    //编写prim算法
    /*
     * MGraph graph表示图
     * int v表示第几个定点作为起点
     * */
    public void prim(MGraph graph, int v) {
        //用来标记节点是否被访问过 没有遍历前是0
        int[] visited = new int[graph.verxs];
        for (int i = 0; i < graph.verxs; i++) {
            visited[i] = 0;
        }
        //把当前节点标记为已访问
        visited[v] = 1;
        //h1,h2记录两个顶点的下标
        int h1 = -1;//初始化
        int h2 = -1;//初始化
        //用来记录最小的权值 将minWeight 初始化成一个很大的数 后面遍历的过程中 被替换
        int minWight = 10000;
        //因为有graph.verxs个定点 prim结束后 生成 graph.verxs-1个边 也就是要执行graph.verxs-1循环
        //最小生成树
        for (int k = 1; k < graph.verxs; k++) {
            //每一次生成的子图 子图的节点和哪一个未被访问节点的距离最近
            //i是假定已经访问过的节点的下标  遍历已经访问过的节点 一个已经访问过的节点和所有还没有被访问的节点进行比较
            for (int i = 0; i < graph.verxs; i++) {
                //j是假定未访问过的节点的下标
                for (int j = 0; j < graph.verxs; j++) {
                    //visited[i]==1表示 节点i已经访问过 visited[j]==0表示节点j还没有被访问
                    //graph.weight[i][j]<minWight i和j的距离比minWeight小的话 赋值
                    if (visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWight) {
                        //替换minWeight（子图的节点和哪一个未被访问节点的最近距离）
                        minWight = graph.weight[i][j];
                        //记录节点 i 和 j
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            //找到一条边最小
            System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + ">权值： " + minWight);
            //把当前节点标记h2（j）为已经访问    i不用标记 因为以经标记为访问过
            visited[h2] = 1;
            //再初始化minWeight 防止影响下次循环
            minWight = 10000;
        }
    }
}

class MGraph {
    int verxs;//节点的个数
    char[] data;//存放节点数据
    int[][] weight;//存放边 就是我们的邻接矩阵

    public MGraph(int verxs) {
        this.verxs = verxs;
        data = new char[verxs];
        weight = new int[verxs][verxs];
    }
}