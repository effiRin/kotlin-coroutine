package org.example.chapter5

import kotlinx.coroutines.*

class Code12 {
    fun `Code5-12`() = runBlocking {
        val startTime = System.currentTimeMillis()
        val helloDeferred = async(Dispatchers.IO) {
            delay(1000L)
            return@async "Hello"
        }

        val worldDeferred = async {
            delay(1000L)
            return@async "World"
        }

        val results = awaitAll(helloDeferred, worldDeferred)

        val endTime = System.currentTimeMillis()

        println("[${(endTime - startTime)} ms] ${results[0]} ${results[1]}")
    }

    /***
     * helloDeferred 코루틴과 worldDeferred 코루틴이 모두 실행된 뒤에 awaitAII 함수가 호출됐다.
     * 2개의 코루틴이 병렬로 실행돼 코드 실행이 1초 가량 걸림
     *
     * 이처럼 withContext 함수를 사용하면 코드가 깔끔해 보이는 효과를 내지만 잘못 사용하게 되면 코루틴을 동기적으로 실행하도록 만들어 코드 실행 시간이 배 이상으로 증가할 수 있다.
     * 따라서 withContext 함수가 새로운 코루틴을 만들지 않는다는 것을 명심하고 사용하자
     */
}