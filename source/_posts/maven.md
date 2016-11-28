---
title: Apache Maven 入门
date: 2016-11-28 11:18:20
tags: [maven, java, jvm, pom]
---

# Apache Maven

1. 对Maven不熟，从0学起。
2. 动起手来，用maven来构建hello world，体会一下不用任何IDE，只用maven的感觉。
3. maven的核心概念，你知道几个？

## Apache Maven 是用来做什么的？

Maven 是一个项目管理和自动化构建工具。对于码农来说，我们最关心的是项目构建功能。
    
Maven 使用 **惯例优于配置** 的原则。她要求在没有定制之前，所有的项目都有如下结构：

| 目录 | 目的 |
| --- | --- |
| ${basedir} | 存放 pom.xml和所有的子目录 |
| ${basedir}/src/main/java | 项目的java源代码 |
| ${basedir}/src/main/resources | 项目的资源，比如说 property 文件 |
| ${basedir}/src/test/java | 项目的测试类，比如说 JUnit 代码 |
| ${basedir}/src/test/resources | 测试使用的资源 |

## Maven 的安装

```
$ mvn -v
Apache Maven 3.0.3 ...
```

## Maven 的使用

* 建立一个 maven 项目：
   1. 执行 maven 目标（goal），goal 和 ant 的 target 类似：
       ```
       $ mvn archetype:generate -DgroupId=com.mycompany.helloworld -DartifactId=helloworld -Dpackage=com.mycompany.helloworld -Dversion=1.0-SNAPSHOT
       ```
       - archetype:generate 这个 goal 会列出一系列的 archetype 让你选择。
       - archetype 可以理解成 **项目的模型**。Maven 为我们提供了很多种项目模型，或者说模板，从简单的 Swing 到复杂的 Web 应用。
   2. 选择默认的 `maven-archetype-quickstart`  ，编号为：#106
   3. 确认项目属性的配置，这些属性是我们在命令行中用 `-D` 选项指定的，使用 `-Dname=value` 的格式。回车确认，创建项目。
      - 以上步骤也可以使用下面的命令代替：
      
        ```
        mvn archetype:generate -DarchetypeArtifactId=maven-archetype-quickstart -DgroupId=com.mycompany.helloworld -DartifactId=helloworld -Dpackage=com.mycompany.helloworld -Dversion=1.0-SNAPSHOT -DinteractiveMode=false
        ```
      - 当你第一次运行mvn命令，她会从官方maven库（repository）下载需要的程序，存放在本地库目录（local repository）中，因此，流畅的外网连接对于maven来说是必须的。
      - Maven 默认的本地库是 `~/.m2/repository`
