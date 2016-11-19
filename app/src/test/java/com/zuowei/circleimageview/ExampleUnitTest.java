package com.zuowei.circleimageview;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void minTest(){
        long start = System.currentTimeMillis();
        System.out.println(min(0.2f,6f,8f,9.8f,0f));
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        System.out.println(min_2(0.2f,6f,8f,9.8f,0f));
        System.out.println(System.currentTimeMillis() - start);
    }

    private float min(float... values){
        if (values.length == 1){
            return values[0];
        }
        float [] newValues = Arrays.copyOf(values,values.length - 1);
        newValues[newValues.length - 1] = Math.min(values[values.length - 1],values[values.length - 2]);
        return min(newValues);
    }
    private float min_2(float... values){
        float min = values[0];
        for (int i = 0;i < values.length;i++){
            min = Math.min(values[i],min);
        }
        return min;
    }

}