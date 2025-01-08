package org.example.chapter3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class `Code3-7` {

    /***
     * 앞서 다룬 newFixedThreadPoolContext 함수를 시용해 CoroutineDispatcher 객체를 생성하면 다음과 같은 경고가 뜬다.
     * This is a delicate API and its use requires care. Make sure you fully read and understand documentation of the declaration that is marked as a delicate API.
     * “이는 섬세하게 다뤄져야 하는 API이다. 섬세하게 다뤄져야 하는 API는 문서를 모두 읽고 제대로 이해하고 사용돼야 한다.”
     *
     * 이런 경고를 하는 이유는 사용자가 newFixedThreadPoolContext 함수를 사용해 CoroutineDispatcher 객체를 만드는 것이 비효율적일 가능성이 높기 때문이다.
     * newFixedThreadPoolContext 함수를 사용해 CoroutineDispatcher 객체를 만들게 되면 특정 CoroutineDispatcher 객체에서만 사용되는 스레드풀이 생성되며，
     * 스레드풀에 속한 스레드의 수가 너무 적거나 많이 생성돼 비효율적으로 동작할 수 있다.
     *
     * 또한 여러 개발자가 함께 개발할 경우 특정 용도를 위해 만들어진 Coroutine Dispatcher 객체가 이미 메모리상에 있음에도 해당 객체의 존재를 몰라
     * 다시 CoroutineDispatcher 객체를 만들어 리소스를 낭비하게 될 수도 있다. 스레드의 생성 비용이 비싸다는 것을 기억하자.
     *
     * 코루틴 라이브러리는 개발자가 직접 CoroutineDispatcher 객체를 생성하는 문제의 방지를 위해 미리 정의된 CoroutineDispatcher의 목록을 제공한다.
     * 멀티 스레드 프로그래밍이 필요한 일반적인 상황에 맞춰 만들어졌기 때문에,
     * 사용자들은 매번 새로운 CoroutineDispatcher 객체를 만들 필요 없이 제공되는 Coroutine Dispatcher 객체를 사용해 코루틴을 실행하면 된다.
     *
     * Dispatchers.IO : 네트워크 요청이나 파일 입출력 등의 입출력(I/O) 작업을 위한 CoroutineDispatcher
     * Dispatchers.Default : CPU를 많이 사용하는 연산 작업을 위한 Coroutine Dispatcher
     * Dispatchers.Main: 메인 스레드를 사용하기 위한 CoroutineDispatcher
     *
     */

    fun test() = runBlocking {
        // 멀티 스레드 프로그래밍이 가장 많이 사용되는 작업은 입출력(I/O) 작업이다.
        // 애플리케이션에서는 네트워크 통신을 위해 HTTP 요청을 하거나 DB 작업 같은 입출력 작업 여러 개를 동시에 수행하므로 이런 요청을 동시에 수행하기 위해서는 많은 스레드가 필요하다.
        // 이를 위해 코루틴 라이브러리에서는 입출력 작업을 위해 미리 정의된 Dispatchers.IO를 제공한다.

        // Dispatchers.IO가 최대로 사용할 수 있는 스레드의 수는 JVM에서 사용이 가능한 프로세서의 수와 64 중 큰 값으로 설정 돼 있다.
        // 즉, Dispatchers.IO를 사용하면 여러 입출력 작업을 동시에 수행할 수 있다.
        launch(Dispatchers.IO) {
            println(Thread.currentThread().name + " 코루틴 실행")
        }
    }
    /***
     * 결과 :
     * DefaultDispatcher-worker-1 @coroutine#2 코루틴 실행
     *
     * 이름 앞에 DefaultDispatcher-worker-1이 붙은 스레드는 코루틴 라이브러리에서 제공하는 공유 스레드풀에 속한 스레드로
     * Dispatchers.IO는 공유 스레드풀의 스레드를 사용할 수 있도록 구현됐기 때문에 DefaultDispatcher-worker-1 스레드에 코루틴이 할당돼 실행된다
     */
}