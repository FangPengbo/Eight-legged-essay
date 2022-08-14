package top.ddddddddd.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/14/23:06
 * @Description:
 */
public class ArrayListDemo {


    public static void main(String[] args) {

        //初始化list
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>(100);
        List<Integer> list3 = new ArrayList<>(Arrays.asList(1,2,3));
        List<Integer> list4 = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));

        //扩容机制
        //list1: 0 -> 10 -> 15 -> 22 -> ((n + n>>1) <=> (n + n/2)) ...
        //list2: 100 -> 150 -> 225 -> ((n + n>>1) <=> (n + n/2)) ...
        //list3: Math(10,3)=10 -> 15 ->...
        //list4: Math(10,12)=12 -> 18 ->...


    }


}
