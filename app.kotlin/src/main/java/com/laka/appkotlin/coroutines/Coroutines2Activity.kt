package com.laka.appkotlin.coroutines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.laka.appkotlin.R
import com.laka.libutils.LogUtils
import kotlinx.coroutines.*

// CoroutineScope by MainScope() ：kotlin 的委托模式
class Coroutines2Activity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines2)
        //testCoroutine1()
        testCoroutine2()
    }

    private fun testCoroutine2() {
        //CoroutineStart.DEFAULT ：协程会自动启动，无需调用 job.start() or job.join()
        //CoroutineStart.LAZY ：只有在需要的时候才会执行，需手动调用 start()
        //CoroutineStart.ATOMIC：立即执行，并且在开始执行前无法取消，既无视canceling状态，
        //如果在协程体内部执行到挂起函数时，job已经调用了cancel()方法，则会抛出JobCancellationException 异常
        //原因是当前协程已经处于canceling状态，无法在协程中挂起函数，虽说抛出了异常，但是如果捕获了，还是会继续执行其他非挂起的操作的
//        val job = launch(Dispatchers.Main, CoroutineStart.ATOMIC) {
//            LogUtils.info("coroutine-----start")
//            onHandleStart()
//
//            caroline1()
//
//            onHandleEnd()
//        }
        //启动协程
        //job.start()
        //job.join()
        //终止协程
//        job.cancel()
        //Thread.sleep(2000)
        //LogUtils.info("coroutine-----job cancel")


        // CoroutineStart.UNDISPATCHED：立即在当前线程执行协程体，直到第一个挂起函数，挂起函数后的执行线程取决于设置的 Dispatcher
//        runBlocking {
//            LogUtils.info("coroutine-----0: " + Thread.currentThread().name) //当前线程main
//            launch(context = Dispatchers.Default, start = CoroutineStart.UNDISPATCHED) {
//                LogUtils.info("coroutine-----1: " + Thread.currentThread().name)//当前线程main
//                delay(1000)
//                LogUtils.info("coroutine-----2: " + Thread.currentThread().name)//工作线程 worker
//            }
//            delay(2000)
//        }

        //协程作用域
//        runBlocking {
////            // 1、launch 启动协程
////            launch { }
////
////            // 2、supervisorScope 挂起函数启动协程
////            // supervisorScope 启动的协程会继承父协程的作用域，他跟coroutineScope不一样的点是 它是单向传递的，
////            // 即内部的取消操作和异常传递 只能由父协程向子协程传播，不能从子协程传向父协程
////            supervisorScope {
////
////            }
////
////            // 3、coroutineScope 挂起函数启动协程
////            // coroutineScope 启动的协程会继承父协程的作用域，其内部的取消操作是双向传播的，
////            // 子协程未捕获的异常也会向上传递给父协程
////            coroutineScope {
////
////            }
////        }
        // 4、MainScope() 启动协程，作用域跟 3 一样
//        launch {  }


        // 协程的异常传递模式
        // 1、supervisorScope 异常传递模式，由父协程向子协程单向传递
        // 测试结果：无论是子协程还是父协程出现异常，程序直接终止
//        runBlocking {
//            LogUtils.info("coroutine-----1")
//            supervisorScope {
//                LogUtils.info("coroutine-----2")
//                launch {
//                    LogUtils.info("coroutine-----${1 / 0}")
//                }
//                delay(100) // 父协程延时，等待子协程抛异常
//                LogUtils.info("coroutine-----3")
//            }
//            LogUtils.info("coroutine-----4")
//        }

//        runBlocking {
//            LogUtils.info("coroutine-----1")
//            supervisorScope {
//                LogUtils.info("coroutine-----2")
//                launch {
//                    try {
//                        delay(1000)
//                        LogUtils.info("coroutine-----error")
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                }
//                delay(200)
//                val tamp1 = 0
//                val tamp2 = 10
//                LogUtils.info("coroutine-----${tamp2 / tamp1}")
//                LogUtils.info("coroutine-----3")
//            }
//            LogUtils.info("coroutine-----4")
//        }

        // 2、coroutineScope 异常传递方式，双向传递，父协程出现的异常，子协程可以捕获到，反之亦然
//        runBlocking {
//            LogUtils.info("coroutine-----1")
//            try {
//                coroutineScope {
//                    LogUtils.info("coroutine-----2")
//                    launch {
//                        val tamp1 = 0
//                        val tamp2 = 10
//                        LogUtils.info("coroutine-----${tamp2 / tamp1}")
//                    }
//                    delay(500)
//                    LogUtils.info("coroutine-----3")
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                LogUtils.info("coroutine-----4")
//            }
//        }
//         输出 1  2  4


//        runBlocking {
//            LogUtils.info("coroutine-----1")
//            coroutineScope {
//                LogUtils.info("coroutine-----2")
//                launch {
//                    try {
//                        delay(1000)
//                    } catch (e: Exception) {
//                        LogUtils.info("coroutine-----error")
//                    }
//                }
//                delay(100)
//                val tamp1 = 0
//                val tamp2 = 10
//                LogUtils.info("coroutine-----${tamp2 / tamp1}")
//                LogUtils.info("coroutine-----3")
//            }
//            LogUtils.info("coroutine-----4")
//        }
//        输出 1  2  error


        // 协程取消


        // 结构化并发，a1 、a2 在不同的协程作用域中，
//        GlobalScope().launch(Dispatchers.Default) {
//            val a1 = GlobalScope().async {
//                delay(1000)
//                LogUtils.info("coroutine-----1: " + Thread.currentThread().name)
//            }
//            val a2 = GlobalScope().async {
//                delay(100)
//                1 / 0 // 故意报错
//                LogUtils.info("coroutine-----2: " + Thread.currentThread().name)
//            }
//            a1.await()
//            a2.await()
//        }
//        输出 coroutine-----1: DefaultDispatcher-worker-5

//        a1 、a2 在同协程作用域中，其中一个出错终止，另一个也会终止
//        GlobalScope().launch(Dispatchers.Default) {
//            val a1 = async {
//                delay(1000)
//                LogUtils.info("coroutine-----1: " + Thread.currentThread().name)
//            }
//            val a2 = async {
//                delay(100)
//                1 / 0 // 故意报错
//                LogUtils.info("coroutine-----2: " + Thread.currentThread().name)
//            }
//            a1.await()
//            a2.await()
//        }

//        协程挂起函数原理分析


//        协程的状态转移


    }


    //协程测试
    private fun testCoroutine1() {
        //因为使用了委托 MainScope() ，所以可以直接调用 launch
        //MainScope 默认指定的协程线程就是Main，所以可以不需要手动指明
        val job = launch(Dispatchers.Main) {
            onHandleStart()

            caroline1()

            onHandleEnd()
        }
        //先执行此处，才会走协程里面的操作，应该是协程启动（线程调度）需要时间吧
        LogUtils.info("coroutine-----testCoroutine1 end")
    }

    private fun caroline1() {
        LogUtils.info("coroutine-----协程体执行")
    }

    private suspend fun onHandleStart() = withContext(Dispatchers.IO) {
        Thread.sleep(1000)
        LogUtils.info("coroutine-----协程开始")
    }

    private suspend fun onHandleEnd() = withContext(Dispatchers.IO) {
        Thread.sleep(1000)
        LogUtils.info("coroutine-----协程结束")
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}