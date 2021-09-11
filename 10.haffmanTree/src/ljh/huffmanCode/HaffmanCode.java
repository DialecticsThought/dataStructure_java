package ljh.huffmanCode;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HaffmanCode {
    public static void main(String[] args) {
        String str="i like like like java do you like a java";
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes.length);
        List<Node> nodes = getNodes(bytes);
        System.out.println("nodes = "+nodes);

        System.out.println("哈夫曼树");
        Node huffmanTreeNode = createHuffmanTree(nodes);

        System.out.println("前序遍历");
        preOrder(huffmanTreeNode);

        //测试生成哈夫曼编码
        //getCodes(huffmanTreeNode,"",stringBuilder);
        getCodes(huffmanTreeNode);
        System.out.println("生成的哈夫曼编码表 <=>"+huffmanCodes);

        byte[] zip = zip(bytes, huffmanCodes);
        //[-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28]
        System.out.println("huffmanCodeBytes == "+Arrays.toString(zip));

        byte[] sourceBytes = decode(huffmanCodes, zip);
        System.out.println("原来的字符串时 = "+sourceBytes.toString()+"     "+new String(sourceBytes));

    }
    //编写一个方法 将一个文件进行压缩
    /*
    * srcFile 压缩的源文件路径
    * dstFile 压缩后的文件路径
    * */
    public static void zipFile(String srcFile,String dstFile) throws IOException {
        //创建文件的输入流
        FileInputStream fileInputStream = new FileInputStream(srcFile);
        //创建文件的输出流
        FileOutputStream fileOutputStream = new FileOutputStream(dstFile);
        //创一个和源文件大小相同的byte[] 用来读取文件
        byte[] bytes = new byte[fileInputStream.available()];
        //读取文件
        fileInputStream.read(bytes);
        fileInputStream.close();
        //使用文件对应哈夫曼编码表 进行文件压缩
        byte[] huffmanBytes = huffmanZip(bytes);
        //创建文件的输出流相关联的ObjectOutputStream
        //对象输出流 可以直接输出对象 这里huffmanBytes数组以对象的方式输出出去
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        //以对象流的方法写入huffmanBytes到压缩文件 恢复文件的时候以对象的形式输出
        objectOutputStream.writeObject(huffmanBytes);
        //还要把哈夫曼编码表写入压缩文件☆☆☆☆☆☆☆☆
        objectOutputStream.writeObject(huffmanCodes);

        fileOutputStream.close();
        fileInputStream.close();
        objectOutputStream.close();
    }

    //编写一个方法 完成对压缩文件的解压
    public static void unZipFile(String zipfile,String dstFile) throws IOException {
        //定义文件输入流
        FileInputStream fileInputStream = null;
        //定义一个对象输入流
        ObjectInputStream objectInputStream = null;
        //定义文件的输出流
        FileOutputStream fileOutputStream = null;
        try {
            //创建文件输入流
            fileInputStream = new FileInputStream(zipfile);
            //创建一个和fileInputStream关联的对象输入流
            objectInputStream=new ObjectInputStream(fileInputStream);
            //读取byte数组以对象的形式
            byte[] huffmanBytes = (byte[]) objectInputStream.readObject();
            //读取哈夫曼表以对象的形式
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) objectInputStream.readObject();
            //解码
            byte[] decode = decode(huffmanCodes, huffmanBytes);
            //将bytes数组写入到目标文件
            fileOutputStream=new FileOutputStream(dstFile);
            //写数据到dstFile文件
            fileOutputStream.write(decode);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            fileInputStream.close();
            fileOutputStream.close();
            objectInputStream.close();
        }
    }

    /*
    * 数据的解压
    * 思路：
    * 1.将[-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28]
    * => 哈夫曼对应的二进制字符串“1010100........”
    * 2.哈夫曼对应的二进制字符串 对照哈弗曼编码 转成str="i like like like java do you like a java";
    * */
   /*
   * 1.将[-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28]
   * => 哈夫曼对应的二进制字符串“1010100........”
   * */
    /*
    * b传入的byte
    * flag表示是否需要补高位（如果是最后一个字节 不用补高位）
    * 返回的是 b对应的二进制的字符串（按照补码返回）
    * */
    private static String byteToBitString(boolean flag,byte b){
        //使用变量保存b
        int temp = b;//因为byte类型没有转成二进制并且是字符串类型的方法 Integer有
        //如果temp是正数 还要补高位
        if(flag){//表示是否需要补高位 false是不用补
            temp |= 256;// temp按位或运算
        }
        String str=Integer.toBinaryString(temp);//返回的是temp对应的二进制的补码
        System.out.println("str = "+str);
        //int是占内存4个Byte=32bit 但是现在需要返回倒数的8位 取低8位
        if(flag){
            return str.substring(str.length()-8);
        }else {
            return str;
        }
    }
    /*
    * 2.哈夫曼对应的二进制字符串 对照哈弗曼编码 转成str="i like like like java do you like a java";
    * huffmanCodes哈夫曼表 {32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010, 107=1111, 108=000, 111=0011}
    * huffmanBytes 哈夫曼编码得到的字节数组[-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28]
    * 返回的是原来的字符串所对应的byte[]数组
     *  */
    private static byte[] decode(Map<Byte,String> huffmanCodes,byte[] huffmanBytes){
        //先得到huffmanBytes对应二进制的字符串 形式“1010100........”
        StringBuilder stringBuilder = new StringBuilder();
        //将byte[]数组转成字符串（里面存放二进制）
        for (int i=0;i<huffmanBytes.length;i++){
            byte huffmanByte = huffmanBytes[i];
            //判断是不是最后一个字节
            boolean flag =(i==huffmanBytes.length-1);
            stringBuilder.append(byteToBitString(!flag,huffmanByte));
        }
        System.out.println("哈夫曼字节数组对应的二进制字符串 = "+stringBuilder.toString());
        //把字符串按照指定的哈弗曼编码进行解码
        //把哈夫曼编码表 进行调换 反向查询 以前a->100  现在 100->a
        Map<String,Byte> map =new HashMap<>();
        for(Map.Entry<Byte,String> entry:huffmanCodes.entrySet()){
            map.put(entry.getValue(),entry.getKey());
        }
        System.out.println(map);
        //创建一个集合 存放byte
        ArrayList<Byte> list = new ArrayList<>();
        //i相当一个索引 每次扫描stringBuilder中的一个字符串(一位二进制数) 把当前扫描到的和map中比较 有的话 说明对应一个字符
        for(int i=0;i<stringBuilder.length();/*i++*/){
            int count=1;//一次循环中 i是不动的
            boolean flag =true;
            Byte b=null;
            while (flag){
                //取出stringBuilder中的一个二进制数'1','0'
                //“1010100........” eg：i指向第一个 一开始count=0那么 截取1个 看看这个二进制数是否有对应哈夫曼表
                //没有的话 count++ 从索引指向的位置截取两个二进制数是否有对应哈夫曼表 直到有对应的
                String key=stringBuilder.substring(i,i+count);//i不动 让count移动 直到匹配到一个字符
                b=map.get(key);
                if(b==null){//说明没有匹配到
                    count++;
                }else {//匹配到了 不在循环了
                    flag=false;
                }
            }
            list.add(b);
            //截取到了以后 索引更新 之前截取了i+count也就是索引(i+count-1) 现在从字符串的i+count索引处 截取
            i=i+count;
        }
        //for循环结束后 list存放了所有的字符"i like like like java do you like a java"
        //把list中的数据放入byte[] 并返回
        byte[] bytes = new byte[list.size()];
        for(int i=0; i< bytes.length;i++){
            bytes[i] =list.get(i);
        }
        return bytes;
    }
    /*
    * bytes 原始字符串对应的字节数组
    * 返回的是 经过哈夫曼编码处理后的字节数组（压缩后的数组）
    * 哈夫曼编码
    * */
    //使用一个方法 将前面的方法封装起来 便于调用
    private static byte[] huffmanZip(byte[] bytes){
        List<Node> nodes = getNodes(bytes);
        //可以通过list 创建对应的哈夫曼树
        Node huffmanTreeNode = createHuffmanTree(nodes);
        //根据哈夫曼树 创建对应的哈夫曼编码
        Map<Byte, String> codes = getCodes(huffmanTreeNode);
        //根据生成的哈夫曼编码 压缩得到压缩后的哈夫曼编码字节数组
        byte[] zip = zip(bytes, huffmanCodes);
        return zip;
    }


    //将字符串对应的byte[] 数组 通过生成的哈夫曼编码表 返回一个哈夫曼编码 压缩后的byte[]
    /*bytes 原始的字符串对应的byte[]
    * huffmanCodes 生成的哈夫曼编码map
    *返回哈夫曼的编码处理后 byte[]
    * 8个bit放入一个byte[]数组
    *
    * String str="i like like like java do you like a java";=>byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
    * */
    private static byte[] zip(byte[] bytes,Map<Byte,String> huffmanCodes){
        //利用huffmanCodes将bytes转成哈夫曼编码的字符串
        StringBuilder stringBuilder = new StringBuilder();
        //遍历bytes数组
        for(byte b:bytes){
            stringBuilder.append(huffmanCodes.get(b));
        }
        System.out.println("stringBuilder = "+stringBuilder.toString());
        //将stringBuilder.toString()的字符串转换成byte[] 要不然原先"i like like like java do you like a java"还比stringBuilder的字符串小
        //统计返回的byte[] huffmanCodeBytes长度
        //int len=(stringBuilder.length()+7)/8
        int len;
        if(stringBuilder.length()%8==0){
            len=stringBuilder.length()/8;
        }else {
            len=stringBuilder.length()/8+1;
        }
        //创建存储压缩后的byte数组
        byte[] huffmanCodebytes = new byte[len];
        int index =0;//记录第几个byte
        for (int i=0;i<stringBuilder.length();i=i+8){//每一个byte = 8个bit
            String strByte;
            if(i+8>stringBuilder.length()){//每次循环都会截取8个数组 但是如果最后一次截取没有8个数字的话
                strByte=stringBuilder.substring(i);//最后一次截取 从头截到尾
            }else {
                strByte=stringBuilder.substring(i,i+8);//每一次循环 从stringBuilder里面截取8个字符 当做8bit
            }
            //将strByte 转成一个byte 放入humanCodeBytes
            huffmanCodebytes[index]=(byte)Integer.parseInt(strByte,2);
            index++;
        }
        return huffmanCodebytes;
    }



    //生成哈夫曼树的对应的哈夫曼编码
    //1.将哈夫曼编码表存放在Map<Byte,String>
    static  Map<Byte,String> huffmanCodes=new HashMap<>();
    //2.在生成哈富马编码时 需要去拼接路径(0,1) StringBuilder
    static StringBuilder stringBuilder = new StringBuilder();

    //为了调用方便 重载getCodes
    private static Map<Byte,String> getCodes(Node root){
        if(root == null){
            return null;
        }
        //处理root的左子树
        getCodes(root.left,"0",stringBuilder);
        //处理root的右子树
        getCodes(root.right,"1",stringBuilder);

        return huffmanCodes;
    }
    /*
    * 将传入的node节点的所有叶子节点的哈夫曼编码得到 并放入huffmanCodes
    * node 传入节点
    * code 路径：左子节点：0 右子节点：1 整个路径 每经过一个节点都会有一个数
    * stringBuilder 拼接路径 每经过一个节点都会有一个数都会存在这个变量
    * */
    private static void getCodes(Node node,String code,StringBuilder stringBuilder){
        StringBuilder stringBuilder1 = new StringBuilder(stringBuilder);
        //将传入的code 加入到stringBuilder1
        stringBuilder1.append(code);
        if(node!=null){//node==null不处理
            //判断当前node 是叶子结点 还是非叶子结点
            if(node.data==null){//是非叶子结点
                //继续递归
                //1.向左递归
                getCodes(node.left,"0",stringBuilder1);
                //2.向右递归
                getCodes(node.right,"1",stringBuilder1);
            }else {//说明是一个叶子结点
                //就表示找到某个叶子节点的最后 那么路径也就生成完毕
                //放入map
                huffmanCodes.put(node.data,stringBuilder1.toString());
            }
        }
    }

    //前序遍历的方法
    private static void preOrder(Node root){
        if(root!=null){
            root.preOrder();
        }else {
            System.out.println("哈夫曼树为空");
        }
    }

    private static List<Node> getNodes(byte[] bytes){
        //创建一个ArrayList
        ArrayList<Node> nodes = new ArrayList<>();

        //编列bytes 统计每一个byte出现的次数 通过map(k,v)
        HashMap<Byte, Integer> counts = new HashMap<>();
        for(byte b: bytes){
            Integer count =counts.get(b);
            if(count==null){//map之前还没有这个字符 第一次
                counts.put(b,1);
            }else {
                counts.put(b,count+1);//每一次出现 计数+1
            }
        }
        //把每个键值对转成Node对象 加入到nodes集合
        for (Map.Entry<Byte,Integer> entry:counts.entrySet()){
            nodes.add(new Node(entry.getKey(),entry.getValue()));
        }
        return nodes;
    }
    //可以通过list 创建对应的哈夫曼树
    private static Node createHuffmanTree(List<Node> nodes){
        while (nodes.size()>1){
            //排序 从小到大
            Collections.sort(nodes);
            //取出第一个最小的二叉树
            Node left = nodes.get(0);
            //取出第二小的二叉树
            Node right = nodes.get(1);
            //创建一颗新的二叉树 它的根节点 没有data 只有权值
            Node parent=new Node(null,left.getWeight()+right.getWeight());
            parent.setLeft(left);
            parent.setRight(right);

            //将已经处理的二叉树从nodes删除
            nodes.remove(left);
            nodes.remove(right);

            //将新的二叉树 填入nodes
            nodes.add(parent);
        }
        //最后返回的节点就是huffman的根节点
        return nodes.get(0);
    }
}
class Node implements Comparable<Node>{
    Byte data;//存放数据 'a'或者97
    int weight;//权值 字符出现的次数
    Node left;
    Node right;

    public Byte getData() {
        return data;
    }

    public void setData(Byte data) {
        this.data = data;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node o) {
        return this.weight-o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }
    //前序遍历
    public void preOrder(){
        System.out.println(this);
        if(this.left!=null){
            this.left.preOrder();
        }
        if (this.right!=null){
            this.right.preOrder();
        }
    }
}