package ljh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GreedyAlgorithm {
    public static void main(String[] args) {
        //创建广播电台 放入到map
        HashMap<String, HashSet<String>> broadcasts = new HashMap<>();
        //将各个电台放入broadcasts
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");
        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");
        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");
        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("天津");
        hashSet4.add("上海");
        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");
        //加入到map
        broadcasts.put("k1", hashSet1);
        broadcasts.put("k2", hashSet2);
        broadcasts.put("k3", hashSet3);
        broadcasts.put("k4", hashSet4);
        broadcasts.put("k5", hashSet5);
        //存放所有的地区
        HashSet<String> allAreas = new HashSet<>();
        allAreas.add("北京");
        allAreas.add("上海");
        allAreas.add("天津");
        allAreas.add("广州");
        allAreas.add("深圳");
        allAreas.add("成都");
        allAreas.add("大连");
        allAreas.add("杭州");
        //创建一个ArrayList,存放选择的电台集合
        ArrayList<String> selects = new ArrayList<>();
        //定义一个临时集合 在遍历的过程 存放遍历过程中 电台覆盖的地区 和当前还没有覆盖的地区的交集
        HashSet<String> tempSet = new HashSet<>();
        HashSet<String> tempSet2 = new HashSet<>();
        //定一个maxKey 保存再一次遍历过程中 能够覆盖最大未覆盖的地区对应的电台的key
        //如果maxKey 不为Null 则会加入到selects
        String maxkey = null;
        while (allAreas.size() != 0) {//allAreas如果不为0 则表示还没有覆盖到所有的地区
            //每一次while循环 选出新的maxKey 所以置空
            maxkey = null;
            //遍历broadcast取出对应的key
            for (String key : broadcasts.keySet()) {
                //每一次循环 都会有tempSet临时存放变量 所以新的循环先清除之前的数据
                tempSet.clear();
                tempSet2.clear();
                //当前的key所能覆盖的地区
                HashSet<String> areas = broadcasts.get(key);
                tempSet.addAll(areas);
                //key所能覆盖的地区与allAreas取交集 通过tempSet 再赋给tempSet
                tempSet.retainAll(allAreas);
                //如果当前的集合包含的未覆盖地区的数量 比maxKey指的集合的未覆盖地区的数量还要多 那么maxKey 指向当前集合
                //重置maxKey
                /*
                 *
                 * tempSet.size()>broadcasts.get(maxkey).retainAll(allAreas).size()是贪婪算法的核心
                 * 又因为上述语句语法错误 必须定一个临时变量接受broadcasts.get(maxkey).retainAll(allAreas)
                 * */
                if (maxkey != null) {//因为一开始maxKey还没有指向 所以不用执行
                    HashSet<String> hashSet = broadcasts.get(maxkey);
                    tempSet2.addAll(hashSet);
                    tempSet2.retainAll(allAreas);
                }
                if (tempSet.size() > 0
                        && (maxkey == null
                        || tempSet.size() > tempSet2.size())) {
                    maxkey = key;
                }
            }
            if (maxkey != null) {
                selects.add(maxkey);//加入到集合中
                //将maxKey指向的广播电台覆盖的地区从allAreas清除掉
                allAreas.removeAll(broadcasts.get(maxkey));
            }
        }
        System.out.println("得到选择结果是" + selects);
    }
}
