package org.example.chapter3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class `Code3-11` {

    fun test() = runBlocking {
        launch(Dispatchers.Main) {
            println(Thread.currentThread().name + " 실행")
        }
    }

    // Dispatchers.Main에 접근하면 IllegalStateException과 함께 메인 디스패처를 제공하는 모듈이 없다는 오류가 발생하는 것을 볼 수 있다.
    // 이 오류는 Dispatchers.Main의 구현체를 제공하는 라이브러리가 없어 생기는 오류로 메인 디스패처를 사용하려면 앞서 설명했듯이
    // 이에 대한 의존성을 제공하는 별도의 라이브러리를 추가 해야 한다.
    // Dispatchers.Main은 일반적으로 UI가 있는 애플리케이션에서 UI를 업데이트하는 데 사용된다.

}