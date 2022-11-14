package StringCompare;

import java.io.*;
import java.util.*;

/*
       check the code duplications by character;
     */
public class CharacterCompare {
    List<String> keyword_list = new ArrayList<>();
    String[] keyword = {"auto", "break", "case", "char", "const", "continue", "default",
            "do", "double", "else", "enum", "extern", "float", "for", "goto", "if", "int",
            "long", "register", "return", "short", "signed", "sizeof", "static", "struct", "switch",
            "typedef", "union", "unsigned", "void", "volatile", "while"};
    private File file1 = null;
    private File file2 = null;
    private FileInputStream input1 = null;
    private FileInputStream input2 = null;

    /*
        构造器
     */
    public CharacterCompare(String filepath1, String filepath2) throws FileNotFoundException {

        file1 = new File(filepath1);
        file2 = new File(filepath2);

        if ((file1 != null) && (file2 != null)) {

            input1 = new FileInputStream(file1);
            input2 = new FileInputStream(file2);
        }
        //add the keyword into the list
        keyword_list.addAll(Arrays.asList(keyword));
    }
    /*
        read file
        @return:
            map has two arrays of char int files
     */
    public Map<String,char[]> readFile() {
        char[] array1_char = new char[(int) file1.length()];
        char[] array2_char = new char[(int) file1.length()];
        int temp;
        int i = 0;
        try {
            //读取第一个文件
            while ((temp = input1.read()) != -1) {
                array1_char[i] = (char) temp;
                i++;
            }
            i = 0;
            while ((temp = input2.read()) != -1) {
                array2_char[i] = (char) temp;
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, char[]> map = null;
        if (array1_char.length != 0 && array2_char.length != 0) {
            map = new HashMap<>();
            map.put("array1", array1_char);
            map.put("array2", array2_char);
        }
        return map;
    }
    /*
        在代码文件中移除空格与换行符,生成字符数组  'abc___'
     */
    public Map<String, char[]> deleteSpace(Map<String,char[]>map){
        char [] array1 = map.get("array1");
        char [] array2 = map.get("array2");
        for (int i=0;i<array1.length;i++){
            char temp = array1[i];
            if(i==array1.length-1){
                break;
            }
            if(temp==' '||temp=='\t'||temp=='\n'||temp=='\r'){

                int num_temp = i+1;
                if(num_temp==array1.length-1){
                    break;
                }
                while(array1[num_temp]==' ' || array1[num_temp]=='\t' || array1[num_temp] =='\n'||array1[num_temp]=='\r') {
                    num_temp++;
                    if(num_temp==array1.length-1){
                        break;
                    }

                }
                array1[i] = array1[num_temp];
                array1[num_temp] = ' ';
            }
        }

        for (int i=0;i<array2.length;i++){
            char temp = array2[i];
            if(i==array2.length-1){
                break;
            }
            if(temp==' '||temp=='\t'||temp=='\n'||temp=='\r'){

                int num_temp = i+1;
                if(num_temp==array2.length-1){
                    break;
                }
                while(array2[num_temp]==' ' || array2[num_temp]=='\t' || array2[num_temp] =='\n'||array2[num_temp]=='\r') {
                    num_temp++;
                    if(num_temp==array2.length-1){
                        break;
                    }

                }
                array2[i] = array2[num_temp];
                array2[num_temp] = ' ';
            }
        }
        map.replace("array1",array1);
        map.replace("array2",array2);
        return map;
    }

    /*
        比较两份代码中的字符是否相同(比较字符序列)
     */
    public double compare_character() {
        Map<String,char[]> map = readFile();
        //移除代码中所有的空格符和换行符
        map = deleteSpace(map);
        char [] array1 = map.get("array1");
        char [] array2 = map.get("array2");

        //进行字符匹配
        int length1 =0;
        int length2 =0;
        for(int i=0;i<array1.length;i++){
            if(array1[i]==' '||array1[i]=='\t'||array1[i]=='\n'||array1[i]=='\r'){
                length1 = i;
                break;
            }
        }
        for(int i=0;i<array2.length;i++){
            if(array2[i]==' '||array2[i]=='\t'||array2[i]=='\n'||array2[i]=='\r'){
                length2 = i;
                break;
            }
        }
        //获取比较长度
        int length = Math.min(length1, length2);
        int count_same_char=0;
        for (int i=0;i<length;i++){
            if(array1[i] == array2[i]){
                count_same_char++;
            }
        }
        return (double) count_same_char / length;//返回相同字符在所有字符中所占的比例
    }

    /*
        通过定位关键字所在的位置来比较代码的相似度
     */
    public double keyword_match(){
        String temp_str="";

        Map<String,char[]> map = readFile();
        char [] array1 = map.get("array1");
        char [] array2 = map.get("array2");
        String str1 = new String(array1);
        String str2 = new String(array2);


        //array1
        //去除 换行符 留下空格作为关键字的分割 windows系统下标识换行是\r\n
        List<String>list1;
        list1 = List.of(str1.split("\r\n"));

        for (String str:list1
             ) {
//            str = str.substring(0,str.length()-1);
            temp_str += str;
        }

//        System.out.println(temp_str);
        /*
            空格处理：按照空格分割
         */
        // 按照空格分割识别code字段
        list1=List.of(temp_str.split("\\s+"));

        Map<String, int[]>map_keyword_location_code1=new HashMap<>();
        for(String str_keyword:keyword_list){
            //遍历每一个关键字，为其生成对应的位置数组
            int []location =new int[list1.size()];
            int temp=0;
            for(int i=0;i<list1.size();i++){
                location[i]=-1;
            }//初始化

            for(String str:list1){
                //遍历代码list
                if(str.toLowerCase().contains(str_keyword.toLowerCase())){//匹配关键字
                    //当前代码字段下含有某个关键字
                    //获取该关键字在代码中的下标
                    int index = list1.indexOf(str);
                    //将该下标添加到Location数组中
                    location[temp++]=index;
                }
            }//end for in code

            //为当前关键字连接其对应的代码中下标数组 该map中可能存在重复的关键字
            map_keyword_location_code1.put(str_keyword,location);
        }//end for 将代码中所有的关键字全部统计完毕 形成map of keyword __ location


        //array2
        temp_str ="";
        //去除换行符仅留下空格作为关键字的分割
        List<String>list2;
        list2 = List.of(str2.split("\n"));
        for (String str:list2
        ) {
            str = str.substring(0,str.length()-1);
            temp_str += str;
        }

//        System.out.println(temp_str);
        /*
            空格处理：按照空格分割
         */
        list2=List.of(temp_str.split("\\s+"));

        Map<String, int[]>map_keyword_location_code2=new HashMap<>();
        for(String str_keyword:keyword_list){
            //遍历每一个关键字，为其生成对应的位置数组
            int []location =new int[list2.size()];
            int temp=0;
            for(int i=0;i< location.length;i++){
                location[i]=-1;
            }//初始化

            for(String str:list2){
                //遍历代码list
                if(str.toLowerCase().contains(str_keyword.toLowerCase())){
                    //当前代码字段下含有某个关键字
                    //获取该关键字在代码中的下标
                    int index = list2.indexOf(str);
                    //将该下标添加到Location数组中
                    location[temp++]=index;
                }
            }//end for in code

            //为当前关键字连接其对应的代码中下标数组 该map中可能存在重复的关键字
            map_keyword_location_code2.put(str_keyword,location);
        }//end for 将代码中所有的关键字全部统计完毕 形成map of keyword __ location

        // 比较两份代码中keyword出现的位置
        /*
            设置一个权重weight,记录关键字在相同位置出现的次数 设置num1、num2记录关键字出现的次数 2*weigth/(num1+num2) 是评价指标
         */
        int weigth=0;
        int num1=0;
        int num2=0;
        for (String keyword:keyword_list
             ) {
            //遍历所有关键字
            int []location_array1 = map_keyword_location_code1.get(keyword);
            int []location_array2 = map_keyword_location_code2.get(keyword);
            int length = Math.min(list1.size(), list2.size());
            for (int i=0;i<length;i++){
                if(location_array1[i] == location_array2[i]){

                    if(location_array1[i] != -1){
                        //此处出现关键字且相同
                        weigth++;
                        num1++;
                        num2++;
                    }
                } else {
                    //此位置不相同(一个出现了关键字、另一个没有、或者两者都出现关键字但不同)
                    if(location_array1[i]!=-1) {
                        num1++;
                    }

                    if (location_array2[i] != -1) {
                        num2++;
                    }
                }
            }//end for
        }//end for

        //计算相似度
        double degree_similarity = (double) (2*weigth)/(num1+num2);
        return degree_similarity;
    }

}
