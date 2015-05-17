package com.gracker.javareflect;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            demo0();
            demo1();
            demo2();
            demo3();
            demo4();
            demo5();
            demo6();
            demo7();
        } catch (ClassNotFoundException
                | InvocationTargetException
                | IllegalAccessException
                | InstantiationException
                | NoSuchFieldException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 正常加载Person类
     */
    private void demo0() {
        System.out.println("====================Demo0======================");
        TimeUtils.start();
        Person mPerson = new Person();
        mPerson.setAge("28");
        mPerson.setName("Xiao Ming");
        System.out.println(mPerson.toString() + " Time = " + TimeUtils.end());
        System.out.println("===============================================");
    }

    /**
     * 演示反射的两种写法
     * @throws ClassNotFoundException
     */
    private void demo1() throws ClassNotFoundException {
        //定义两个类型都未知的Class , 设置初值为null, 看看如何给它们赋值成Person类
        Class<?> class1;
        Class<?> class2;

        //写法1, 可能抛出 ClassNotFoundException [多用这个写法]
        class1 = Class.forName("com.gracker.javareflect.Person");
        System.out.println("====================Demo1======================");

        System.out.println("Demo1:(写法1) 包名: " + class1.getPackage().getName() + "，"
                + "完整类名: " + class1.getName());

        //写法2
        class2 = Person.class;
        System.out.println("Demo1:(写法2) 包名: " + class2.getPackage().getName() + "，"
                + "完整类名: " + class2.getName());
        System.out.println("===============================================");
    }

    /**
     * Demo2: 通过Java反射机制，用Class 创建类对象[这也就是反射存在的意义所在]
     *
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void demo2() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        TimeUtils.start();
        Class<?> class1;
        class1 = Class.forName("com.gracker.javareflect.Person");
        //由于这里不能带参数，所以你要实例化的这个类Person，一定要有无参构造函数，后续的Demo会演示如何调用带参数的构造函数
        Person person = (Person) class1.newInstance();
        person.setAge("20");
        person.setName("Gao Jianwu");

        System.out.println("====================Demo2======================");
        System.out.println("Demo2 构造没有参数的对象: " + person.getName() + " : " + person.getAge());
        System.out.println("Time = " + TimeUtils.end());
        System.out.println("===============================================");
    }


    /**
     * Demo3: 通过Java反射机制得到一个类的构造函数，并实现创建带参实例对象
     *
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IllegalArgumentException
     */
    public static void demo3() throws ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> class1;
        Person person1;
        Person person2;

        class1 = Class.forName("com.gracker.javareflect.Person");
        //得到一系列构造函数集合
        Constructor<?>[] constructors = class1.getConstructors();

        person1 = (Person) constructors[0].newInstance();
        person1.setAge("22");
        person1.setName("Gao Jianwu");


        person2 = (Person) constructors[1].newInstance("20", "Sun Yibo");

        System.out.println("=====================Demo3=====================");
        System.out.println("demo3 通过反射构造带参数的对象: " + person1.getName() + " : " + person1.getAge()
                        + "  ,   " + person2.getName() + " : " + person2.getAge()
        );
        System.out.println("===============================================");
    }

    /**
     * Demo4: 通过Java反射机制操作成员变量, set 和 get
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public static void demo4() throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException, InstantiationException, ClassNotFoundException {
        Class<?> class1;
        class1 = Class.forName("com.gracker.javareflect.Person");
        Object obj = class1.newInstance();

        Field personNameField = class1.getDeclaredField("name");
        personNameField.setAccessible(true);
        personNameField.set(obj, "Cui Yingyun");

        Field personAgeField = class1.getDeclaredField("age");
        personAgeField.setAccessible(true);
        personAgeField.set(obj, "30");

        System.out.println("======================Demo4=====================");
        System.out.println("demo4: 修改属性之后得到属性变量的值：Name = " + personNameField.get(obj));
        System.out.println("demo4: 修改属性之后得到属性变量的值：Age = " + personAgeField.get(obj));
        System.out.println("demo4: 修改属性之后得到属性变量的值：Person = " + obj.toString());
        System.out.println("===============================================");

    }

    /**
     * demo5: 通过Java反射机制得到类的一些属性： 继承的接口，父类，函数信息，成员信息，类型等
     *
     * @throws ClassNotFoundException
     */
    public static void demo5() throws ClassNotFoundException {
        Class<?> class1;
        class1 = Class.forName("com.gracker.javareflect.SuperMan");

        //取得父类名称
        Class<?> superClass = class1.getSuperclass();

        System.out.println("=====================Demo5=====================");

        System.out.println("demo5:  SuperMan类的父类名: " + superClass.getName());


        Field[] fields = class1.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("类中的成员: " + field);
        }

        //取得类方法

        Method[] methods = class1.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("demo5,取得SuperMan类的方法：");
            System.out.println("函数名：" + method.getName());
            System.out.println("函数返回类型：" + method.getReturnType());
            System.out.println("函数访问修饰符：" + Modifier.toString(method.getModifiers()));
            System.out.println("函数代码写法： " + method);
        }


        //取得类实现的接口,因为接口类也属于Class,所以得到接口中的方法也是一样的方法得到哈
        Class<?> interfaces[] = class1.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            System.out.println("实现的接口类名: " + anInterface.getName());
        }
        System.out.println("===============================================");

    }

    /**
     * Demo6: 通过Java反射机制调用类方法
     *
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     */
    public static void demo6() throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> class1;
        class1 = Class.forName("com.gracker.javareflect.SuperMan");
        System.out.println("=====================Demo6=====================");

        System.out.println("Demo7: \n调用无参方法fly()：");
        Method method = class1.getMethod("fly");
        method.invoke(class1.newInstance());

        System.out.println("调用有参方法walk(int m)：");
        method = class1.getMethod("fly", int.class);
        method.invoke(class1.newInstance(), 100);
        System.out.println("===============================================");

    }

    /**
     * Demo7: 通过Java反射机制得到类加载器信息
     * <p/>
     * 在java中有三种类类加载器。[这段资料网上截取]
     * <p/>
     * 1）Bootstrap ClassLoader 此加载器采用c++编写，一般开发中很少见。
     * <p/>
     * 2）Extension ClassLoader 用来进行扩展类的加载，一般对应的是jre\lib\ext目录中的类
     * <p/>
     * 3）AppClassLoader 加载classpath指定的类，是最常用的加载器。同时也是java中默认的加载器。
     *
     * @throws ClassNotFoundException
     */
    public static void demo7() throws ClassNotFoundException {
        System.out.println("=====================Demo7=====================");

        Class<?> class1;
        class1 = Class.forName("com.gracker.javareflect.SuperMan");
        String nameString = class1.getClassLoader().getClass().getName();

        System.out.println("Demo8: 类加载器类名: " + nameString);
        System.out.println("================================================");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
