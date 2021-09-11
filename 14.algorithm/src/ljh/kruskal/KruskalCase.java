package ljh.kruskal;

import java.sql.Array;
import java.util.Arrays;

public class KruskalCase {
    private int edgeNum;//边的个数
    private char[] vertexs;//顶点的数组
    private int[][] matrix;//邻接矩阵 用来表现权值
    //用来表示两个顶点不连通
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int matrix[][] = {
                /*A*//*B*//*C*//*D*//*E*//*F*//*G*/ /*A*/
                {0, 12, INF, INF, INF, 16, 14},
                /*B*/ {12, 0, 10, INF, INF, 7, INF},
                /*C*/ {INF, 10, 0, 3, 5, 6, INF},
                /*D*/ {INF, INF, 3, 0, 4, INF, INF},
                /*E*/ {INF, INF, 5, 4, 0, 2, 8},
                /*F*/ {16, 7, 6, INF, 2, 0, 9},
                /*G*/ {14, INF, INF, INF, 8, 9, 0}};

        KruskalCase kruskalCase = new KruskalCase(vertexs, matrix);

        kruskalCase.print();
        EData[] edges = kruskalCase.getEdges();
        //未排序
        //System.out.println(Arrays.toString(edges));
        //kruskalCase.sortEdges(edges);
        //System.out.println(Arrays.toString(edges));

        kruskalCase.kruskal();
    }

    public KruskalCase(char[] vertexs, int[][] matrix) {
        //初始化定点数 和边的个数
        int vlen = vertexs.length;
        //初始化顶点
        this.vertexs = new char[vlen];
        for (int i = 0; i < vertexs.length; i++) {
            this.vertexs[i] = vertexs[i];
        }
        //初始化边 使用的是赋值拷贝的方式
        this.matrix = new int[vlen][vlen];
        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
        //统计边的条数
        for (int i = 0; i < vlen; i++) {
            //for (int j = 0; j<vlen; j++){
            //因为左右三角对称 不用重复遍历
            for (int j = i + 1; j < vlen; j++) {
                if (this.matrix[i][j] != INF) {
                    edgeNum++;
                }
            }
        }
    }

    public void kruskal() {
        int index = 0;//表示最后结果数组的索引
        /*
         *1) 就是将所有顶点按照从小到大的顺序排列好之后；某个顶点的终点就是"与它连通的最大顶点"。
         *2) 我们加入的边的两个顶点不能都指向同一 个终点，否则将构成回路。
         * */
        int[] ends = new int[edgeNum];//用于保存 已有最小生成树 中的每个顶点在最小生成树中的终点
        //创建结果数组 保存最后的最小生成树
        EData[] result = new EData[edgeNum];

        //获取 所有的边的集合  一共有12条边
        EData[] edges = getEdges();
        System.out.println("图的边的集合=" + Arrays.toString(edges) + "共" + edges.length);
        //按照边的权值大小进行排序(从小到大)
        sortEdges(edges);
        //遍历edges数组 将边添加到最小生成树中时，判断是否形成了回路 如果没有回路 加入result 有的话 不能加入
        for (int i = 0; i < edgeNum; i++) {
            //获取到第i条边的出度（起点）
            int position1 = getPosition(edges[i].start);
            //获取到第i条边的入度（终点）
            int position2 = getPosition(edges[i].end);
            //获取p1这个顶点在已有的最小生成树的终点
            int end1 = getEnd(ends, position1);//对于之前没有加入到最小生成树的顶点的终点是自己
            //获取p2这个顶点在已有的最小生成树的终点
            int end2 = getEnd(ends, position2);//对于之前没有加入到最小生成树的顶点的终点是自己
            //是否构成回路
            if (end1 != end2) {//没构成回路
                //设置end1在已有的最小生成树中的终点
                //eg 一开始 将边<E,F>加入 R 中 E的下标是4 设置为5 [0,0,0,0,0,0,0,0,0,0,0,0]->[0,0,0,0,5,0,0,0,0,0,0,0]
                //为什么不把F所对应的位置 设置成终点5 因为getEnd(ends, position2)帮忙做了
                ends[end1] = end2;
                //没有这个ends[end2]=end1; 因为getEnd(ends, position2)帮忙做了
                result[index++] = edges[i];//有条边到result
            }
        }
        //输出 最小生成树
        System.out.println("最小生成树" + Arrays.toString(result));
    }


    //打印邻接矩阵
    public void print() {
        System.out.println("邻接矩阵：");
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                System.out.printf("%20d\t", matrix[i][j]);
            }
            System.out.println();
        }
    }

    //对边进行排序处理，冒泡排序
    private void sortEdges(EData[] edges) {
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - 1 - i; j++) {
                if (edges[j].weight > edges[j + 1].weight) {
                    EData tmp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = tmp;
                }
            }
        }
    }

    /*
     * 传入顶点的值 eg:'A''B'
     * 返回对应的下标
     * 找不到返回-1
     * */
    private int getPosition(char ch) {
        for (int i = 0; i < vertexs.length; i++) {
            if (vertexs[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    /*
     * 截取图中边 放到EData[]数组中 后面需要遍历该数组
     * 边是通过matrix来获取
     * EData[] {'A','B',12}......
     * */
    private EData[] getEdges() {
        int index = 0;
        EData[] edges = new EData[edgeNum];
        for (int i = 0; i < vertexs.length; i++) {
            //因为左右三角对称 不用重复遍历
            for (int j = i + 1; j < vertexs.length; j++) {
                if (matrix[i][j] != INF) {
                    edges[index++] = new EData(vertexs[i], vertexs[j], matrix[i][j]);
                }
            }
        }
        return edges;
    }

    /*
     * 获取下标为i的顶点的终点 用于判断两个顶点的终点是否相同
     * 因为：我们加入的边的两个顶点不能都指向同一 个终点，否则将构成回路
     * ends 数组记录各个顶点对应的终点是哪一个 数组在遍历过程中 逐步地加入
     * i 表示传入的顶点对应的下标
     * 返回的是下标为i的顶点 所对应的 终点 下标
     * */
    private int getEnd(int[] ends, int i) {//アルゴリズムの魂　
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i;
    }

}

//创建一个类EData 表示对象实例的一条边
class EData {
    char start;//变得出度
    char end;//边的入度
    int weight;//边的权值

    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
    //重写toString 便于输出这一条边

    @Override
    public String toString() {
        return "EData{<" +
                start +
                ", " + end +
                "> =" + weight +
                '}';
    }
}