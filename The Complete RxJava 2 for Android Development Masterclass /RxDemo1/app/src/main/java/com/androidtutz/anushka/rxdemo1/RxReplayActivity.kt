package com.androidtutz.anushka.rxdemo1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class RxReplayActivity : AppCompatActivity() {

    // You’ll often want
    // to represent an infinite stream that will never terminate
    // RxRelay mimics all of the subjects you’ve come to know and
    //love, but without the option of calling onComplete or onError

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_replay)

        publishRelay()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun publishRelay() {

        val publishRelay = PublishRelay.create<Int>()

        compositeDisposable.add(
                publishRelay.subscribeBy {
                            println("1) $it")
                        }
        )

        publishRelay.accept(1)
        publishRelay.accept(2)
        publishRelay.accept(3)
    }
}
