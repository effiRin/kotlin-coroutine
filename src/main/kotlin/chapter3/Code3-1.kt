package org.example.chapter3

import kotlinx.coroutines.*

class `Code3-1` {

    /***
     * 코루틴은 일시 중단이 기능한 ‘작업’이기 때문에 스레드가 있어야 실행될 수 있으며，CoroutineDispatcher는 코루틴을 스레드로 보내 실행시키는 역할을 한다.
     * CoroutineDispatcher는 코루틴을 스레드로 보내는 데 사용할 수 있는 스레드나 스레드풀을 가지며,
     * 코루틴을 실행 요청한 스레드에서 코루틴이 실행되도록 만들 수 있다.
     *
     * CoroutineDispatcher는 코루틴의 실행을 관리하는 주체로 자신에게 실행 요청된 코루틴들을 작업 대기열에 적재하고，
     * 자신이 사용할 수 있는 스레드가 새로운 작업을 실행할 수 있는 상태라면 스레드로 코루틴을 보내 실행될 수 있게 만드는 역할을 한다
     * (하지만 코루틴의 실행 옵션에 따라 작업 대기열에 적재되지 않고 즉시 실행될 수도 있고，작업 대기열이 없는 CoroutineDispatcher 구현체도 있다)
     *
     * CoroutineDispatcher에는 두 가지 종류가 있다. 하나는 제한된 디스패처 (Confined Dispatcher)이고，
     * 다른 하나는 무제한 디스패처(UnconfinedDispatcher)이다.
     * 제한된 디스패처는 사용할 수 있는 스레드나 스레드풀이 제한된 디스패처이고,
     * 무제한 디스패처는 사용 할 수 있는 스레드나 스레드풀이 제한되지 않은 디스패처이다.
     *
     * 예를 들어 IO 작업을 실행할 때는 입출력 작업용 CoroutineDispatcher 객체에 실행을 요청해야 하며,
     * CPU 연산 작업을 실행할 때는 CPU 연산 작업용 CoroutineDispatcher 객체에 실행을 요청해야한다.
     */

    /***
     * 사용할 수 있는 스레드가 하나인 CoroutineDispatcher 객체는 단일 스레드 디스패처 Single-Thread Dispatcher 라고 부르는데，
     * 다음과 같이 코루틴 라이브러리에서 제공하는 newSingleThreadContext 함수를 시용해 만들 수 있다.
     * CoroutineDispatcher 객체에는 작업을 적재하기 위한 작업 대기열이 있고，스레드 하나로 구성된 스레드풀을 사용할 수 있다.
     */

    /***
     * 사용할 수 있는 스레드가 하나인 CoroutineDispatcher 객체는 단일 스레드 디스패처 Single-Thread Dispatcher 라고 부르는데，
     * 다음과 같이 코루틴 라이브러리에서 제공하는 newSingleThreadContext 함수를 시용해 만들 수 있다.
     * CoroutineDispatcher 객체에는 작업을 적재하기 위한 작업 대기열이 있고，스레드 하나로 구성된 스레드풀을 사용할 수 있다.
     */

    @OptIn(ExperimentalCoroutinesApi::class)
    fun test() = runBlocking<Unit> {
        val singleDispatcher: CoroutineDispatcher = newSingleThreadContext(name = "SingleThread")

        // launch 함수를 호출해 만든 코루틴을 특정 CoroutineDispatcher 객체에 실행 요청하기 위해서는 launch 함수의 context 인자로 CoroutineDispatcher 객체를 넘기면 된다.
        // context를 안넘기면 새로운 코루틴 컨텍스트가 실행된다. context: CoroutineContext = EmptyCoroutineContext,
        launch(context = singleDispatcher) {
            println(Thread.currentThread().name + " 실행")
        }
    }
}