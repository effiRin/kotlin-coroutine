package org.example.chapter3

import kotlinx.coroutines.*

class `Code3-10` {

    /***
     * Dispatchers.IO와 Dispatchers.Default에서 다룬 코드의 실행 결과를 살펴보면 두 결과 모두 코루틴을 실행시킨 스레드의 이름이 DefaultDispatcher-worker-1 인 것을 볼 수 있다.
     * 이는 Dispatchers.IO와 Dispatchers.Default가 같은 스레드 풀을 시용한다는 것을 의미한다.
     * 즉, 둘은 코루틴 라이브러리의 공유 스레드풀을 사용한다.
     *
     * 코루틴 라이브러리는 스레드의 생성과 관리를 효율적으로 할 수 있도록 애플리케이션 레벨의 공유 스레드풀을 제공한다.
     * 이 공유 스레드풀에서는 스레드를 무제한으로 생성할 수 있으며，코루틴 라이브러리는 공유 스레드풀에 스레드를 생성하고 사용할 수 있도록 하는 API를 제공한다.
     * Dispatchers.IO와 DispatchersDefault는 모두 이 API를 사용해 구현됐기 때문에 같은 스레드풀을 사용하는 것이다.
     * 물론, 스레드풀 내에서 Dispatchers.IO와 Dispatchers.Default가 사용하는 스레드는 구분되며,
     * Dispatchers.Default.limitedParaU^ Dispatchers.Default의 여러 스레드 중 2개의 스레드만 사용하는 것
     *
     *  하지만, Dispatchers.IO의 limitedParallelism은 조금 다르다.
     *  Dispatchers.IO의 limitedParallelism 함수는 공유 스레드 풀의 스레드로 구성된 새로운 스레드 풀을 만들어내며，
     *  만들어낼 수 있는 스레드에 제한이 있는 Dispatchers.IO나 Dispatchers.Default와 달리 스레드의 수를 제한 없이 만들어낼 수 있다.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun test() = runBlocking {
        // Dispatchers.IO.limitedParallelism(100)을 통해 공유 스레드풀 상에서 100개의 스레드로 구성된 스레드풀을 만들어낸다.
        // 따라서 이 코드를 실행 시켜 보면, 공유 스레드 풀에서 사용하는 스레드의 개수가 100개 정도 되는 것을 볼 수 있다.
        launch(Dispatchers.IO.limitedParallelism(100)) {
            repeat(1000) {
                launch {
                    delay(1000)
                    println(Thread.currentThread().name + " 실행")
                }
            }
        }
    }

    /***
     * 그렇다면, Dispatchers.IO의 limitedParallelism을 사용해야 될 때는 언제일까?
     * 바로 특정한 작업이 다른 작업에 영향을 받지 않아야 해, 별도 스레드풀에서 실행 되는 것이 필요할 때 사용해야 한다 .
     * 다만, 이 함수는 공유 스레드 풀에서 새로운 스레드를 만들어내고, 새로운 스레드를 만들어내는 것은 비싼 작업이므로 남용하지 말도록 하자.
     */
}