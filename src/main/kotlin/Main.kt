package org.example

import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("Hello Coroutines!")
}

/***
 * runBlocking 함수는 해당 함수를 호출한 스레드를 사용해 실행되는 코루틴을 만들어 낸다
 * 이 코루틴은 runBlocking 함수의 인자로 들어온 람다식을 실행하며, 람다식 내부의 모든 코드가 실행 완료될 때까지 코루틴은 종료되지 않는다.
 */