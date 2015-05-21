# Java反射的使用
这个小例子简单介绍了Java使用反射的各种方法，帮助大家快速掌握Java反射的基本使用方法。例子程序是用Android Studio编写的,如果你使用Eclipse,新建一个工程,拷贝src下面的几个类过去就可以了.Android Studio用户可以直接打开.

# TODO
1. 反射的效率对比
2. 反射的高级用法
3. 反射的基本原理
4. 反射的优缺点对比

以上几个点，如果大家有什么想法或者有这方面的经验，欢迎一起交流或者发PR。

# Demo解释

Demo0 : 不使用反射，直接通过New生成对象

```java
Person mPerson = new Person();
mPerson.setAge("28");
mPerson.setName("Xiao Ming");
```
Demo1 : 通过反射获取类信息

```java
        //第一种写法
        Class<?> class1;
        class1 = Class.forName("com.gracker.javareflect.Person");

        //第二种写法
        Class<?> class2;
        class2 = Person.class;
```

Demo2: 反射调用无参数的构造函数

```java
        class1 = Class.forName("com.gracker.javareflect.Person");
        //由于这里不能带参数，所以你要实例化的这个类Person，一定要有无参构造函数，后续的Demo会演示如何调用带参数的构造函数
        Person person = (Person) class1.newInstance();
        person.setAge("20");
        person.setName("Gao Jianwu");
```

Demo3: 通过反射调用有参数的构造函数

```java
        Person person;
        person = (Person) constructors[1].newInstance("20", "Sun Yibo");
```

Demo4: 通过反射获取和设置类变量

```java
        Class<?> class1;
        class1 = Class.forName("com.gracker.javareflect.Person");
        Object obj = class1.newInstance();

        Field personNameField = class1.getDeclaredField("name");
        personNameField.setAccessible(true);
        personNameField.set(obj, "Cui Yingyun");

        Field personAgeField = class1.getDeclaredField("age");
        personAgeField.setAccessible(true);
        personAgeField.set(obj, "30");
```

Demo5:通过反射获取子类和父类的相关信息

```java
        Class<?> class1;
        class1 = Class.forName("com.gracker.javareflect.SuperMan");

        //取得父类名称
        Class<?> superClass = class1.getSuperclass();
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
```

Demo6:通过反射调用类方法

```java
        Class<?> class1;
        class1 = Class.forName("com.gracker.javareflect.SuperMan");

        System.out.println("Demo7: \n调用无参方法fly()：");
        Method method = class1.getMethod("fly");
        method.invoke(class1.newInstance());

        System.out.println("调用有参方法walk(int m)：");
        method = class1.getMethod("fly", int.class);
        method.invoke(class1.newInstance(), 100);
```

Demo7: 通过反射获取类加载器

```java
        Class<?> class1;
        class1 = Class.forName("com.gracker.javareflect.SuperMan");
        String nameString = class1.getClassLoader().getClass().getName();
```
