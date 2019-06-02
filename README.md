# Timer
[Timer](https://user-gold-cdn.xitu.io/2019/6/2/16b16b5309a16633/timer)是一个基于spring-aop的方法计时器，支持高度定制的功能来适配项目的需要

## 项目结构

![](https://user-gold-cdn.xitu.io/2019/6/2/16b160c13e305377?w=548&h=566&f=png&s=33014)

- Properties：属性配置，定义了一些结果模式和规格，可以在`application.yml`文件中自行更改
- Formatter：属性格式化器，用于将每一项需要展示的属性格式化为字符串输出
- InfoReceiver：属性接收者，也是排版格式化器，保存每一项待展示的属性，以一定的格式输出
- Outputer：结果输出器，负责将InfoReceiver格式化后的结果输出到控制台或文件等地方

## 特点
- 基于注解的灵活使用
- 高度自定义的输出格式
- 多种配置方式，使用自适应的优先级

## 快速启动

### 在springboot项目中引入依赖
```xml
<properties>
    <timer.version>1.0.0-RELEASE</timer.version>
</properties>

<dependencies>
    <dependency>
      <groupId>com.github.mayoi7</groupId>
      <artifactId>timer</artifactId>
      <version>${timer.version}</version>
    </dependency>
</dependencies>
```

### 配置包扫描路径
```java
@SpringBootApplication
@ComponentScan(basePackages = {"com.*"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```

### 定义需要计时的方法，添加@Timer注解
```java
@Component
public class TimerDemo {

    @Timer
    public void longTimeMethod() {
        System.out.println("[longTimeMethod]run begin");
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 10000000; i++) {
            list.add(i);
        }
        System.out.println("[longTimeMethod]run over");
    }
}
```

## 配置项

### 在application.yml中，开放的配置有：
```yml
timer:
  mode:
    class-mode: simple
    method-mode: simple
    time-mode: simple
    unit: milliseconds
  format:
    date-format: yyyy-MM-dd HH:mm:ss
    file-format: timer-demo
    formatter-path: com.github.mayoi7.timer.simulate.UserFormatter
    log-path: /
```

#### class-mode
类输出模式，有`full`和`simple`两种模式，`full`为全路径输出，`simple`仅输出类名，默认为`simple`

#### method-mode
方法输出模式，目前仅有`simple`一种输出方式，默认为`simple`

#### time-mode

时间输出模式，目前仅有`simple`一种输出方式，默认为`simple`

#### unit

时间单位，默认为`milliseconds（毫秒）`

#### date-format

日期格式，和SimpleDateFormat中所配置的一致，默认为`yyyy-MM-dd HH:mm:ss`

#### file-format
输出的日志文件名，默认为`timer`

目前需要手动设置日志的后缀名，不会自动添加

#### formatter-path

自定义的格式化器的全路径，默认为空，系统会自动使用SimpleFormatter

#### log-path

日志输出的路径，不需要包含文件名，默认为空，会输出到当前磁盘根目录

### 在@Timer注解中，开放的配置有：

```java
@Timer(formatter = "", position = ResultPosition.LOG, unit = TimeUnit.SECONDS)
```

#### formatter
相当于yml中的formatter-path

#### position
结果输出路径，有`ResultPosition.LOG`和`ResultPosition.CONSOLE`两种方式，分别代表输出到日志文件和控制台，默认输出到控制台

#### unit
输出的时间单位，默认为`TimeUnit.MILLISECONDS`

### 优先级
对于拥有相同含义的配置项（特指formatter和unit），@Timer注解中配置项的优先级高于yml文件

## 自定义格式化器

项目中已提供了一个默认的格式化器，即SimpleFormatter，如果不自行配置，则会默认使用该格式化器

### 定义排版格式化器

如果需要自定义输出格式，需要定义一个排版格式化器，定义一个类，继承InfoReceiver，重写其`output()`方法

```java
import com.github.mayoi7.timer.format.TimerFormatable.InfoReceiver;

public class MyReceiver extends InfoReceiver {

    @Override
    public String output() {
        return "\nuser:\n" + date + " | " + duration + " | " + classInfo + " | " + methodInfo;
    }
}
```

InfoReceiver提供了四种可供使用的属性：
- date：打印结果时的时间
- duration：方法执行时间
- classInfo：类名
- methodInfo：方法名

其中类和方法名打印的格式由Formatter来设置

### 定义属性格式化器

定义一个类，继承AbstractFormatter

```java
public class MyFormatter extends AbstractFormatter {

    /**
     * 通过配置和属性源来初始化格式化器
     * @param properties 配置项，包括输出模式和规则
     * @param source     属性源，包含所有需要输出的属性
     */
    public MyFormatter(TimerProperties properties, TimerOutputSource source) {
        super(properties, source);
    }

    @Override
    public InfoReceiver getInfoReceiver() {
        return new MyReceiver();
    }
}
```

重写其`getInfoReceiver()`方法即可，该方法需要返回一个InfoReceiver的实例

如果对属性格式化方式不满意，比如想对类路径做一些处理之类的操作，AbstractFormatter提供了四种格式化方法，方法名均为`formatXXX`的格式，可以自行重写

## 注意事项
使用前需要在项目入口类`XXXApplication`类上添加`@ComponentScan`注解，确保注解能被扫描到，否则会无效

配置的格式化器路径特指属性格式化器类的路径，即继承了AbstractFormatter类的类路径，而不是指InfoReceiver，如果该路径配置错误，会默认使用SimpleFormatter，但是依然会在控制台显示出错信息

## 其他

本项目还不完善，如有bug或其他建议，请联系acerola.orion@foxmail.com，或[github_mayoi7](https://user-gold-cdn.xitu.io/2019/6/2/16b16b5309a16633)
