# Everything about Java

* [Anonymous Classes](#anonymous-classes)
* [Concurrency](#concurrency)

## Anonymous Classes

* An anonymous class is a local class without a name.
* An anonymous class is defined and instantiated in a single succinct expression using the `new` operator.
* While a local class definition is a statement in a block of Java code, an anonymous class definition is an expression, which means that it can be included as part of a larger expression, such as a method call.
* When a local class is used only once, consider using anonymous class syntax, which places the definition and use of the class in exactly the same place.

1. __Defining and Using a Local Class__

```java
// This method creates and returns an Enumeration object
public java.util.Enumeration enumerate() {

  // Here's the definition of Enumerator as a local class
  class Enumerator implements java.util.Enumeration {
    Linkable current;
    public Enumerator() { current = head; }
    public boolean hasMoreElements() {  return (current != null); }
    public Object nextElement() {
      if (current == null) throw new java.util.NoSuchElementException();
      Object value = current;
      current = current.getNext();
      return value;
    }
  }

  // Now return an instance of the Enumerator class defined directly above
  return new Enumerator();
}
```
2. __An Enumeration Implemented with an Anonymous Class__

```java
public java.util.Enumeration enumerate() {
  // The anonymous class is defined as part of the return statement
  return new java.util.Enumeration() { 
    Linkable current; = head;
    { current = head; }  // Replace constructor with an instance initializer
    public boolean hasMoreElements() {  return (current != null); }
    public Object nextElement() {
      if (current == null) throw new java.util.NoSuchElementException();
      Object value = current;
      current = current.getNext();
      return value;
    }
  };  // Note the required semicolon: it terminates the return statement
}
```
* One common use for an anonymous class is to provide a simple implementation of an adapter class. An adapter class is one that defines code that is invoked by some other object. Take, for example, the list() method of the java.io.File class. This method lists the files in a directory. Before it returns the list, though, it passes the name of each file to a FilenameFilter object you must supply. This FilenameFilter object accepts or rejects each file. When you implement the FilenameFilter interface, you are defining an adapter class for use with the File.list() method. Since the body of such a class is typically quite short, it is easy to define an adapter class as an anonymous class. Here's how you can define a FilenameFilter class to list only those files whose names end with .java :

```java
File f = new File("/src");      // The directory to list

// Now call the list() method with a single FilenameFilter argument
// Define and instantiate an anonymous implementation of FilenameFilter
// as part of the method invocation expression. 
String[] filelist = f.list(new FilenameFilter() {
  public boolean accept(File f, String s) { return s.endsWith(".java"); }
}); // Don't forget the parenthesis and semicolon that end the method call!
```
* As you can see, the syntax for defining an anonymous class and creating an instance of that class uses the new keyword, followed by the name of a class and a class body definition in curly braces. If the name following the new keyword is the name of a class, the anonymous class is a subclass of the named class. If the name following new specifies an interface, as in the two previous examples, the anonymous class implements that interface and extends Object. The syntax does not include any way to specify an extends clause, an implements clause, or a name for the class.

* Because an anonymous class has no name, it is not possible to define a constructor for it within the class body. This is one of the basic restrictions on anonymous classes. Any arguments you specify between the parentheses following the superclass name in an anonymous class definition are implicitly passed to the superclass constructor. Anonymous classes are commonly used to subclass simple classes that do not take any constructor arguments, so the parentheses in the anonymous class definition syntax are often empty. In the previous examples, each anonymous class implemented an interface and extended Object. Since the Object() constructor takes no arguments, the parentheses were empty in those examples.

* __Features of Anonymous Classes__

> One of the most elegant things about anonymous classes is that they allow you to define a one-shot class exactly where it is needed. In addition, anonymous classes have a succinct syntax that reduces clutter in your code.
* __Restrictions on Anonymous Classes__

> Because an anonymous class is just a type of local class, anonymous classes and local classes share the same restrictions. An anonymous class cannot define any static fields, methods, or classes, except for staticfinal constants. Interfaces cannot be defined anonymously, since there is no way to implement an interface without a name. Also, like local classes, anonymous classes cannot be public, private, protected, or static.

> Since an anonymous class has no name, it is not possible to define a constructor for an anonymous class. If your class requires a constructor, you must use a local class instead. However, you can often use an instance initializer as a substitute for a constructor. In fact, instance initializers were introduced into the language for this very purpose.

> The syntax for defining an anonymous class combines definition with instantiation. Thus, using an anonymous class instead of a local class is not appropriate if you need to create more than a single instance of the class each time the containing block is executed.

* __New Syntax for Anonymous Classes__

  We've already seen examples of the syntax for defining and instantiating an anonymous class. We can express that syntax more formally as:
  ```java
  new class-name ( [ argument-list ] ) { class-body }
  ```

  or:

  ```java
  new interface-name () { class-body }
  ```

  > As I already mentioned, instance initializers are another specialized piece of Java syntax that was introduced to support anonymous classes. As we discussed earlier in the chapter, an instance initializer is a block of initialization code contained within curly braces inside a class definition. The contents of an instance initializer for a class are automatically inserted into all constructors for the class, including any automatically created default constructor. An anonymous class cannot define a constructor, so it gets a default constructor. By using an instance initializer, you can get around the fact that you cannot define a constructor for an anonymous class.

* __When to Use an Anonymous Class__

  As we've discussed, an anonymous class behaves just like a local class and is distinguished from a local class merely in the syntax used to define and instantiate it. In your own code, when you have to choose between using an anonymous class and a local class, the decision often comes down to a matter of style. You should use whichever syntax makes your code clearer. In general, you should consider using an anonymous class instead of a local class if:

  * The class has a very short body.
  * Only one instance of the class is needed.
  * The class is used right after it is defined.
  * The name of the class does not make your code any easier to understand.
  
* __Anonymous Class Indentation and Formatting__

  The common indentation and formatting conventions we are familiar with for block-structured languages like Java and C begin to break down somewhat once we start placing anonymous class definitions within arbitrary expressions. Based on their experience with inner classes, the engineers at Sun recommend the following formatting rules:
  
  * The opening curly brace should not be on a line by itself; instead, it should follow the close parenthesis of the new operator. Similarly, the new operator should, when possible, appear on the same line as the assignment or other expression of which it is a part.
  
  * The body of the anonymous class should be indented relative to the beginning of the line that contains the new keyword.
  
  * The closing curly brace of an anonymous class should not be on a line by itself either; it should be followed by whatever tokens are required by the rest of the expression. Often this is a semicolon or a close parenthesis followed by a semicolon. This extra punctuation serves as a flag to the reader that this is not just an ordinary block of code and makes it easier to understand anonymous classes in a code listing.

## Concurrency
* Computer users take it for granted that their systems can do more than one thing at a time.
* They assume that they can continue to work in a word processor, while other applications download files, manage the print queue, and stream audio.
* Even a single application is often expected to do more than one thing at a time.
* For example, that streaming audio application must simultaneously read the digital audio off the network, decompress it, manage playback, and update its display.
* Even the word processor should always be ready to respond to keyboard and mouse events, no matter how busy it is reformatting text or updating the display.
* Software that can do such things is known as _concurrent software_.

* The Java platform is designed from the ground up to support concurrent programming, with basic concurrency support in the Java programming language and the Java class libraries.
* Since version 5.0, the Java platform has also included high-level concurrency APIs.
* This lesson introduces the platform's basic concurrency support and summarizes some of the high-level APIs in the `java.util.concurrent` packages.

### Processes and Threads

* In concurrent programming, there are two basic units of execution: _processes_ and _threads_.
* In the Java programming language, concurrent programming is mostly concerned with threads.
* However, processes are also important.

* A computer system normally has many active processes and threads. This is true even in systems that only have a single execution core, and thus only have one thread actually executing at any given moment.
* Processing time for a single core is shared among processes and threads through an OS feature called time slicing.

* It's becoming more and more common for computer systems to have multiple processors or processors with multiple execution cores.
* This greatly enhances a system's capacity for concurrent execution of processes and threads -- but concurrency is possible even on simple systems, without multiple processors or execution cores.

#### Processes
* A process has a self-contained execution environment.
* A process generally has a complete, private set of basic run-time resources; in particular, each process has its own memory space.

* Processes are often seen as synonymous with programs or applications.
* However, what the user sees as a single application may in fact be a set of cooperating processes.
* To facilitate communication between processes, most operating systems support __*Inter Process Communication*__ resources, such as pipes and sockets.
* IPC is used not just for communication between processes on the same system, but processes on different systems.

* Most implementations of the Java virtual machine run as a single process.
* A Java application can create additional processes using a `ProcessBuilder` object.
* Multiprocess applications are beyond the scope of this lesson.

#### Threads
* Threads are sometimes called _lightweight processes_.
* Both processes and threads provide an execution environment, but creating a new thread requires fewer resources than creating a new process.

* Threads exist within a process -- every process has at least one. Threads share the process's resources, including memory and open files.
* This makes for efficient, but potentially problematic, communication.

* Multithreaded execution is an essential feature of the Java platform.
* Every application has at least one thread -- or several, if you count "system" threads that do things like memory management and signal handling.
* But from the application programmer's point of view, you start with just one thread, called the main thread.
* This thread has the ability to create additional threads, as we'll demonstrate in the next section.

### Thread Objects
* Each thread is associated with an instance of the class `Thread`.
* There are two basic strategies for using `Thread` objects to create a concurrent application.
  * To directly control thread creation and management, simply instantiate `Thread` each time the application needs to initiate an asynchronous task.
  * To abstract thread management from the rest of your application, pass the application's tasks to an _executor_.
  
* This section documents the use of `Thread` objects. Executors are discussed with other high-level concurrency objects.

### Defining and Starting a Thread
* An application that creates an instance of `Thread` must provide the code that will run in that thread.
* There are two ways to do this:
  * Provide a `Runnable` object. The `Runnable` interface defines a single method, `run`, meant to contain the code executed in the thread. The `Runnable` object is passed to the `Thread` constructor, as in the `main.java.HelloRunnable` example:
    ```java
    public class main.java.HelloRunnable implements Runnable {
        public void run() {
            System.out.println("Hello from a thread");
        }
    
        public static void main(String args[]) {
            (new Thread(new main.java.HelloRunnable())).start();
        }
    }
    ```
* Subclass `Thread`. The `Thread` class itself implements `Runnable`, though its `run` method does nothing.
* An application can subclass `Thread`, providing its own implementation of `run`, as in the `HelloThread` example:
  ```java
  public class HelloThread extends Thread {
      public void run() {
          System.out.println("Hello from a thread");
      }
  
      public static void main(String args[])
      {
          (new HelloThread()).start();
      }
  }
  ```
* Notice that both examples invoke `Thread.start` in order to start the new thread.

* Which of these idioms should you use?
* The first idiom, which employs a `Runnable` object, is more general, because the `Runnable` object can subclass a class other than `Thread`.
* The second idiom is easier to use in simple applications, but is limited by the fact that your task class must be a descendant of `Thread`.
* This lesson focuses on the first approach, which separates the `Runnable` task from the `Thread` object that executes the task.
* Not only is this approach more flexible, but it is applicable to the high-level thread management APIs covered later.

* The `Thread` class defines a number of methods useful for thread management.
* These include `static` methods, which provide information about, or affect the status of, the thread invoking the method.
* The other methods are invoked from other threads involved in managing the thread and `Thread` object.
* We'll examine some of these methods in the following sections.