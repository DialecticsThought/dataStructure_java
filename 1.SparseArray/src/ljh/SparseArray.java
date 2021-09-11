package ljh;

public class SparseArray {
    public static void main(String[] args) {
        //创建按一个原始的二维数组 11*11
        //0：表示没有棋子 1：表示黑子 2：表示篮子
        int chessArr1[][] = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[5][7] = 2;
        chessArr1[8][9] = 99;
        //输出原始的二维数组
        //外层的增强for循环是遍历行的
        for(int[] row : chessArr1){
            //第二层for循环是便利行里面的数据
            for(int data : row){
                System.out.printf("%d\t",data);
            }
            //每一行结束用来换行
            System.out.println();
        }
        //1 遍历二维数组 得到非0数据的个数
        int sum = 0;
        for(int i = 0; i< 11; i++){
                for(int j = 0;j < 11;j++){
                    //说明这个数字非0 则计入总数
                    if(chessArr1[i][j]>0){
                        sum++;
                    }
                }
        }
        System.out.println(sum);
        //2 创建相应的稀疏数组
        int sparseArr[][] = new int[sum+1][3];
        //3 给稀疏数组赋值
        //稀疏数组第一行是 几行 几列 有多少值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;
        //遍历二维数组 将非0数据存到数组sparseArr中
        int count = 0;//用于记录是第几个非0数据
        /*
        *稀疏数组的第一行是记录 原数组的元信息（共有几行 共有几列 值多少个）
        * 第二行开始稀疏数组每行记录一个 原数组的非0数据
        * 每一行 是该数据的所在行 所在列 本身的值
        * count+1是因为第0行是元信息
        * */
        for(int i = 0; i< 11; i++){
            for(int j = 0;j < 11;j++){
                if(chessArr1[i][j] != 0){//说明这个数字非0
                    count++;
                    sparseArr[count][0]=i;//该数据的所在行
                    sparseArr[count][1]=j;//该数据的所在列
                    sparseArr[count][2]=chessArr1[i][j];//该数据的本身的值

                }
            }
        }
        //输出稀疏数组的形式
        System.out.println();
        System.out.println("得到稀疏数组为-------");
        for(int i = 0; i < sparseArr.length ; i++ ){
            System.out.printf("%d\t%d\t%d\t",sparseArr[i][0],sparseArr[i][1],sparseArr[i][2]);
            System.out.println();
        }

        //将稀疏数组 回复称原始的二维数组
        /*
        * 读取稀疏数组的第一行元信息 建立相应的二维数组
        * 通过遍历 读取稀疏数组之后所有行 把每一行的 数据所在行 所在列 值 拿到
        * */
        int[][] chessArr2 = new int[sparseArr[0][0]][sparseArr[0][1]];
        for (int i = 1;i < sparseArr.length ;i++){//sparseArr.length表示有几行
            //不管是第几行 第一列永远是值所在的列 第二列是值所在的行 第三列是值
            chessArr2[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        //输出回复后的二维数组
        System.out.println();
        System.out.println("恢复后的二维数组");
        for(int[] row : chessArr2){
            //第二层for循环是便利行里面的数据
            for(int data : row){
                System.out.printf("%d\t",data);
            }
            //每一行结束用来换行
            System.out.println();
        }
    }

}

