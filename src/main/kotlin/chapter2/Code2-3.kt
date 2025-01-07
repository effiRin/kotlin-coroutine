package org.example.chapter2

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class `Code2-3` {
    /***
     * runBlocking 함수는 해당 함수를 호출한 스레드를 사용해 실행되는 코루틴을 만들어 낸다
     * 이 코루틴은 runBlocking 함수의 인자로 들어온 람다식을 실행하며, 람다식 내부의 모든 코드가 실행 완료될 때까지 코루틴은 종료되지 않는다.
     */

    fun test() = runBlocking<Unit> {
        /***
        총 3개의 코루틴을 생성한다.
        runBlocking 함수를 통해 하나의 코루틴이 생성되고，
        runBlocking 함수 내부에서 launch가 두 번 호출돼 각각 하나의 코루틴이 생성된다
        */
        println("Thread Name : ${Thread.currentThread().name}")

        launch {
            println("Thread Name : ${Thread.currentThread().name}")
        }
        launch {
            println("Thread Name : ${Thread.currentThread().name}")
        }
    }
}

