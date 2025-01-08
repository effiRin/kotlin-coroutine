package org.example.chapter3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class `Code3-8` {

    /***
     * 대용량 데이터를 처리해야 하는 작업처럼 CPU 연산이 필요한 작업이 있다.
     * 이런 작업을 CPU 바운드 작업이라고 한다. Dispatchers.Default는 CPU 바운드 작업이 필요할 때 사용하는 CoroutineDispatcher이다.
     * Dispatchers.Default도 그 자체로 싱글톤 인스턴스이므로 다음과 같이 사용할 수 있다.
     */
    fun test() = runBlocking {
        launch(Dispatchers.Default) {
            println(Thread.currentThread().name + " 코루틴 실행")
        }
    }
    /***
     * 입출력 작업과 CPU 바운드 작업의 중요한 차이는 작업이 실행됐을 때 스레드를 지속적으로 사용하는지의 여부이다.
     * 일반적으로 입출력 작업은 작업(네트워크 요청，DB 조회 요청 등)을 실행한 후 결과를 반환받을 때까지 스레드를 사용하지 않는다.
     * 반면에 CPU 바운드 작업은 작업을 하는 동안 스레드를 지속적으로 사용한다.
     * 이 차이로 인해 입출력 작업과 CPU 바운드 작업이 스레드 기반 작업을 사용해 실행됐을 때와 코루틴을 사용해 실행됐을 때 효율성에 차이가 생긴다.
     *
     * 입출력 작업을 코루틴을 사용해 실행하면 입출력 작업 실행 후 스레드가 대기하는 동안 해당 스레드에서 다른 입출력 작업을 동시에 실행할 수 있어서 효율적이다.
     * 반면에 CPU 바운드 작업은 코루틴을 사용해 실행하더라도 스레드가 지속적으로 사용되기 때문에 스레드 기반 작업을 사용해 실행됐을 때와 처리 속도에 큰 차이가 없다.
     *
     * 즉, 정리하면 다음과 같다
     *                       입출력(I/O) 작업 | CPU 바운드 작업
     * 스레드 기반 작업 사용 시 |      느림       |     비슷
     * 코루틴 사용 시         |      빠름       |     비슷
     *
     */
}