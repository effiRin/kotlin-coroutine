package org.example.chapter5

import kotlinx.coroutines.*

class `Code9` {

    fun `Code5-7`() = runBlocking {
        val networkDeferred: Deferred<String> = async(Dispatchers.IO) {
            delay (1000L) // 네트워크 요청
            return@async "Dummy Response" // 문자열 반환
        }
        val result = networkDeferred.await() // networkDeferred로부터 결과값이 반환될 때까지 대기
        println(result)
    }

    /***
     * 위 코드에서는 async 함수를 호출해 Deferred 객체를 만들고，곧바로 Deferred 객체에 대해 await 함수를 호출한다.
     * 이처럼 async 함수를 호출한 후 연속적으로 await 함수를 호출해 결과값 수신을 대기하는 코드는 다음과 같이 withContext 함수로 대체될 수 있다.
     */

    fun `Code5-8`() = runBlocking {
        val result: String = withContext(Dispatchers.IO) {
            delay(1000L) // 네트워크 요청
            return@withContext "Dummy Response" // 문자열 반환
        }
        println(result)
    }

    /***
     * withContext 함수는 겉보기에는 async와 await를 연속적으로 호출하는 것과 비슷하게 동작하지만 내부적으로 보면 다르게 동작한다.
     * async-await 쌍은 새로운 코루틴을 생성해 작업을 처리하지만 withContext 함수는 실행 중이던 코루틴을 그대로 유지한 채로 코루틴의 실행 환경만 변경해 작업을 처리한다.
     * 다음 코드를 통해 확인해 보자.
     *
     * 이 코드는 runBlocking 함수의 block 람다식을 실행하는 스레드와 코루틴을 출력 하고，
     * 내부에서 withContext(Dispatchers.IO)를 호출한 후 withContext 함수의 block 람다식을 실행하는 스레드와 코루틴을 출력한다.
     * 코드의 실행 결과를 보면 runBlocking 함수의 block 람다식을 실행하는 스레드와 withContext 함수의 block 람다식을 실행하는 스레드는 main과 Default Dispatcher-worker-1 으로 다르지만
     * 코루틴은 coroutine#1으로 같은 것을 볼 수 있다. 즉，withContext 함수는 새로운 코루틴을 만드는 대신 기존의 코루틴에서 CoroutineContext 객체만 바꿔서 실행된다.
     * 여기서는 CoroutineContext 객체가 Dispatchers.IO로 바뀌었기 때문에 백그라운드 스레드(DefaultDispatcher-worker-1)에서 실행됐다.
     */
    fun `Code5-9`() = runBlocking {
        println("${Thread.currentThread().name} runBlocking 블록 실행")

        withContext(Dispatchers.IO) {
            println("${Thread.currentThread().name} withContext 블록 실행")
        }
    }

    // 결과
    // main @coroutine#1 runBlocking 블록 실행
    // DefaultDispatcher-worker-1 @coroutine#1 withContext 블록 실행
}