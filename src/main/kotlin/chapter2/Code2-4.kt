package org.example.chapter2

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class `Code2-4` {

    fun test() = runBlocking<Unit>(context = CoroutineName("코루틴 네임")) {
        println("Thread Name : ${Thread.currentThread().name}")

        launch {
            println("Thread Name : ${Thread.currentThread().name}")
        }
        launch {
            println("Thread Name : ${Thread.currentThread().name}")
        }
    }

    /***
     * Thread Name : main @코루틴 네임#1
     * Thread Name : main @코루틴 네임#2
     * Thread Name : main @코루틴 네임#3
     */
}