package ljh;

import java.util.Arrays;

public class Dijkstra {
    /*
     * 设置出发顶点为 v，顶点集合 V{v1,v2,vi...} 记录访问过的顶点，
     * v 到 V 中各顶点的距离构成距离集合 Dis，Dis{d1,d2,di...}，
     * Dis 集合记录着出发点 v 到图中各顶点的距离(到自身可以看作 0，v 到 vi 距离对应为 di)
     *
     * 1）从 Dis 中选择值最小的 di 并移出 Dis 集合，同时移出 V 集合中对应的顶点 vi，此时的 v
     * 2）更新 Dis 集合，更新规则为：比较 v 到 V 集合中顶点的距离值，与 v 通过 vi 到 V 集合中顶点的距离值，保留 值较小的一个(同时也应该更新顶点的前驱节点为 vi，表明是通过 vi 到达的)
     * 3）重复执行两步骤，直到最短路径顶点为目标顶点即可结束
     * */
    /*
     * dis记录 G点到各个顶点的距离 刚开始 还没有到各个顶点的距离的时候 dis中 除了G点所对应的索引处是0 其他都设置为65535
     *
     *
     * */
    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        //邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;// 表示不可以连接
        matrix[0] = new int[]{N, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, N, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, N, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, N, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, N, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, N, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, N};
        //创建Grapg对象
        Graph graph = new Graph(vertex, matrix);
        graph.showGraph();
        graph.dsj(6);
        graph.showDijkstra();
    }
}

class Graph {
    private char[] vertex;//顶点数组
    private int[][] matrix;//邻接矩阵
    private VisitedVertex visitedVertex;

    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;

    }

    //显示图
    public void showGraph() {
        for (int[] link : matrix) {
            System.out.println(Arrays.toString(link));
        }
    }

    //显示结果
    public void showDijkstra() {
        visitedVertex.show();
    }

    //算法实现
    public void dsj(int index) {
        visitedVertex = new VisitedVertex(vertex.length, index);
        update(index);//更新index下标顶点到周围顶点的距离和前驱顶点
        //上面两步上走第一个顶点 for循环是用来走剩余的顶点
        for (int j = 1; j < vertex.length; j++) {
            int i = visitedVertex.updateArr();//选择并返回新的访问顶点
            update(i);
        }
    }

    //更新index下标顶点到周围顶点的距离 和周围顶点所对应的前驱节点
    private void update(int index) {
        int len = 0;
        //遍历第index行
        for (int j = 0; j < matrix[index].length; j++) {
            //出发顶点->index下标所对应的顶点的距离+index下标所对应的顶点->j顶点的距离
            len = visitedVertex.getDis(index) + matrix[index][j];
            //如果j顶点没有被访问过（j顶点还没有找到最短路径） 且 len小于出发顶点到即奠定的距离
            if (!visitedVertex.in(j) && len < visitedVertex.getDis(j)) {
                visitedVertex.updateDis(j, len);//更新出发点到j下标的顶点的距离
                visitedVertex.updatePre(j, len);//更新j顶点的前驱为idnex下标的顶点
            }
        }
    }
}

//已经访问顶点的集合
class VisitedVertex {
    //记录各个顶点是否访问过 1表示访问过 0表示没有访问过
    public int[] already_arr;
    //每一个下标多对应的值是前驱节点对应的下标
    public int[] pre_visited;
    //记录出发顶点到其他所有顶点的距离 eg：G作为除法定点 记录下 G到其他顶点的距离 会动态更新 把从初始点到某顶点的最短距离记录下来
    public int[] dis;

    /*
     *
     * length:表示顶点的个数
     * index:出发顶点对应的下标 eg:G点 小标是6
     * */
    public VisitedVertex(int length, int index) {
        this.already_arr = new int[length];
        this.pre_visited = new int[length];
        this.dis = new int[length];
        //初始化dis 全部设置为65535
        Arrays.fill(dis, 66535);
        this.already_arr[index] = 1;//设置出发顶点已经被访问过
        this.dis[index] = 0;//设置出发点 dis=0
    }

    /*
    判断index顶点是否被访问过
    访问过 返回true
     */
    public boolean in(int index) {
        return already_arr[index] == 1;
    }

    /*
     * 更新出发顶点到下标为index的顶点的距离
     *
     * */
    public void updateDis(int index, int len) {
        dis[index] = len;
    }

    /*
     * 更新下标pre的节点的前驱节点设置成index
     * */
    public void updatePre(int pre, int index) {
        pre_visited[pre] = index;
    }

    /*
     * 返回出发顶点到下标为index的顶点的距离
     * */
    public int getDis(int index) {
        return dis[index];
    }

    //继续选择并返回新的访问顶点 比如这里 G结束后 就是A点作为新的访问顶点（不是出发顶点）
    public int updateArr() {
        int min = 65535, index = 0;
        for (int i = 0; i < already_arr.length; i++) {
            //already_arr[i]==0表示还没有访问过
            if (already_arr[i] == 0 && dis[i] < min) {
                min = dis[i];//替换更小的
                index = i;//下一个新的访问顶点就是index 永远是最小的作为访问顶点
            }
        }
        //更新index顶点被访问过 真正没有访问过并且是 dis最小的
        already_arr[index] = 1;
        return index;
    }

    //显示最后的结果
    public void show() {
        for (int i : already_arr) {
            System.out.print(i + "  ");
        }
        System.out.println();
        for (int i : pre_visited) {
            System.out.print(i + "  ");
        }
        System.out.println();
        for (int i : dis) {
            System.out.print(i + "  ");
        }
        System.out.println();
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int count = 0;
        for (int i : dis) {
            if (i != 65535) {
                System.out.print(vertex[count] + "(" + i + ")");
            } else {
                System.out.print("N");
            }
            count++;
        }
        System.out.println();
    }
}