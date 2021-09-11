package ljh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Graph {
    private ArrayList<String> vertextList;//存储定点的集合
    private int[][] edges;//存储图对应的邻接矩阵
    private int numOfEdges;//表示边的数目
    //定义个数组 记录某个节点是否被访问
    private boolean[] isVisited;

    public static void main(String[] args) {
        //测试
        int n = 5;
        String vertexs[] = {"A", "B", "C", "D", "E"};
        //创建图对象
        Graph graph = new Graph(vertexs.length);
        //循环添加顶点
        for (String vertex : vertexs) {
            graph.insertVertex(vertex);
        }
        //添加边
        //设定 A<->B A<->C B<->C B<->D B<->E
        graph.insertEage(0, 1, 1);
        graph.insertEage(0, 2, 1);
        graph.insertEage(1, 2, 1);
        graph.insertEage(1, 3, 1);
        graph.insertEage(0, 4, 1);
        //显示邻接矩阵
        graph.showGraph();
        System.out.println("深度遍历");
        graph.dfs();
        System.out.println("广度遍历");
        //graph.bfs();
    }

    public Graph(int n) {
        this.vertextList = new ArrayList<String>(n);
        this.edges = new int[n][n];
        this.numOfEdges = 0;
        isVisited = new boolean[5];
    }

    //得到第一个邻接节点的下标
    /*
     * 传一个节点的下标 查找该节点的邻接点通过数组下标
     * index相当于行 for循环遍历 改行的每一个值
     * 返回的是该节点的邻接点的索引 否则返回-1表示没有
     * */
    public int getFirstNeighbor(int index) {
        for (int j = 0; j < vertextList.size(); j++) {
            if (edges[index][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    //根据前一个邻接节点的下标来获取下一个邻接节点
    public int getNextNeighbor(int v1, int v2) {
        //v2是v1的邻接点的索引 但是已经访问过了 要找针对v1节点 除了v2是邻接点 还有其他邻接点么
        for (int j = v2 + 1; j < vertextList.size(); j++) {
            //如果节点v1 所在的行有值 说明v1节点存在邻接节点 返回
            if (edges[v1][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    //深度优先算法
    //i第一次就是0
    public void dfs(boolean[] isVisited, int i) {
        //1.1首先访问该节点 输出
        System.out.print(getValueByIndex(i) + "->");
        //1.2将节点设置成已经访问
        isVisited[i] = true;
        //2.并且访问该节点的第一个邻接节点w
        int w = getFirstNeighbor(i);
        while (w != -1) {//说明有
            //如果w没有被访问!isVisited[firstNeighbor_w]
            if (!isVisited[w]) {
                dfs(isVisited, w);
            } else {//如果节点w被访问过
                //i表示正在被访问的节点v firstNeighbor_w是节点i的邻接点 但是被访问过了
                w = getNextNeighbor(i, w);
            }
        }
    }

    /*
     *
     * 1) 访问初始结点 v，并标记结点 v 为已访问。
     * 2) 查找结点 v 的第一个邻接结点 w。
     * 3) 若 w 存在，则继续执行 4，如果 w 不存在，则回到第 1 步，将从 v 的下一个结点继续。
     * 4) 若 w 未被访问，对 w 进行深度优先遍历递归（即把 w 当做另一个 v，然后进行步骤 123）。
     * 5) 查找结点 v 的 w 邻接结点的下一个邻接结点，转到步骤 3。
     * 6) 分析图
     * */
    //为了实现 步骤3的"则回到第 1 步，将从 v 的下一个结点继续"。 对dfs进行一个重载
    public void dfs() {
        //遍历所有的节点 进行dfs回溯
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                dfs(isVisited, i);
            }
        }
    }

    /*
     *1) 访问初始结点 v 并标记结点 v 为已访问。
     *2) 结点 v 入队列
     *3) 当队列非空时，继续执行，否则算法结束。
     *4) 出队列，取得队头结点 u。
     *5) 查找结点 u 的第一个邻接结点 w。
     *6) 若结点 u 的邻接结点 w 不存在，则转到步骤 3；否则循环执行以下三个步骤：
     *   6.1 若结点 w 尚未被访问，则访问结点 w 并标记为已访问。
     *   6.2 结点 w 入队列
     *   6.3 查找结点 u 的继 w 邻接结点后的下一个邻接结点 w，转到步骤 6。
     * */
    //对一个节点进行广度优先遍历的方法
    //i表示要遍历的节点的索引
    public void bfs(boolean[] isVisited, int i) {
        int u;//队列的头结点对应的下标
        int w;//邻接节点w
        //队列 记录节点访问的顺序
        LinkedList<Integer> queue = new LinkedList<Integer>();
        //访问节点 输出节点信息
        System.out.print(getValueByIndex(i) + "->");
        //标记为已访问
        isVisited[i] = true;
        //将节点加入队列
        queue.addLast(i);
        while (!queue.isEmpty()) {
            //只要队列不为空 就取出队列的头结点（下标）
            u = queue.removeFirst();
            //得到节点u的第一个邻接点下标w
            w = getFirstNeighbor(u);
            while (w != -1) {//说明找到了
                //判断是否访问过
                if (!isVisited[w]) {
                    /*
                     * 6) 若结点 u 的邻接结点 w 不存在，则转到步骤 3；否则循环执行以下三个步骤：
                     * 6.1 若结点 w 尚未被访问，则访问结点 w 并标记为已访问。
                     * 6.2 结点 w 入队列
                     * 6.3 查找结点 u 的继 w 邻接结点后的下一个邻接结点 w，转到步骤 6。
                     * */
                    System.out.print(getValueByIndex(w) + "->");
                    //标记该节点被访问
                    isVisited[w] = true;
                    //并且在队列中添加这个w
                    queue.addLast(w);
                }
                //继续以节点u为基准 也就是u所在的行 找w后面的邻接点 当然这些邻接点也标记为变量w
                w = getNextNeighbor(u, w);
            }
        }
    }

    //上面的bfs是针对一个节点的 要针对所有的节点
    public void bfs() {
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
    }

    //（无向）插入节点
    public void insertVertex(String vertex) {
        vertextList.add(vertex);
    }

    //（无向）添加边
    /*
     * v1 表示点1的下标 也就是第几个定点
     * v2 表示点2的下标 也就是第几个定点
     * weight = 权值
     * */
    public void insertEage(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    //（无向）图中常用的方法
    public int getNumOfVertex() {
        return vertextList.size();
    }

    //得到（无向）边的数目
    public int getUmOfEdges() {
        return numOfEdges;
    }

    //返回节点下标i对应的数据 0->A 1->B
    public String getValueByIndex(int i) {
        return vertextList.get(i);
    }

    //返回无向图v1<->v2的权值
    //形参是 v1 v2节点的索引
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    //显示（无向）图对应的矩阵
    //也就是遍历矩阵
    public void showGraph() {
        //一次遍历得到以一行为单位的数据
        for (int[] link : edges) {
            System.out.println(Arrays.toString(link));
        }
    }
}
