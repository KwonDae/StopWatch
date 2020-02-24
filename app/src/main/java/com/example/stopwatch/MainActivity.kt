package com.example.stopwatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time =0 // 시간을 계산할 변수를 0으로 초기화
    private var isRunning = false
    private var timerTask: Timer? = null // timerTask 변수를 null을 허용하는 Timer 타입으로 선언
    private var lap = 1 //  변수 lap을 1로 초기화하여 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            isRunning = !isRunning // FAB이 클릭되면 타이머가 동작 중인지를 저장하는 isRunning 변수의 값을 반전시키고 그 상태에 따라서

            if(isRunning) { // 타이머를 시작 또는 정지시킨다
                start()
            } else {
                pause()
            }
        }

        lapButton.setOnClickListener {
            recordLapTime()
        }
    }

    private fun start() {0
        fab.setImageResource(R.drawable.ic_pause_black_24dp) // 타이머를 시작하는 FAB을 누르면 FAB이미지를 일시정지 이미지로 변경

        timerTask = timer(period = 10) {
            // 0.01초 마다 이 변수를 증가시키 면서 UI를 갱신
            time++ // 계산한 초와 밀리초를 각각의 텍스트 뷰에 설정하도록 함
            val sec = time / 100
            val milli = time % 100
            runOnUiThread { // timer는 워커 스레드에서 동작하여 UI 조작이 불가능하므로, runOnUiThread로 감싸서 UI 조작이 가능하게 한다.
                secTextView.text = "$sec"
                millTextView.text = "$milli"
            }
        }
    }

    private fun pause() {
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp) // 타이머 시작과 반대로 FAB를 클릭하면 FAB의 이미지를 시작 이미지로 교체
        timerTask?.cancel() // 실행중인 타이머가 있다면 타이머를 취소한다.

        /**
         * The timer task queue.  This data structure is shared with the timer
         * thread.  The timer produces tasks, via its various schedule calls,
         * and the timer thread consumes, executing timer tasks as appropriate,
         * and removing them from the queue when they're obsolete.
         */
    }

    private fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this) // 동적으로 TextView를 생성하여 텍스트값을
        textView.text = "$lap LAP : ${lapTime / 100}.${lapTime % 100}" // '1 Lap : 5.35' 와 같은 형태가 되도록 시간을 계산하여 문자열로 설정

        //맨 위에 랩타임 추가하고  lap 변수는 다음을 위해 1 증가
        lapLayout.addView(textView,0)
        lap++

    }
}
