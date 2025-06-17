package org.example.chapter5

import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class Code13 {

    private val myDispatcher1 = newSingleThreadContext("MyThread1")
    private val myDispatcher2 = newSingleThreadContext("MyThread2")

    fun `Code5-13`() = runBlocking<Unit> {
        println("[${Thread.currentThread().name}] 코루틴 실행")

        withContext(myDispatcher1) {
            println("[${Thread.currentThread().name}] 코루틴 실행")

            withContext(myDispatcher2) {
            println(" [${Thread.currentThread().name}] 코루틴 실행")
            }

            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
    // 결과
    // [main @coroutine#1] 코루틴 실행
    // [MyThread1 @coroutine#1] 코루틴 실행
    // [MyThread2 @coroutine#1] 코루틴 실행
    // [MyThread1 @coroutine#1] 코루틴 실행
    // [main @coroutine#1] 코루틴 실행

    /***
     * 이 코드에는 MyThread1 스레드를 사용하는 CoroutineDispatcher 객체인 myDispatcher1과
     * MyThread2 스레드를 사용하는 CoroutineDispatcher 객체인 myDispatcher2가 존재한다.
     * runBlocking 함수를 호출해 runBlocking 코루틴을 생성하고 다른 코루틴은 생성하지 않으며,
     * withContext(myDispatcher1)과 withContext(myDispatcher2)를 이용해 실행 스레드를 전환한다.
     * 코드를 실행해 보면 다음과 같은 결과가 나온다
     *
     * 모든 코루틴이 runBlocking 코루틴(coroutine#1)인데 스레드가 메인 스레드(main)에서 MyThread1 스레드로 바뀌었다가
     * MyThread2 스레드로 전환되고 다시 MyThread1 스레드로 전환된 후 다시 메인 스레드로 돌아온다.
     * 이처럼 withContext 함수를 CoroutineDispatcher 객체와 함께 사용하면 코루틴이 자유롭게 스레드를 전환할 수 있다.
     * 좀더 정확히 말하면 코루틴이 실행되는데 사용하는 CoroutineDispatcher 객체를 자유롭게 바꿀 수 있다.
     *
     * withContext 함수를 통해 바뀐 CoroutineDispatcher 객체가 유효한 것은 withContext 블록 내부뿐이다.
     * withContext 블록을 벗어나면 다시 이전의 CoroutineDispatcher 객체를 사용하게 되며 스레드가 다시 전환된다.
     * 이 때문에 runBlocking 블록에 있는 맨 앞의 출력과 마지막 출력은 메인 스레드를 사용하고,
     * withContext(myDispatcher1) 블록에서 실행되는 두 번째 출력과 네 번째 출력은 MyThread 1을 사용하며,
     * withContext(myDispatcher2) 블록에서 실행되는 세 번째 출력만 MyThread2를 사용하는 것이다.
     */
}