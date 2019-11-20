package com.xhh.anotationprocess;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * 注解处理器，编译期执行，扫描源码中的注解，然后走process方法进行处理，来生成代码
 */
@AutoService(Processor.class) //自动生成 Processor 文件的 META-INF 配置信息。
@SupportedSourceVersion(SourceVersion.RELEASE_8) //java 版本支持
@SupportedAnnotationTypes({  //改处理器处理哪些注解，注解全类名，类名之前用 ， 分隔
        "",
        ""
})
public class MyProcessor extends AbstractProcessor {

    /**
     * 核心方法，必须实现。
     * APT 会扫描源码中所有的相关注解，然后会回调process() 这个方法，如果没有扫描到声明的相关注解，则不会回调此方法。
     * 它的实现一般可以分为两个步骤，首先收集信息，然后根据收集的信息生成代码。
     *
     * @param set              将返回所有的由该Processor处理，并待处理的 Annotations，属于该Processor处理的注解，但并未被使用，不存在与这个集合里。
     * @param roundEnvironment 表示当前或是之前的运行环境，可以通过该对象查找到需要的注解。
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {


        //文件相关的辅助类，它可以创建文件。
        Filer filer = processingEnv.getFiler();


        //日志相关的辅助类，用于打印注解处理器中的日志到控制台。
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.OTHER, "Diagnostic.Kind.OTHER");
        messager.printMessage(Diagnostic.Kind.NOTE, "Diagnostic.Kind.NOTE");
        messager.printMessage(Diagnostic.Kind.WARNING, "Diagnostic.Kind.WARNING");
        messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, "Diagnostic.Kind.MANDATORY_WARNING");
        messager.printMessage(Diagnostic.Kind.ERROR, "Diagnostic.Kind.ERROR"); //注解处理器出错，打印此日志后，编译会失败


        //元素相关的辅助类。这个类非常重要，下面会重点介绍。
        Elements elementUtils = processingEnv.getElementUtils();


        Set<? extends Element> rootElements = roundEnvironment.getRootElements();
        Iterator<? extends Element> iterator = rootElements.iterator();
        while (iterator.hasNext()) {
            Element next = iterator.next();
            List<? extends Element> enclosedElements = next.getEnclosedElements(); //获取子元素
            Element enclosingElement = next.getEnclosingElement(); //获取子元素
        }


        //Javapoet 的操作

//        1、生成以下代码
//        package com.example.helloworld;
//        public final class HelloWorld {
//            public static void main(String[] args) {
//                System.out.println("Hello, JavaPoet!");
//            }
//        }

        //方法（普通参数）
        MethodSpec methodSpec = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(String[].class, "args")
                .returns(TypeName.VOID)
                .build();

//        public void testList(ArrayList<TestBean> beanList){
//        }
        //方法（复杂参数）
        MethodSpec methodSpec1 = MethodSpec.methodBuilder("testList")
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(ParameterizedTypeName.get(ClassName.get(ArrayList.class), ClassName.get(TestBean.class)), "beanList")
                //.addParameter(ParameterizedTypeName.get(ClassName.get(ArrayList.class),ClassName.get("com.xhh.anotationprocess.TestBean","TestBean")),"beanList")
                .build();

        //生成如下代码，ParameterizedTypeName 可以通过嵌套完成复杂的参数
//        public void testHashMap(HashMap<String, ArrayList<TestBean> hashMap){
//        }
        MethodSpec methodSpec2 = MethodSpec.methodBuilder("testHashMap")
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(ParameterizedTypeName.get(
                        ClassName.get(HashMap.class),
                        ClassName.get(String.class),
                        ParameterizedTypeName.get(
                                ClassName.get(ArrayList.class),
                                ClassName.get(TestBean.class)
                        )
                ), "hashMap").build();


        //成员变量生成
//        public class test(){
//            private final String name;
//        }
        TypeSpec typeSpec1 = TypeSpec.classBuilder("test")
                .addModifiers(Modifier.PUBLIC)
                .addField(String.class, "name", Modifier.PUBLIC, Modifier.FINAL)
                .build();

//        需要对成员变量进行初始化
//        public class test(){
//            private final String name = "Lollipop v." + 5.0;
//        }
        FieldSpec typeSpec2 = FieldSpec.builder(String.class, "name", Modifier.PUBLIC, Modifier.FINAL)
                .initializer("$S+$L", "Lollipop v.", 5.0d)
                .build();
        TypeSpec.classBuilder("test")
                .addModifiers(Modifier.PUBLIC)
                .addField(typeSpec2)
                .build();



        //类、接口、枚举
        TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(methodSpec)
                .build();

        // 最终生成文件
        JavaFile javaFile = JavaFile.builder("com.xhh.example", typeSpec).build();

        //写入文件中，类生成完成
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
