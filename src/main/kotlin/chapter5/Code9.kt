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

    /***
     * 이 코드는 runBlocking 블록을 실행하는 스레드와 코루틴을 출력하고, runBlocking 블록 내부에서 async(Dispatchers.IO)를 사용해
     * 백그라운드 스레드에서 코루틴이 실행되도록 만든 후 async 블록을 실행하는 스레드와 코루틴을 출력한다.
     * 코드의 실행 결과를 보면 async 블록을 실행하는 코루틴은 coroutine#2로 runBlocking 블록을 실행하는 coroutine1과 다른 것을 볼 수 있다.
     * 즉, async-await 쌍을 사용하면 새로운 코루틴을 만들지만 await 함수가 호출돼 순차 처리가 돼 동기적으로 실행되는 것이다.
     * 그림 5-6과 같이 coroutine#1은 유지한 채로 coroutine#2가 새로 만들어져 실행된다.
     */
    fun `Code5-10`() = runBlocking {
        println("${Thread.currentThread().name} runBlocking 블록 실행")
        async(Dispatchers.IO) {
            println("${Thread.currentThread().name} async 블록 실행")
        }.await()
    }
    // 결과
    //    main @coroutine#1 runBlocking 블록 실행
    //    DefaultDispatcher-worker-1 @coroutine#2 async 블록 실행

    /***
     * 정리하면 withContext를 호출하면 코루틴이 유지된 채로 코루틴을 실행하는 스레드만 변경되기 때문에 동기적으로 실행되는 것이고,
     * async-await 쌍을 사용하면 새로운 코루틴을 만들지만 await 함수를 통해 순차 처리가 돼 동기적으로 실행되는 것이다.
     * 이런 차이로 인해 withContext 함수를 사용할 때는 주의해야 한다
     */
}