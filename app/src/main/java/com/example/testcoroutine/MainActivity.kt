package com.example.testcoroutine

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.example.testcoroutine.注解.RuntimeConstructInfo
import com.example.testcoroutine.注解.RuntimeMethodInfo
import com.example.testcoroutine.注解.RuntimeMethodTest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.lang.Exception
import kotlin.system.measureTimeMillis
class MainActivity : BaseActivity() {
    val dataGetUtil =  AsynchronousUtil()
    val subThread = AsynchronousUtil.ChildThread2()
    val dog = AsynchronousUtil.Animal("dog")
    val cat = AsynchronousUtil.Animal("cat")
    val thread1 = Thread{
        dog.lock.readSynchronized()
    }

    val mutex = Mutex()
    var counter = 0
    var handler = object :Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                1-> show.text = msg.obj as String
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        subThread.start()
//        request.setOnClickListener {
////            requestDataByThread()
////            requestDataByAsyncTask()
////            requestDataByCallback()
////            sendMsgtoSubthread()
////              requestCoroutine()
////            testCoroutineException()
////            useFeatureTaskGetData()
////            CoroutineScope(Dispatchers.IO).launch {
////                dataGetUtil.useDefaultStart()
////            }
////            testSynchronized()
////            useMutex()
//            getMethodInfoAnnotation()
//        }
//        intercept.setOnClickListener {
//        }

    }

    private fun testSynchronized(){
        thread1.start()
    }

    private fun inter(){
        val thread2 = Thread{
            try {
                  AsynchronousUtil.Animal.eatStaticSynchronized()
            } catch (e:Exception){
                e.printStackTrace()
            }
        }
        thread2.start()
    }

    private fun requestDataByThread() {
        dataGetUtil.requestDataByNewThread(handler)
    }

    private fun requestDataByAsyncTask() {
        dataGetUtil.requestDataByAsyncTask(show)
    }

    private fun requestDataByCallback(){
        dataGetUtil.requestDataByCallBack { data-> processData(data) }
    }

    private fun processData(data: String) {
        Log.e("AA", Thread.currentThread().name + "获取到异步线程结果,time:" + System.currentTimeMillis())
        show.text = data
    }

    private fun sendMsgtoSubthread(){
        var msg = subThread.handler?.obtainMessage()
        msg?.let {
            it.what = 112
            subThread.handler?.sendMessage(msg)
        }
    }

    private fun requestCoroutine(){
        dataGetUtil.coroutineRequest { processData(it) }
    }

    private fun testCoroutineException(){
        dataGetUtil.coroutineException()
    }

    private fun useFeatureTaskGetData(){
        dataGetUtil.useFeatureTask { processData(it) }
    }

    private fun main() = runBlocking {
            launch(CoroutineExceptionHandler { _, exception ->
                println("launch : CoroutineExceptionHandler got $exception")
            }) {
//                supervisorScope {

                        launch {
                            println("The child throws an exception")
//                            withContext(Dispatchers.IO){
//                            }
                            throw AssertionError()
                        }

                launch {
//                            delay(1000)
                    println("The second child finished")
                }

//                }
            }

    }

    private fun useMutex() = runBlocking{
        withContext(Dispatchers.Default){
            massiveRun {
                mutex.withLock {
                    counter++
                }
            }
        }
    }

    private suspend fun massiveRun(action: suspend () -> Unit) {
        val n = 100  // 启动的协程数量
        val k = 1000 // 每个协程重复执行同一动作的次数
        val time = measureTimeMillis {
            coroutineScope { // 协程的作用域
                repeat(n) {
                    launch {
                        repeat(k) {
                            println("Completed in thread" + Thread.currentThread().name)
                            action() }
                    }
                }
            }
        }
        println("Completed ${n * k} actions in $time ms" + "final result:" + counter)
    }

    private fun getMethodInfoAnnotation(){
        val cls = RuntimeMethodTest::class.java
        for (method in cls.methods){
            val runtimeMethodInfo = method.getAnnotation(RuntimeMethodInfo::class.java)
            if (runtimeMethodInfo != null){
                println("RuntimeMethodInfo author =" + runtimeMethodInfo.author)
                println("RuntimeMethodInfo version =" + runtimeMethodInfo.version)
            }
        }
        for (construct in cls.constructors){
            val runtimeConstructInfo = construct.getAnnotation(RuntimeConstructInfo::class.java)
            if (runtimeConstructInfo != null){
                println("RuntimeMethodInfo author =" + runtimeConstructInfo.className)
                println("RuntimeMethodInfo version =" + runtimeConstructInfo.layoutId)
            }
        }
    }

}