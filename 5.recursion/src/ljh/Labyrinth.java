package ljh;

public class Labyrinth {
    public static void main(String[] args) {
        //创建一个二维数组 模拟迷宫
        int[][] map =new int[8][7];
        //使用1表示墙
        //墙上下的边
        for (int i = 0;i < 7;i++){
            map[0][i] = 1;
            map[7][i] = 1;
        }
        //墙左右的边
        for (int i =0;i < 8; i++){
            map[i][0] = 1;
            map[i][6] = 1;
        }
        //设置挡板
        map[3][1] = 1;
        map[3][2] = 1;
        //输出地图
        System.out.println("地图：");
        for (int i = 0;i < 8;i++){
            for(int j = 0;j < 7;j++){
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
        //使用递归回溯 给小球找路
        findWay(map,1,1);
        //输出新的地图 小球走过 并标识的地图
        System.out.println("地图：");
        for (int i = 0;i < 8;i++){
            for(int j = 0;j < 7;j++){
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
    }

    //使用递归回溯给小球找路
    /*
    * map表示地图
    * i,j表示从地图的哪个位置出发  一开始是(1,1)
    * 如果小球能到map[6][5]也就是重点 说明找到路
    * 约定：
    * map[i][j]=0表示 该点没有走过
    * 1 表示墙
    * 2表示通路 可以走
    * 3表示该店已经走过 但是走不通
    * 走迷宫时 定一个策略 先走下 -> 走不通 就走右 ->走不通 就走上 ->走不通 就走左
    *   如果该店走不通 再回溯
    *
    * 每到一个新的点 都是先把该点置为0 在假设该点是能走通的（置为2）
    * 每个点就会按照 下 ->右 -> 上 -> 左 的规则 找有没有路
    *           如果一个点上下左右都走不通 2 ----> 3
    *           如果有一个点某个方向能走通 那还是 2
     * */
    public static boolean findWay(int[][] map, int i, int j) {
        if(map[6][5] == 2){//说明通路已经找到
            return true;//直接结束
        }else {
            //判断当前这个点有没有走过
            if(map[i][j] == 0){
                //按照约定策略走
                map[i][j] = 2;//假定该点是能走通的
                if(findWay(map,i+1,j)){//向下走 判断下一个点
                    return true;//说明能走通
                }else if(findWay(map,i,j+1)){//向右走 判断右边一个点
                    return true;
                }else if(findWay(map,i-1,j)) {//向上走 判断上边一个点
                    return true;
                }else if(findWay(map,i,j-1)){//向左走 判断左边一个点
                    return true;
                }else {//说明上下左右都走不通 是死路
                    map[i][j] = 3;//从 2 到 3
                    return false;//表示这条路不要走了
                }
            }else {// 如果map[i][j] != 0 可能是 1 2 3
                //1是墙  不要走了 2表示之前走过了 就不要走了 3 表示死路 也不要走了
                return false;//标识不要走了
            }
        }
    }
}
