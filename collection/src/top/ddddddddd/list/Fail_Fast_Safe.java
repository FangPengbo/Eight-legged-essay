package top.ddddddddd.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/14/23:37
 * @Description:
 */
public class Fail_Fast_Safe {


    public static void main(String[] args) {
        //fail-fast 一旦发现遍历的同时其他人来修改，则立刻抛异常
        //fail-safe 发现遍历的同时其它人来修改，应当能有应对策略，例如牺牲一致性来人昂整个遍历运行完成

        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.forEach(el ->{
            System.out.println(el);
            //循环过程中对数组进行修改会抛出java.util.ConcurrentModificationException异常
            //原因是，在ArrayList使用迭代器迭代的时候，开始的时候记录了原List中modCount值，每次遍历的时候会先比较这个值是否发生了变化，发生了就抛出异常
            arrayList.add(100);
        });


        List<Integer> cowList = new CopyOnWriteArrayList<>();
        cowList.add(1);
        cowList.add(2);
        cowList.add(3);
        cowList.forEach(el ->{
            System.out.println(el);
            //这里不会抛出异常，是因为CopyOnWriteArrayList在创建迭代器的时候，copy了一份原数组，去遍历它，原数组add的时候不会影响迭代器内部的数组
            cowList.add(4);
        });

    }

}
