package com.example.testcoroutine.排序算法;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CharFuncUtil {

    private static void printChars(char[] host){
        if (host.length <= 0)return;
        Log.e("*****","printMessage");
        System.out.println(host);
    }

    private static void replaceBlankWithChar(char[] host,char[] replace,int hostLength,int replaceLength){
        if (hostLength == 0 || replaceLength == 0)return;
        int blackCharLength = 0;
        for (char c:host){
            if (c == ' '){
                blackCharLength += (replaceLength-1);
            }
        }
        int finalLength = hostLength + blackCharLength;
        char[] finalChars = new char[finalLength];
        int i = finalLength - 1,j = hostLength - 1;
        while (i >= 0 && j >= 0){
            if (host[j] == ' '){
                for (int k = replaceLength - 1; k >=0; k--){
                    finalChars[i--] = replace[k];
                }
            } else {
                finalChars[i--] = host[j];
            }
            j--;
        }
        printChars(finalChars);
    }

    //替换字符串中的空格
    public static void replaceChar(){
        char[] source = "I am sentence with space".toCharArray();
        char[] replace = "%20".toCharArray();
        replaceBlankWithChar(source,replace,source.length,replace.length);
    }


    private static void firstCharWithOnce(char[] origin,int length){
        if (length == 1) return;
        char c = origin[0];
        int[] charMaps = new int[256];
        for(int i : charMaps){
            i = 0;
        }
        for (int i = 0;i < length;i++){
            charMaps[origin[i]] += 1;
        }
        for (int i = 0;i < length;i++){
            if (charMaps[origin[i]] == 1){
                c = origin[i];
            }
        }
        System.out.println("***************");
        System.out.println(c);
    }

    //第一个只出现一次的字符
    public static void firstCharFind(){
        char[] source = "abaccdeff".toCharArray();
        firstCharWithOnce(source, source.length);
    }

    //翻转句子
    private static void advertSub(char[] origin,int start,int end){
        int i = start,j =end;
        char temp;
        while (i < j){
            temp = origin[i];
            origin[i] = origin[j];
            origin[j] = temp;
            i++;
            j--;
        }
    }

    public static void advertSentence(){
        char[] host = "I am a original string".toCharArray();
        int length = host.length;
        advertSub(host,0, length-1);
        int startC = 0,endC = 0;
        while (startC < length && endC < length){
            if (host[startC] == ' '){
                startC ++;
                endC ++;
            } else if (host[endC] == ' ' || host[endC] == '\0'){
                advertSub(host,startC,--endC);
                startC = ++endC;
            } else {
                endC++;
            }
        }
        printChars(host);
    }

    private static void permutationStr(char p[], int depth, int length){
        if (depth == length) {
            System.out.println(p);
            return;
        }
        char c;
        // 0，3
        for (int i = depth; i < length; i++){
            c = p[depth]; p[depth] = p[i]; p[i] = c;
            permutationStr(p, depth+1, length);
            c = p[depth]; p[depth] = p[i]; p[i] = c;
        }
    }

//打印·所有字符组合
    public static void permutationStr(){
        char[] p = "ab".toCharArray();
        permutationStr(p,0,p.length);
    }


    //获取字符串中最长的相同的字符
    static void quickSortStr(List<String> c, int start, int end){
        if(start >= end)
            return;
        int pStart = start;
        int pEnd = end;
        int pMid = start;
        String t = null;
        for (int j = pStart+1; j <= pEnd; j++) {
            if ((c.get(pStart)).compareTo(c.get(j)) > 0) {
                pMid++;
                t = c.get(pMid);
                c.set(pMid, c.get(j));
                c.set(j, t);
            }
        }
        t = c.get(pStart);
        c.set(pStart, c.get(pMid));
        c.set(pMid, t);
        quickSortStr(c, pStart, pMid-1);
        quickSortStr(c, pMid+1, pEnd);
    }

    //获得两个字符串从第一个字符开始，相同部分的最大长度。
    static int comLen(String p1, String p2){
        int count = 0;
        int p1Index = 0;
        int p2Index = 0;
        while (p1Index < p1.length()) {
            if (p1.charAt(p1Index++) != p2.charAt(p2Index++))
                return count;
            count++;
        }
        return count;
    }

    static String longComStr(String p, int length){
        List<String> dic = new ArrayList<String>();
        int ml = 0 ;
        for (int i = 0; i < length; i++) {
            if (p.charAt(i) != ' ') {
                //构造所有的后缀数组。
                dic.add(p.substring(i, p.length()));
            }
        }
        String mp = null;
        //对后缀数组进行排序。
        quickSortStr(dic, 0, dic.size()-1);
        //打印排序后的数组用于调试。
        for (int i = 0; i < dic.size(); i++) {
            System.out.println("index=" + i + ",data=" + dic.get(i));
        }
        for (int i = 0; i < dic.size()-1; i++) {
            int tl = comLen(dic.get(i), dic.get(i+1));
            if (tl > ml) {
                ml = tl;
                mp = dic.get(i).substring(0, ml);
            }
        }
        return mp;
    }

    public static void getSameChar(){
        String source = "Ask not what your country can do for you, but what you can do for your country";
        System.out.println("result = " + longComStr(source, source.length()));
    }

    //将字符串中的 * 移到前部，并且不改变非 * 的顺序
    public static void moveSpecialChar(){
        char[] source = "ab**cd**e*12".toCharArray();
        int lastCharIndex = source.length;
        char temp, c;
        for (int i = source.length-1; i >=0; i--){
            if ((c = source[i]) != '*'){
                lastCharIndex--;
                temp = source[lastCharIndex];
                source[lastCharIndex] = c;
                source[i] = temp;
            }
        }
        printChars(source);
    }

    //不开辟用于交换的空间，完成字符串的逆序（C++）,这里利用的是两次亦或等于本身的思想.
    public static void revertWithoutTemp(){
        char[] p = "1234567".toCharArray();
        int i = 0;
        int j = p.length - 1;
        while (i < j){
            p[i] = (char) (p[i]^p[j]);
            p[j] = (char)(p[i] ^ p[j]);
            p[i] = (char)(p[i] ^ p[j]);
            i++;
            j--;
        }
        printChars(p);
    }



}
