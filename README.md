# JavaReflect
This is a sample of java reflect.

Demo0 : no reflect 

```java
Person mPerson = new Person();
mPerson.setAge("28");
mPerson.setName("Xiao Ming");
```
Demo1 : who to use reflect

```java
1.
Class<?> class1;
class1 = Class.forName("com.gracker.javareflect.Person");

2.
Class<?> class2;
class2 = Person.class;
```

Demo2: 

```java
        class1 = Class.forName("com.gracker.javareflect.Person");
        //由于这里不能带参数，所以你要实例化的这个类Person，一定要有无参构造函数，后续的Demo会演示如何调用带参数的构造函数
        Person person = (Person) class1.newInstance();
        person.setAge("20");
        person.setName("Gao Jianwu");
```

Demo3:

```java
        Person person;
        person = (Person) constructors[1].newInstance("20", "Sun Yibo");
```

Demo4:

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

Demo5:

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

Demo6:

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

Demo7:

```java
        Class<?> class1;
        class1 = Class.forName("com.gracker.javareflect.SuperMan");
        String nameString = class1.getClassLoader().getClass().getName();
```
