package org.example.chapter3

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking

class `Code3-5` {
    /***
     * newFixedThreadPool Context 함수로 만들어진 CoroutineDispatcher의 모습은 newSingleThread Context 함수를 호출해 만들어진 CoroutineDispatcher와 매우 비슷하다.
     * 그 이유는 newSingleThreadContext가 내부적으로 newFixedThreadPoolContext를 사용하도록 구현돼 있기 때문이다.
     * newSingleThreadContext는 newFixedThreadPoolContext에 스레드의 개수 를 결정하는 nThreads 인자로 1을 넘겨 하나의 스레드만 갖게 된다.
     * 즉，두 함수는 같은 함수라고 봐도 무방하다.
     */
    /***
     * newFixedThreadPool Context 함수로 만들어진 CoroutineDispatcher의 모습은 newSingleThread Context 함수를 호출해 만들어진 CoroutineDispatcher와 매우 비슷하다.
     * 그 이유는 newSingleThreadContext가 내부적으로 newFixedThreadPoolContext를 사용하도록 구현돼 있기 때문이다.
     * newSingleThreadContext는 newFixedThreadPoolContext에 스레드의 개수 를 결정하는 nThreads 인자로 1을 넘겨 하나의 스레드만 갖게 된다.
     * 즉，두 함수는 같은 함수라고 봐도 무방하다.
     */

    fun test() = runBlocking {

        val multiThreadDispatcher: CoroutineDispatcher = newFixedThreadPoolContext(nThreads = 2, name = "MultiThread")

        // 실행 환경에 따라 각 코루틴이 다른 속도로 처리될 수 있기 때문에 사용되는 스레드가 다른 순서로 나오거나 일부 스레드만 사용될 수 있다.
        // 만약 코드를 실행했는데 생성된 스레드 2개를 모두 확인하지 못했다면 여러번 실행해 보자.
        launch(context = multiThreadDispatcher) {
            println(Thread.currentThread().name + " 실행")
        }

        launch(context = multiThreadDispatcher) {
            println(Thread.currentThread().name + " 실행")
        }
    }
}