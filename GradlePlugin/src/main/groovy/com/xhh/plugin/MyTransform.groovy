package com.xhh.plugin

import com.android.SdkConstants
import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.ClassPool
import javassist.CtClass
import javassist.CtField
import javassist.CtMethod
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

import javax.xml.crypto.dsig.TransformException

class MyTransform extends Transform {

    private static final def CLICK_LISTENER = "android.view.View\$OnClickListener"

    def pool = ClassPool.default
    def project

    MyTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "ModifyTransform"
    }

    /**
     * ContentType，数据类型，有 CLASSES 和 RESOURCES 两种。
     * 其中的 CLASSES 包含了源项目中的 .class 文件和第三方库中的 .class 文件。
     * RESOURCES 仅包含源项目中的 .class 文件。
     * 对应 getInputTypes() 方法。
     * */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * Scope，表示要处理的 .class 文件的范围，主要有
     * PROJECT， SUB_PROJECTS，EXTERNAL_LIBRARIES（外部库）等。
     * 对应 getScopes() 方法。
     * */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 是否支持增量编译
     * true : 支持增量编译，clean 后首次编译属于非增量编译，往后的编译，如果代码有修改，则属于增量编译，插件task会参与到编译中，
     * 如果没有修改任何文件，则属于非增量编译，此时的编译器会自动跳过支持增量编译的插件的task
     * （增量的时间缩短为全量的速度提升了3倍多，而且这个速度优化会随着工程的变大而更加显著）
     * false :不知道增量编译，无论代码文件是否有修改，都会走
     *
     * 结论：1、一次编译对 Transform 来说是否是增量编译取决于两个方面：
     * （1）、当前编译是否有增量基础；
     * （2）、当前 Transform 是否开启增量编译。
     *
     * sp：经过测试发现 clean 后的第一次编译，transform 属于增量编译，如果transform 开启增量编译，则会走该 transformTask
     * */
    @Override
    boolean isIncremental() {
        return true
    }

    /**
     * 编译项目的时候会在 build 面板中打印这里的日志
     * */
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        isIncremental()

        project.android.bootClasspath.each {
            pool.appendClassPath(it.absolutePath)
        }

        transformInvocation.inputs.each {

            it.jarInputs.each { //遍历项目中导入的jar包
                // it 为 JarInput 类型
                println "it jarInputs path :${it.file.absolutePath}"
                pool.insertClassPath(it.file.absolutePath)

                // 重命名输出文件（同目录copyFile会冲突）
                def jarName = it.name //jar 包的名称
                def md5Name = DigestUtils.md5Hex(it.file.absolutePath)
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def dest = transformInvocation.outputProvider.getContentLocation(
                        jarName + md5Name, it.contentTypes, it.scopes, Format.JAR)

                println "new jar name :${jarName + md5Name}"
                println "dest absolutePath: ${dest.absolutePath}"

                FileUtils.copyFile(it.file, dest)
            }


            it.directoryInputs.each { //遍历项目中的的字节码文件
                println "directory inputs path : ${it.file.absolutePath}"
                def preFileName = it.file.absolutePath
                pool.insertClassPath(preFileName)
                //查找目标file
                findTarget(it.file, preFileName)

                // 获取output目录
                def dest = transformInvocation.outputProvider.getContentLocation(
                        it.name,
                        it.contentTypes,
                        it.scopes,
                        Format.DIRECTORY)

                println "copy directory: " + it.file.absolutePath
                println "dest directory: " + dest.absolutePath
                // 将input的目录复制到output指定目录
                FileUtils.copyDirectory(it.file, dest)
            }
        }
    }

    /**
     * 递归遍历class文件目录，不过 fileName 一直为根目录
     * */
    private void findTarget(File dir, String fileName) {
        if (dir.isDirectory()) {
            dir.listFiles().each {
                findTarget(it, fileName)
            }
        } else {
            modify(dir, fileName)
        }
    }

    private void modify(File dir, String fileName) {
        println "修改class文件的 fileName :$fileName"
        println "修改class文件的 absolutePath :${dir.absolutePath}"

        def filePath = dir.absolutePath

        if (!filePath.endsWith(SdkConstants.DOT_CLASS)) {
            return
        }
        if (filePath.contains('R$') || filePath.contains('R.class')
                || filePath.contains("BuildConfig.class")) {
            return
        }

        // filePath : C:\android\source\github\javassist\app\build\intermediates\classes\debug\com\zw\yzk\sample\javassist\MainActivity$1.class
        // fileName : C:\android\source\github\javassist\app\build\intermediates\classes\debug
        def className = filePath.replace(fileName, "")
                .replace("\\", ".")
                .replace("/", ".")
        // name ：com.zw.yzk.sample.javassist.MainActivity$1
        // MainActivity$1 表示 MainActivity 的第一个内部类
        def name = className.replace(SdkConstants.DOT_CLASS, "")
                .substring(1)

        println "className :$className ------ name:$name"

        //获取class对象，下面是对class进行判断和修改
        CtClass ctClass = pool.get(name)
        CtClass[] interfaces = ctClass.getInterfaces()
        if (interfaces.contains(pool.get(CLICK_LISTENER))) { //判断该类是否实现了 View.OnClickListener 接口
            if (name.contains("\$")) {
                println "class is inner class：" + ctClass.name
                println "CtClass: " + ctClass
                //获取外部类class对象
                CtClass outer = pool.get(name.substring(0, name.indexOf("\$")))
                //内部类通过对外部类的隐私应用获取外部类
                CtField field = ctClass.getFields().find {
                    return it.type == outer
                }
                //内部类对外部类由引用的情况下才进行修改
                if (field != null) {
                    println "fieldStr: " + field.name
                    def body = "android.widget.Toast.makeText(" + field.name + "," +
                            "\"javassist\", android.widget.Toast.LENGTH_SHORT).show();"
                    addCode(ctClass, body, fileName)
                }
            } else {
                println "class is outer class: " + ctClass.name
                //更改onClick函数
                def body = "android.widget.Toast.makeText(\$1.getContext(), \"javassist\", android.widget.Toast.LENGTH_SHORT).show();"
                addCode(ctClass, body, fileName)
            }
        }
    }

    /**
     * 在目标方法尾部添加自定义代码
     * */
    private void addCode(CtClass ctClass, String body, String fileName) {

        ctClass.defrost()
        CtMethod method = ctClass.getDeclaredMethod("onClick", pool.get("android.view.View"))
        method.insertAfter(body)

        ctClass.writeFile(fileName)
        ctClass.detach()
        //C:\android\source\github\javassist\app\build\intermediates\classes\debug\com.zw.yzk.sample.javassist.MainActivity$1
        println "write file: " + fileName + "\\" + ctClass.name
        println "modify method: " + method.name + " succeed"
    }
}