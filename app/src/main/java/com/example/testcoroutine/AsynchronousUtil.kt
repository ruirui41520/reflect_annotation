package com.example.testcoroutine

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.*
import java.util.concurrent.*


class AsynchronousUtil {

    fun requestDataByNewThread(mainHandler: Handler) {
        Thread {
            Thread.sleep(2000)
            val message: Message = mainHandler.obtainMessage(1, "子线程中发布消息，更新主线程")
            mainHandler.sendMessage(message)
        }.start()
    }

    fun requestDataByAsyncTask(textView: TextView) {
        class MyTask : AsyncTask<String?, String?, String>() {
            override fun doInBackground(vararg params: String?): String {
                return "子线程中发布消息，更新主线程"
            }

            override fun onPreExecute() {
                super.onPreExecute()
                Thread.sleep(2000)
                textView.text = "start"
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                textView.text = result
            }
        }
        MyTask().execute()
    }

    fun requestDataByCallBack(callback: (resultData: String) -> Unit) {
        Thread { callback(requestData()) }.start()
    }

    private fun requestData(): String {
        Thread.sleep(2000)
        return "子线程中发布消息，更新主线程"
    }

    class ChildThread2 : Thread() {
        /** 获取子线程的消息处理器  */
        var handler: Handler?= null
        override fun run() {
            super.run()
            // 非主线程中默认没有创建Looper对象，需要先调用Looper.prepare()创建启用Looper
            Looper.prepare()
            // 开始工作，从消息队列里取消息，处理消息
            handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    if (msg.what == 112) {
                        Log.e("ard", "主线程发来的消息")
                    }
                }
            }
            Looper.loop()
            // 写在Looper.loop()之后的代码不会被执行，函数内部是一个 for (;;) 循环
            // 当调用mHandler.getLooper().quit()后，loop才会中止，其后的代码才能得以运行
        }
    }

    fun coroutineRequest(callback: (resultData: String) -> Unit){
        CoroutineScope(Dispatchers.Main).launch {
            Log.e("test", "start load data")
            withContext(Dispatchers.IO){
                delay(2000)
            }
            callback("子线程中发布消息，更新主线程")
            val deferred = async(Dispatchers.Main) {
                callback("子线程中发布消息，更新主线程")
            }
            deferred.await()
        }

    }


    suspend fun useDefaultStart(){
        Log.e("test", "1")
        val job = GlobalScope.launch {
            Log.e("test", "2")
        }
        Log.e("test", "3")
//        job.join()
        Log.e("test", "4")
    }

    suspend fun useLazyStart(){
        Log.e("test", "1")
        val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
            Log.e("test", "2")
        }
        Log.e("test", "3")
        job.start()
        Log.e("test", "4")
    }

    fun coroutineException(){
        //1.根协程为launch
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO){
                throw IllegalStateException("this is an error")
            }
        }
//2.根协程为async
        CoroutineScope(Dispatchers.IO).async {
            async{ launch {
                delay(1000)
                throw IllegalStateException("this is an error") } }
        }
//3.捕获async{}.await()异常
        CoroutineScope(Dispatchers.IO).async {
            try {
                val job = async {
                    delay(1000)
                    Log.e("async", "async")
                    throw IllegalStateException("this is an error") }
                job.await()
            } catch (e: Exception) {
                Log.e("error", e.message.toString())
            }
        }
//1.根协程为launch捕获异常
        CoroutineScope(Dispatchers.IO).launch {
                try {
                    delay(1000)
                    Log.e("launch", "0000")
                    throw IllegalStateException("this is an error")
                } catch (e: Exception) {
                    Log.e("error", e.message.toString())
                }
        }
    }

    fun useFeatureTask(callback: (resultData: String) -> Unit){
        val callable: Callable<String> = object : Callable<String> {
            override fun call(): String {
                return "子线程中发布消息，更新主线程"
            }
        }
        val executorService: ExecutorService = Executors.newFixedThreadPool(5)
        val future: Future<String> = executorService.submit(callable)
        try {
            val data = future.get()
            Log.e(
                "AA",
                Thread.currentThread().name + "获取到异步线程结果,time:" + System.currentTimeMillis()
            )
            callback(data)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
    }


    class Animal constructor(val name: String){
        private val animalName = name
        val lock = Book("hali" + animalName)
        fun eat(){
            for (i in 0..5){
                Log.d(
                    "AAA",
                    name + "第 " + i + " 次" + "同步叫唤," + "Thread: " + Thread.currentThread().name + " / Time: " + System.currentTimeMillis()
                )
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

        @Synchronized
        fun eatSynchronized() {
            for (i in 0..5) {
                Log.d(
                    "AAA",
                    name + "第 " + i + " 次" + "同步叫唤," + "Thread: " + Thread.currentThread().name + " / Time: " + System.currentTimeMillis()
                )
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

        fun instanceEat()= synchronized(this@Animal){
            for (i in 0..5) {
                Log.d(
                    "AAA",
                    name + "第 " + i + " 次" + "同步叫唤," + "Thread: " + Thread.currentThread().name + " / Time: " + System.currentTimeMillis()
                )
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

        fun classEat()= synchronized(Animal::class.java){
            for (i in 0..5) {
                Log.d(
                    "AAA",
                    name + "第 " + i + " 次" + "同步叫唤," + "Thread: " + Thread.currentThread().name + " / Time: " + System.currentTimeMillis()
                )
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

        companion object{
            @Synchronized
            fun eatStaticSynchronized() {
                for (i in 0..5) {
                    Log.d(
                        "AAA",
                        "Animal" + "第 " + i + " 次" + "同步叫唤," + "Thread: " + Thread.currentThread().name + " / Time: " + System.currentTimeMillis()
                    )
                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }


    }

    class Book constructor(name:String){
        private val bookName = name
        @Synchronized
        fun readSynchronized(){
            for (i in 0..5){
                Log.d(
                    "AAA",
                    bookName + "第 " + i + " 次" + "同步叫唤," + "Thread: " + Thread.currentThread().name + " / Time: " + System.currentTimeMillis()
                )
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                    return
                }
            }

        }

        fun readSyncClass()= synchronized(Book::class.java){
            for (i in 0..5){
                val currentThread = Thread.currentThread()
                Log.d(
                    "AAA",
                    bookName + "第 " + i + " 次" + "同步叫唤," + "Thread: " + Thread.currentThread().name + " / Time: " + System.currentTimeMillis()
                )
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                }
            }

        }

        companion object{
            @Synchronized
            fun read(){
                for (i in 0..5){
                    Log.d(
                        "AAA",
                        "Book" + "第 " + i + " 次" + "同步叫唤," + "Thread: " + Thread.currentThread().name + " / Time: " + System.currentTimeMillis()
                    )
                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }



}