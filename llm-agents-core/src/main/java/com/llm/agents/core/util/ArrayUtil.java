package com.llm.agents.core.util;

import java.util.Arrays;
import java.util.Objects;

public class ArrayUtil {

    private ArrayUtil() {
    }


    /**
     * 判断数组是否为空
     *
     * @param array 数组
     * @param <T>   数组类型
     * @return {@code true} 数组为空，{@code false} 数组不为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }


    /**
     * 判断数组是否不为空
     *
     * @param array 数组
     * @param <T>   数组类型
     * @return {@code true} 数组不为空，{@code false} 数组为空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }


    /**
     * 合并两个数组为一个新的数组
     *
     * @param first  第一个数组
     * @param second 第二个数组
     * @param <T>
     * @return 新的数组
     */
    public static <T> T[] concat(T[] first, T[] second) {
        if (first == null && second == null) {
            throw new IllegalArgumentException("not allow first and second are null.");
        } else if (isEmpty(first) && second != null) {
            return second;
        } else if (isEmpty(second)) {
            return first;
        } else {
            T[] result = Arrays.copyOf(first, first.length + second.length);
            System.arraycopy(second, 0, result, first.length, second.length);
            return result;
        }
    }


    public static <T> T[] concat(T[] first, T[] second, T[] third, T[]... others) {
        T[] results = concat(first, second);
        results = concat(results, third);

        if (others != null && others.length > 0) {
            for (T[] other : others) {
                results = concat(results, other);
            }
        }
        return results;
    }


    /**
     * 可变长参形式数组
     *
     * @param first  第一个数组
     * @param second 第二个数组
     * @param <T>
     * @return 新的数组
     */
    @SafeVarargs
    public static <T> T[] append(T[] first, T... second) {
        if (first == null && second == null) {
            throw new IllegalArgumentException("not allow first and second are null.");
        } else if (isEmpty(first) && second != null) {
            return second;
        } else if (isEmpty(second)) {
            return first;
        } else {
            T[] result = Arrays.copyOf(first, first.length + second.length);
            System.arraycopy(second, 0, result, first.length, second.length);
            return result;
        }
    }


    /**
     * 查看数组中是否包含某一个值
     *
     * @param arrays 数组
     * @param object 用于检测的值
     * @param <T>
     * @return true 包含
     */
    public static <T> boolean contains(T[] arrays, T object) {
        if (isEmpty(arrays)) {
            return false;
        }
        for (T array : arrays) {
            if (Objects.equals(array, object)) {
                return true;
            }
        }
        return false;
    }


}
