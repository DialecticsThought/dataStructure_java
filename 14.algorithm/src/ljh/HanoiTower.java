package ljh;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HanoiTower {
    static Long count = 0L;

    public static void main(String[] args) {
        Date date1 = new Date();
        //格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String dateStr1 = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是:" + dateStr1);


        hanoiTower(40, 'A', 'B', 'C');
        System.out.println(count);


        Date date2 = new Date();
        String dateStr2 = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是:" + dateStr2);
    }

    //汉诺塔的移动方法
    //使用分治算法
    public static void hanoiTower(int num, char a, char b, char c) {
        //如果只有一个盘
        if (num == 1) {
            //System.out.println("第一个盘从 "+a+"->"+c);
            count++;
        } else {
            /*
            如果我们有 n >= 2 情况，我们总是可以看做是两个盘 1.最下边的盘 2. 上面的盘
            * 2) 先把 最上面的盘 A->B
            * 3) 把最下边的盘 A->C
            * 4) 把 B 塔的所有盘 从 B->C
            *
            *这三个子步骤 用递归来解决
            *
            *对于n个盘 n-1个盘要移动到B 最后一个盘也就是第n个盘移动到C
            *对于n-1个盘中的 n-2个盘要移动到B 最后一个盘也就是第n-1个盘移动到C
            *。。。。。。。不断地递归
            * */

            //第一次传进来abc 指的是ABC 但是盘子大于2个及以上 c指代B b指代C
            //先把 最上面的盘 A->B 移动过程中 会用到c c是辅助
            hanoiTower(num - 1, a, c, b);
            //2.吧最下面的盘A->C
            //System.out.println("第"+num+"个盘从 "+a+"->"+c);
            count++;
            //3) 把 B 塔的所有盘 从 B->C 移动过程中 会用到a
            hanoiTower(num - 1, b, a, c);
        }
    }
}
