package ljh.kmp;

public class ViolenceMatch {
    public static void main(String[] args) {
        String str1 = "尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
        String str2 = "尚硅谷你尚硅你";
        int index = violenceMatch(str1, str2);
        System.out.println("index=" + index);
    }
    public static int violenceMatch(String str1,String str2){
        char[] s1=str1.toCharArray();//先变成字符数组
        char[] s2=str2.toCharArray();//先变成字符数组

        int s1Len=s1.length;
        int s2Len=s2.length;

        int i=0;//相当于指针索引 指向s1
        int j=0;//相当于指索引针 指向s2
        while (i<s1Len &&j<s2Len){//保证匹配时 不越界
            if(s1[i] == s2[j]){
                //说明匹配成功
                i++;
                j++;
            }else {//没有匹配成功
                i =i-(j-1);
                j=0;//从的str2头再匹配
            }
        }
        //判断匹配成功
        if(j==s2Len){//说明j指向了s2的末尾 结束
            return i-j;
        }else {
            return -1;
        }
    }
}
