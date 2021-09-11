package ljh.floyd;

import java.util.Arrays;

public class FloydAlgorithm {
    public static void main(String[] args) {
        //测试图是否创建成功
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        //创建邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;
        matrix[0] = new int[]{0, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, 0, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, 0, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, 0, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, 0, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, 0, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, 0};

        //创建Graph对象
        Graph graph = new Graph(vertex.length, matrix, vertex);
        graph.show();
        graph.floyd();
        System.out.println("================");
        graph.show();
    }
}

//创建图
class Graph {
    private char[] vertex;//存放顶点的数组
    private int[][] distance;//保存 从各个顶点出发到其他顶点的距离 最后的结果 也保存在该数组
    private int[][] pre;//保存到达目标顶点的前驱顶点

    /*
     *  length 大小
     *  matrix 邻接矩阵
     *  vertex 顶点数组
     *
     * */
    //构造器
    public Graph(int length, int[][] matrix, char[] vertex) {
        this.vertex = vertex;
        this.distance = matrix;
        this.pre = new int[length][length];
        //对pre数组 初始化 存放的是前驱顶点的下标 而不是直接填入A B C...
        for (int i = 0; i < length; i++) {
            Arrays.fill(pre[i], i);
        }
    }

    public Graph() {
    }

    public char[] getVertex() {
        return vertex;
    }

    public void setVertex(char[] vertex) {
        this.vertex = vertex;
    }

    public int[][] getDistance() {
        return distance;
    }

    public void setDistance(int[][] distance) {
        this.distance = distance;
    }

    public int[][] getPre() {
        return pre;
    }

    public void setPre(int[][] pre) {
        this.pre = pre;
    }

    //显示pre数组 和distance数组
    public void show() {
        //为了显示便于阅读 优化一下输出
        char[] vertex = getVertex();

        for (int k = 0; k < distance.length; k++) {
            //先将pre数组输出
            for (int i = 0; i < distance.length; i++) {
                System.out.print(vertex[pre[k][i]] + " ");
            }

            //输出distance数组的一行数据
            for (int i = 0; i < distance.length; i++) {
                System.out.print("(" + vertex[k] + "到" + vertex[i] + "的最短路径是：" + distance[k][i] + ") ");
            }
            System.out.println();
            System.out.println();
        }
    }
    /*
    * 弗洛伊德算法
    * 中间顶点 [A,B.C.D.E.F.G] k
    * 出发顶点 [A,B.C.D.E.F.G] i
    * 终点    [A,B.C.D.E.F.G] j
    * k=0 i=0 j=0
    *     i=0 ...
    *     i=0 j=6
    *
    * k=0 i=1 j=0
    *     i=1 ...
    *     i=1 j=6
    *
    * k=0 ... ...
    *
    * k=0 i=6 j=0
    *     i=6 ...
    *     i=6 j=6
    * ... ... ...
    * ... ... ...
    *
    * k=6 i=0 j=0
    *     i=0 ...
    *     i=0 j=6
    * */
    public void floyd() {
        int len = 0;//变量保存距离
        //对中间顶点的遍历 k是 中间顶点的下标
        for (int k = 0; k < distance.length; k++) {
            //从i顶点出发
            for (int i = 0; i < distance.length; i++) {
                //j为最终定点
                for (int j = 0; j < distance.length; j++) {
                    //求出 i顶点 出发 经过 k顶点 到 j顶点的距离
                    //i --> k之间 可以有中间定点  k --> j 也可以有中间定点
                    len = distance[i][k] + distance[k][j];
                    //算出的结果 < 直连的距离
                    if (len < distance[i][j]) {
                        distance[i][j] = len;
                        //更新前驱顶点 pre[k][j] 不能用k的原因是 k-->j之间有经过其他的点
                        pre[i][j] = pre[k][j];
                    }
                }
            }
        }
    }
}