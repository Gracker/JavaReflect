package com.gracker.javareflect;

import android.annotation.TargetApi;
import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final String ClASS_NAME = "com.gracker.javareflect.Person";
    public static final String ClASS_NAME_SUPERMAN = "com.gracker.javareflect.SuperMan";
    public static final String ClASS_NAME_SYSTEMUI_RECENT = "com.android.systemui.mzrecent.DragView";
    public static final String ClASS_NAME_SYSTEM_WIDGET = "android.view.Choreographer";
    private static final int RECYCLE_TIME = 100000;

    private TextView mTextView;
    private int demoIndex = 0;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.txt_reflect);

        testDemo(9);
    }

    private void testDemo(int demoIndex) {
        this.demoIndex = demoIndex;
        new Handler().postDelayed(r, 1000);
    }


    Runnable r = new Runnable() {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            switch (demoIndex) {
                case 0:
                    demo0();
                    break;
                case 1:
                    try {
                        demo1();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        demo2();
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        demo3();
                    } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        demo4();
                    } catch (IllegalAccessException | ClassNotFoundException | NoSuchFieldException | InstantiationException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    try {
                        demo5();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    try {
                        demo6();
                    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    try {
                        demo7();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 8:
                    try {
                        demo8();
                    } catch (ClassNotFoundException | NoSuchFieldException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                case 9:
                    try {
                        demo9();
                    } catch (ClassNotFoundException | NoSuchFieldException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                case 10:
                    try {
                        demo10();
                    } catch (ClassNotFoundException | NoSuchFieldException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                default:
                    break;
            }
        }
    };

    /**
     * 正常加载Person类
     */
    private void demo0() {
        TimeUtils.start();

        Person mPerson = new Person();
        mPerson.setAge("28");
        mPerson.setName("Xiao Ming");

        String result = "Demo0 : \n " + " " + mPerson.toString() + " \n  Time = " + TimeUtils.end();
        mTextView.setText(result);
    }

    /**
     * 演示反射的两种写法
     *
     * @throws ClassNotFoundException
     */
    private void demo1() throws ClassNotFoundException {
        //定义两个类型都未知的Class , 设置初值为null, 看看如何给它们赋值成Person类

        TimeUtils.start();
        Class<?> class1;
        Class<?> class2;

        //写法1, 可能抛出 ClassNotFoundException [多用这个写法]
        class1 = Class.forName(ClASS_NAME);

        String result1 = "\n*************\n"
                + "Demo1.1 : get package name and class name  only\n "
                + " PackageName = " + class1.getPackage().getName() + "\n"
                + " ClassName = " + class1.getName() + "\n"
                + " Time = " + TimeUtils.end();
        mTextView.append(result1);


        //写法2
        TimeUtils.start();

        class2 = Person.class;

        String result2 = "\n*************\n"
                + "Demo1.2 : get package name and class name  only\n "
                + " PackageName = " + class2.getPackage().getName() + "\n"
                + " ClassName = " + class2.getName() + "\n"
                + " Time = " + TimeUtils.end();
        mTextView.append(result2);
    }

    /**
     * Demo2: 通过Java反射机制，用Class 创建类对象[这也就是反射存在的意义所在]
     *
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void demo2() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        TimeUtils.start();
        Class<?> class1;
        class1 = Class.forName(ClASS_NAME);
        //由于这里不能带参数，所以你要实例化的这个类Person，一定要有无参构造函数，后续的Demo会演示如何调用带参数的构造函数
        Person person = (Person) class1.newInstance();
        person.setAge("20");
        person.setName("Xiao Ming");

        String result = "\n ************* \n"
                + "Demo2 : new instance with relfect " + "\n"
                + " Person : " + person.toString() + "\n"
                + " Time = " + TimeUtils.end();
        mTextView.append(result);
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
    public void demo3() throws ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        TimeUtils.start();
        Class<?> class1;
        Person person1;

        class1 = Class.forName(ClASS_NAME);
        //得到一系列构造函数集合
        Constructor<?>[] constructors = class1.getConstructors();

        person1 = (Person) constructors[0].newInstance();
        person1.setAge("22");
        person1.setName("Gao Jianwu");

        String result1 = "\n ************* \n"
                + "Demo3.1 : new instance with constructor " + "\n"
                + " Person : " + person1.toString() + "\n"
                + " Time = " + TimeUtils.end();
        mTextView.append(result1);


        TimeUtils.start();
        Person person2;
        person2 = (Person) constructors[1].newInstance("20", "Sun Yibo");


        String result2 = "\n ************* \n"
                + "Demo3.2 : new instance with constructor " + "\n"
                + " Person : " + person2.toString() + "\n"
                + " Time = " + TimeUtils.end();
        mTextView.append(result2);
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
    public void demo4() throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException, InstantiationException, ClassNotFoundException {

        TimeUtils.start();
        Class<?> class1;
        class1 = Class.forName(ClASS_NAME);
        Object obj = class1.newInstance();

        Field personNameField = class1.getDeclaredField("name");
        personNameField.setAccessible(true);
        personNameField.set(obj, "Xiao Hong");

        Field personAgeField = class1.getDeclaredField("age");
        personAgeField.setAccessible(true);
        personAgeField.set(obj, "30");

        String result = "\n ************* \n"
                + "Demo4 : field get and set " + "\n"
                + " Person : " + "Name = " + personNameField.get(obj)
                + " Age = " + personAgeField.get(obj) + "\n"
                + " Time = " + TimeUtils.end();
        mTextView.append(result);
    }

    /**
     * demo5: 通过Java反射机制得到类的一些属性： 继承的接口，父类，函数信息，成员信息，类型等
     *
     * @throws ClassNotFoundException
     */
    public static void demo5() throws ClassNotFoundException {
        Class<?> class1;
        class1 = Class.forName(ClASS_NAME);

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
    public void demo6() throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TimeUtils.start();
        Class<?> class1;
        class1 = Class.forName(ClASS_NAME_SUPERMAN);

        Method method = class1.getMethod("fly");
        method.invoke(class1.newInstance());

        String result1 = "\n ************* \n"
                + "Demo6.1 : invoke method " + "\n"
                + " Time = " + TimeUtils.end();
        mTextView.append(result1);


        TimeUtils.start();
        Class<?> class2;
        class2 = Class.forName(ClASS_NAME_SUPERMAN);

        method = class2.getMethod("fly", int.class);
        method.invoke(class2.newInstance(), 100);

        String result2 = "\n ************* \n"
                + "Demo6.2 : invoke method " + "\n"
                + " Time = " + TimeUtils.end();
        mTextView.append(result2);

    }

    /**
     * Demo7: 通过Java反射机制得到类加载器信息
     *
     * @throws ClassNotFoundException
     */
    public static void demo7() throws ClassNotFoundException {
        System.out.println("=====================Demo7=====================");

        Class<?> class1;
        class1 = Class.forName(ClASS_NAME_SUPERMAN);
        String nameString = class1.getClassLoader().getClass().getName();

        System.out.println("Demo8: 类加载器类名: " + nameString);
        System.out.println("================================================");

    }

    /**
     * 通过反射调用系统类
     *
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void demo8() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        TimeUtils.start();
        Class<?> class1;

        class1 = Class.forName(ClASS_NAME_SYSTEM_WIDGET);

        Field personNameField = class1.getDeclaredField("USE_VSYNC");

        String result = "\n*************\n"
                + "Demo8 : get package name and class name  only\n "
                + " PackageName = " + class1.getPackage().getName() + "\n"
                + " ClassName = " + class1.getName() + "\n"
                + " personNameField = " + personNameField.toString() + "\n"
                + " Time = " + TimeUtils.end();
        mTextView.append(result);
    }

    /**
     * 对比反射调用Notification和正常调用Notification的效率
     *
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void demo9() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        //正常初始化一个Notification
        TimeUtils.start();
        Notification notification = new Notification();
        notification.iconLevel = 0;
        notification.flags = 1;
        notification.icon = R.drawable.abc_ab_share_pack_mtrl_alpha;
        notification.color = R.color.abc_input_method_navigation_guard;
        String result1 = "\n*************\n"
                + "Demo9.1 : new Notification directly\n "
                + " Time = " + TimeUtils.end();
        mTextView.append(result1);

        //通过反射初始化一个Notification
        TimeUtils.start();
        Class<?> class1;
        class1 = Class.forName("android.app.Notification");
        Object object = class1.newInstance();

        Field iconLevel = class1.getDeclaredField("iconLevel");
        Field flags = class1.getDeclaredField("flags");
        Field icon = class1.getDeclaredField("icon");
        Field color = class1.getDeclaredField("color");
        iconLevel.setAccessible(true);
        flags.setAccessible(true);
        icon.setAccessible(true);
        color.setAccessible(true);
        iconLevel.setInt(object, 1);
        flags.setInt(object, 1);
        icon.setInt(object, R.drawable.abc_ab_share_pack_mtrl_alpha);
        color.setInt(object, R.color.abc_input_method_navigation_guard);

        String result2 = "\n*************\n"
                + "Demo9.2 : new Notification with reflect\n "
                + " Time = " + TimeUtils.end();
        mTextView.append(result2);
    }

    /**
     * 循环一定次数对比反射调用Notification和正常调用Notification的效率
     *
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void demo10() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        //正常初始化一个Notification
        TimeUtils.start();

        for (int i = 0; i < RECYCLE_TIME; i++) {
            Notification notification = new Notification();
            notification.iconLevel = 0;
            notification.flags = 1;
            notification.icon = R.drawable.abc_ab_share_pack_mtrl_alpha;
            notification.color = R.color.abc_input_method_navigation_guard;
        }
        String result1 = "\n*************\n"
                + "Demo10.1 : no reflcet:recycle 1000 Times \n "
                + " Time = " + TimeUtils.end();
        mTextView.append(result1);

        //通过反射初始化一个Notification
        TimeUtils.start();
        for (int i = 0; i < RECYCLE_TIME; i++) {
            Class<?> class1;
            class1 = Class.forName("android.app.Notification");
            Object object = class1.newInstance();

            Field iconLevel = class1.getDeclaredField("iconLevel");
            Field flags = class1.getDeclaredField("flags");
            Field icon = class1.getDeclaredField("icon");
            Field color = class1.getDeclaredField("color");
            iconLevel.setAccessible(true);
            flags.setAccessible(true);
            icon.setAccessible(true);
            color.setAccessible(true);
            iconLevel.setInt(object, 1);
            flags.setInt(object, 1);
            icon.setInt(object, R.drawable.abc_ab_share_pack_mtrl_alpha);
            color.setInt(object, R.color.abc_input_method_navigation_guard);
        }
        String result2 = "\n*************\n"
                + "Demo10.2 : reflcet:recycle 1000 Times \n "
                + " Time = " + TimeUtils.end();
        mTextView.append(result2);
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