* Maven 项目的文件目录结构：
  1. 如果项目顺利成功创建，最后可以看到 `BUILD SUCCESS` 的结果。
  2. 同时 maven 会按照之前选择的属性配置和项目模型，为我们建立如下的文件目录结构：
     ```
     helloworld
     |-- pom.xml
     `-- src
         |-- main
         |   `-- java
         |       `-- com
         |           `-- mycompany
         |               `-- helloworld
         |                   `-- App.java
         `-- test
             `-- java
                 `-- com
                     `-- mycompany
                         `-- helloworld
                             `-- AppTest.java
     ```
  3. 各文件目录的用途：
    * maven 的 `archetype` 插件建立了一个 `helloworld` 目录，这个名字来自 artifactId;
    * 在 `helloworld` 目录下，有一个 **Project Object Model(POM)** 文件：pom.xml，用于描述项目、配置插件和管理依赖关系。
    * 代码目录结构：
      ```
      |-- src  # 源代码
               |-- main  # 正式
               |   `-- java  # 代码
               |   `-- resources  # 资源
               `-- test  # 测试
                   `-- java  # 代码
                   `-- resources  # 资源
      ```
      * 这个代码目录结构和开始提到的一般Maven项目的目录结构是一致的。
      * 而且 maven 已经为我们建立了一个 `App.java` 类文件。
* Maven 项目的构建和运行
  * 一个maven项目默认情况下会产生.jar文件
  * 执行以下命令：
    ```
    $ cd helloworld
    $ mvn package
    ```
  * 构建成功，会得到 `BUILD SUCCESS` 的结果。
  * `helloworld` 目录下面会建立一个新目录 `target/`，存放打包后的 .jar 文件 `helloworld-1.0-SNAPSHOT.jar`，以及编译后的 `class` 文件，放在 `target/classes/` 目录下，测试的 `class` 文件放在 `target/test-classes/` 目录下。
  * 运行java程序
    ```
    $ java -cp target/helloworld-1.0-SNAPSHOT.jar com.mycompany.helloworld.App
    ```
  * 验证结果
    ```
    Hello World!
    ```
  
## Maven 的核心概念

* POM (Project Object Model)
* Maven 插件
* Maven 生命周期
* Maven 依赖管理
- Maven 库

* __POM (Project Object Model)__

  * 一个Maven项目所有的配置都放在 POM 文件中：定义项目的类型、名字、管理依赖关系、定制插件行为等等。比如，可以配置 compiler 插件使用 java 1.5 来编译。
  * 在 POM 中，__groupId，artifactId，packaging，version__ 叫做 Maven 坐标，它能唯一确定一个项目。有了 Maven 坐标，我们就可以用它来指定我们的项目所依赖的其他项目、插件，或者父项目。一般 maven 坐标写成如下的格式：
    ```
    groupId:artifactId:packaging:version
    ```
    像我们的例子就会写成：
    ```
    com.mycompany.helloworld:helloworld:jar:1.0-SNAPSHOT
    ```
  * 我们的 `helloworld` 示例很简单，但是大项目一般会分成几个子项目。在这种情况下，每个子项目就会有自己的 POM 文件，然后他们会有一个共同的父项目，子项目的 POM 会继承父项目的 POM，只要构建父项目就能够构建所有的子项目。
  * 另外，所有的 POM 都继承了一个 Super-POM。Super-POM 设置了一些默认值，比如之前提到的默认的目录结构，默认的插件等等，它遵循了惯例优于配置的原则。所以尽管我们的这个 POM 很简单，但是这只是你看到的一部分，运行时候的 POM 要复杂的多。
  
    如果你想看到运行时候的 POM 的全部内容的话，可以运行下面的命令：
        
    ```
    $ mvn help:effective-pom
    ```

- __Maven 插件__

  * 之前我们用到了 `mvn archetype:generate` 命令来生成一个项目，这是什么意思呢？
  
    * `archetype` 是一个插件(plugin)的名字
    * `generate` 是目标(goal)的名字
    
    这个命令是告诉maven执行 `archetype` 插件的 `generate` 目标，即执行一个插件目标，通常写成 *pluginId:goalId* 的形式。
  
  * 一个目标(goal)是一个工作单元，而插件则是一个或是多个目标的集合。
    比如：
    * Jar 插件：包含建立Jar文件的目标
    * Compiler 插件：包含编译源代码和单元测试代码的目标
    * Surefire 插件：包含运行单元测试的目标
  
  * maven 本身不会做太多事情，她其实不知道怎样去编译或者打包。她把构建的任务交给插件去做。
  
  * 插件定义了常用的构建逻辑，能够被重复利用。这样做的好处是，一旦插件有了更新，那么所有的 Maven 用户都能得到更新。
  
* __Maven 生命周期__

  * 之前我们用到了 `mvn package` 命令来打包，这个 package 又是啥？
    * 项目的构建过程叫做 Maven 生命周期(lifecycle)，它包含了一系列的有序的阶段(phase)，而一个阶段就是构建过程中的一个步骤。
    * package 就是一个 maven 的生命周期阶段(lifecycle phase)
    * 插件目标可以绑定到生命周期阶段上，一个生命周期阶段可以绑定多个插件目标。
    * 当 maven 在构建过程中逐步的通过每个阶段时，会执行该阶段所有的插件目标。
    * maven 能支持不同的生命周期，但是最常用的还是默认的 Maven 生命周期(default Maven lifecycle)
    * 如果你没有进行任何的插件配置，那么上面的命令 `mvn package` 会依次执行默认生命周期中package阶段前的所有阶段的插件目标，最后执行package阶段的插件目标，如下：
      1. process-resources 阶段： resources:resources
      2. compile 阶段： compiler:compile
      3. process-classes 阶段： 默认无目标
      4. process-test-resources 阶段： resources:testResources
      5. test-compile 阶段： compiler:testCompile
      6. test 阶段： surefire:test
      7. prepare-package 阶段： 默认无目标
      8. package 阶段： jar:jar
      
- __Maven 依赖管理__

  * Maven 坐标能够确定一个项目，也就可以用它来解决依赖关系。
  * 在 POM 中，依赖关系是在 `dependencies` 部分定义的。
  * 实际开发中会有复杂得多的依赖关系，因为被依赖的jar文件会有自己的依赖关系。
  * 因为maven提供了传递依赖的特性，因此不需要把那些间接依赖的jar文件也定义在pom文件中。
  * 所谓传递依赖，是指maven会检查被依赖的jar文件中的pom文件，把它的依赖关系纳入最终的依赖关系链中。
  * 在 POM 的 `dependencies` 部分中，`scope` 决定了依赖关系的适用范围：
    * test：执行compiler:testCompile 和 surefire:test 时，才会加到 classpath 中
    * provided：表示 JDK 或者 容器会提供所需的jar文件，比如Web应用中的 servlet API 相关的 jar 文件，编译时需要用到，打包时不需要包含在 WAR 包中，因为servlet容器或者应用服务器会提供。
    * compile：任何时候都会被包含在 classpath 中，打包时也会被包含进去。
    
* __Maven 库__

  * Maven 官方远程库：http://repo1.maven.org/maven2，包含核心插件和可供下载的jar包
  * 对于我们自己开发的公共包：
    1. 可以搭建私有库
    2. 也可以手工安装所需的jar文件到本地库
       ```
       $ mvn install
       ```
  * 项目安装到本地库后，其他项目就可以通过 maven 坐标和这个项目建立依赖关系。