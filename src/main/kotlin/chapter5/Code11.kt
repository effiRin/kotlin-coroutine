package org.example.chapter5

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class Code11 {

    fun `Code5-11`() = runBlocking {
        val startTime = System.currentTimeMillis()
        val helloString = withContext(Dispatchers.IO) {
            delay(1000L)
            return@withContext "Hello"
        }

        val worldString = withContext(Dispatchers.IO) {
            delay(1000L)
            return@withContext "World"
        }

        val endTime = System.currentTimeMillis()

        println("[${(endTime - startTime)} ms] ${helloString} ${worldString}")
    }

    /***
     * 이 코드에서는 withContext(Dispatchers.IO)를 사용해 1초간 대기 후 “Hello” 문자를 반환하는 작업과 1초간 대기 후 “World” 문자를 반환하는 두 가지 작업을 실행한다.
     * 각 작업은 withContext(Dispatchers.IO)를 통해 백그라운드 스레드에서 병렬적으로 실행되는 것처럼 보이지만 실제로는 순차적으로 실행된다.
     * 따라서 코드의 실행 결과를 보면 총 2초의 시간이 걸린 것으로 나온다
     */
}