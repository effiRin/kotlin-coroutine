package org.example.chapter3

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking

class `Code3-6` {

    /***
     * 코루틴은 구조화를 제공해 코루틴 내부에서 새로운 코루틴을 실행할 수 있다.
     * 이때 바깥쪽의 코루틴을 부모 코루틴(Parent Coroutine)이라고 하고,
     * 내부에서 생성되는 새로운 코루틴을 자식 코루틴(Child Coroutine)이라고 한다.
     *
     * 코루틴들은 기본적으로 부모 코루틴의 CoroutineDispatcher 객체를 사용한다.
     * 따라서 특정 CoroutineDispatcher에서 여러 작업을 실행해야 한다면 부모 코루틴에 CoroutineDispatcher를 설정하고,
     * 그 아래에 자식 코루틴을 여러 개 생성하면 된다.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun test() = runBlocking {
        val multiThreadDispatcher = newFixedThreadPoolContext(nThreads = 2, name = "MultiThread")

        launch(multiThreadDispatcher) { // 부모 코루틴 - 전용 스레드가 2개인 multiThreadDispatcher를 사용한다.
            println(Thread.currentThread().name + " 부모 코루틴 실행")
            launch { // 자식 코루틴 - 별도 CoroutineDispatcher 객체가 설정돼 있지 않으므로 부모 코루틴에 설정된 CoroutineDispatcher 객체를 사용한다
                println(Thread.currentThread().name + " 자식 코루틴 실행")
            }
            launch { // 자식 코루틴
                println(Thread.currentThread().name + " 자식 코루틴 실행")
            }
        }
    }
    /***
     * 결과: // 코루틴이 어떤 스레드에서 실행되는지는 실행 시마다 달라질 수 있다.
     * MultiThread-1 @coroutine#2 부모 코루틴 실행
     * MultiThread-2 @coroutine#3 자식 코루틴 실행
     * MultiThread-1 @coroutine#4 자식 코루틴 실행
     */
}