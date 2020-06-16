package com.dsg.hotfixdemo;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

/**
 * @author DSG
 * @Project HotFixDemo
 * @date 2020/6/12
 * @describe
 */
public class Fix {


    public static void Fix(ClassLoader classLoader, Context context) {
        try {
            //1.0 获取 pathClassloader DexElements对象
            Object pathPathList = getPathList(classLoader);
            Object pathDexElements = getDexElements(pathPathList);

            //1.1 获取  修复Dex classLoader
            String dexDirPath = context.getExternalFilesDir(null) + File.separator + "fix.dex";
            if (!new File(dexDirPath).exists()) {
                return;
            }
            String optimizedDirectoryPath = context.getExternalFilesDir(null) + File.separator + "optimize_dex";
            DexClassLoader dexClassLoader = new DexClassLoader(dexDirPath, optimizedDirectoryPath, null, classLoader);

            //1.2 获取  DexClassLoader DexElements对象
            Object dexPathList = getPathList(dexClassLoader);
            Object dexDexElements = getDexElements(dexPathList);

            //2.0 合并两个数组
            Object dexElements = combineArray(dexDexElements, pathDexElements);

            //3.0 将pathClassLoader 修改为dexElements
            setField(pathPathList, pathPathList.getClass(), "dexElements", dexElements);
            Log.e("dsg", "current");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Toast.makeText(context, "修复完成", Toast.LENGTH_SHORT).show();
    }


    private static Object getPathList(Object obj) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        return getField(obj, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
    }

    private static Object getDexElements(Object obj) throws NoSuchFieldException, IllegalAccessException {
        return getField(obj, obj.getClass(), "dexElements");
    }


    private static Object getField(Object obj, Class<?> cls, String fieldStr) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = cls.getDeclaredField(fieldStr);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    private static void setField(Object object, Class<?> cls, String fieldStr, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = cls.getDeclaredField(fieldStr);
        declaredField.setAccessible(true);
        declaredField.set(object, value);
    }

    /**
     * 数组合并
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> clazz = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);// 得到左数组长度（补丁数组）
        int j = Array.getLength(arrayRhs);// 得到原dex数组长度
        int k = i + j;// 得到总数组长度（补丁数组+原dex数组）
        Object result = Array.newInstance(clazz, k);// 创建一个类型为clazz，长度为k的新数组
        System.arraycopy(arrayLhs, 0, result, 0, i);
        System.arraycopy(arrayRhs, 0, result, i, j);
        return result;
    }

}
