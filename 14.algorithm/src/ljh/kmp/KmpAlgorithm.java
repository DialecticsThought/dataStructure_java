package ljh.kmp;

public class KmpAlgorithm {
    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        int[] next = kmpNext("ABCDABD"); //[0, 1, 2, 0]
        int index = kmpSearch(str1, str2, next);
        System.out.println("index=" + index); // 15 了
    }
    /*
    * String str1源字符串
    *String str2子串
    * int[] next字符串（子串）的部分匹配值表
    * 如果是-1就是没有匹配到 否则返回第一个匹配的位置
    * */
    public static int kmpSearch(String str1,String str2,int[] next){
        //遍历
        for(int i=0,j=0;i<str1.length();i++){

            //处理str1.charAt(i)!=str2.charAt(j) 去调整j大小
            while (j>0&&str1.charAt(i)!=str2.charAt(j)){
                j=next[j-1];//在部分匹配表 从后往前找
            }

            if(str1.charAt(i)==str2.charAt(j)){
                j++;
            }
            if(j==str2.length()){//str="BBC"的话 j=3 i=2 因为i还没有机会+1 =3
                return i-j+1;
            }
        }
        return -1;
    }

    //获取到一个字符串（子串）的部分匹配值表
    public static int[] kmpNext(String dest){
        //创建一个next 数组保存部分匹配值
        int[] next=new int[dest.length()];
        next[0]=0;//如果dest字符串的长度为1 部分匹配值一定是0
        for (int i=1,j=0;i<dest.length();i++){
            //dest.charAt(i)!=dest.charAt(j) 我们需要从next[j-1]获取新的j
            //直到我们发现有 dest.charAt(i)==dest.charAt(j)成立再退出
            while (j>0&&dest.charAt(i)!=dest.charAt(j)){
                j=next[j-1];
            }
            if(dest.charAt(i)==dest.charAt(j)){//说明了部分匹配值+1
                j++;
            }
            next[i]=j;
        }
        return next;
    }
}
