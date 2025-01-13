package org.example.chapter3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class `Code3-9` {

    /***
     * Dispatchers.Default를 사용해 무겁고 오래 걸리는 연산을 처리하면 특정 연산을 위해 Dispatchers.Default의 모든 스레드가 사용될 수 있다.
     * 이 경우 해당 연산이 모든 스레드를 사용하는 동안 Dispatchers.Default를 사용하는 다른 연산이 실행 되지 못한다.
     * 이의 방지를 위해 코루틴 라이브러리는 Dispatchers.Default의 일부 스레드만 사용해 특정 연산을 실행할 수 있도록 하는 limitedParallelism 함수를 지원한다.
     * 이 함수는 다음과 같이 사용할 수 있다.
     */

    fun test() = runBlocking {
        launch(Dispatchers.Default.limitedParallelism(2)) {
            repeat(10) {
                launch {
                    println(Thread.currentThread().name + " 코루틴 실행")
                }
            }
        }
    }
    /***
     * Dispatchers.Default의 여러 스레드 중 2개의 스레드만 사용해 10개의 코루틴을 실행시킨다.
     * 따라서 결과에서 코루틴을 실행하는 데 사용된 스레드를 보면 DefaultDispatcher-worker-1 과 DefaultDispatcher-worker-2만 사용된 것을 볼 수 있다.
     *
     * // 결과
     * DefaultDispatcher-worker-1 @coroutine#3 코루틴 실행
     * DefaultDispatcher-worker-1 @coroutine#4 코루틴 실행
     * DefaultDispatcher-worker-1 @coroutine#5 코루틴 실행
     * DefaultDispatcher-worker-1 @coroutine#6 코루틴 실행
     * DefaultDispatcher-worker-2 @coroutine#7 코루틴 실행
     * DefaultDispatcher-worker-1 @coroutine#8 코루틴 실행
     * DefaultDispatcher-worker-2 @coroutine#9 코루틴 실행
     * DefaultDispatcher-worker-1 @coroutine#10 코루틴 실행
     * DefaultDispatcher-worker-2 @coroutine#11 코루틴 실행
     * DefaultDispatcher-worker-1 @coroutine#12 코루틴 실행
     */
}