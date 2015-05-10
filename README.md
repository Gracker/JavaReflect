# JavaReflect
This is a sample of java reflect.

demo0 : no reflect 

```java
Person mPerson = new Person();
mPerson.setAge("28");
mPerson.setName("Xiao Ming");
```
demo1 : who to use reflect

```java
1.
Class<?> class1;
class1 = Class.forName("com.gracker.javareflect.Person");

2.
Class<?> class2;
class2 = Person.class;
```