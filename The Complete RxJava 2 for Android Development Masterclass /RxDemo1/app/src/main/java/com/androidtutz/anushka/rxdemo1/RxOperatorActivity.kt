package com.androidtutz.anushka.rxdemo1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class RxOperatorActivity : AppCompatActivity() {

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_operator)

        //operatorIgnoreElements()
        //operatorElementAt()
        //operatorFilter()
        //operatorSkip()
        //operatorSkipWhile()
        //operatorSkipUntil()
        //operatorTake()
        //operatorTakeWhile()
        //operatorTakeUntil()
        operatorDistinctUntilChanged()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    // available in Flowable and Observable
    private fun operatorIgnoreElements() {
        // ignoring elements which are emitted from onNext by Observable or Flowable and return Completable that signals only error or complete
        val strikes = PublishSubject.create<String>()
        strikes.onNext("X")
        strikes.onNext("X")
        strikes.onNext("X")
        strikes.onComplete()

        compositeDisposable.add(
                // Since this observable now has no elements, ignoreElements converts it into a Completable
                strikes.ignoreElements()
                        .subscribeBy {
                            println("You're out!")
                        }
        )

        compositeDisposable.add(
                Observable.just(1,2,3,4).ignoreElements() // transform observable into completable
                        .subscribe(
                                {
                                    println("completed")
                                },
                                {
                                    t -> println("error: ${t.message}")
                                }
                        )
        )

        compositeDisposable.add(
                Observable.create<Int> { emitter ->
                    emitter.onNext(1)
                    emitter.onNext(2)
                    emitter.onComplete()
                }.ignoreElements()
                        .subscribe(
                                {
                                    println("complete")
                                },
                                {
                                    t -> println("error: ${t.message}")
                                }
                        )
        )

        val source = Observable.intervalRange(1, 5, 1, 1, TimeUnit.SECONDS)
        val completable = source.ignoreElements()
        completable.doOnComplete { println("Done! after 5 seconds") }
                .blockingAwait()
    }

    // available in Flowable and Observable
    private fun operatorElementAt() {
        // Emits the single item at the specified zero-based index in a sequence of emissions from a reactive source.
        // A default item can be specified that will be emitted if the specified index is not within the sequence.

        //1. create strikes subject
        val strikes = PublishSubject.create<String>()

        //2. subscribe to the strikes observable, ignoring every elemnt other than the third (index 2)
        //   Since this observable may not have a third item, elementAt returns Maybe
        compositeDisposable.add(
                strikes
                        .doOnNext { s -> println("onNext: $s") }
                        .elementAt(2) // returns a Maybe, subscribe with onSuccess instead of onNext
                        .subscribeBy(
                                onSuccess = { s -> println("You're out! $s") },
                                onComplete = { println("complete") },
                                onError = { t -> println("Error: ${t.message}")}
                        )
        )
        strikes.onNext("X1")
        strikes.onNext("X2")
        strikes.onNext("X3")
        strikes.onNext("X4")
        strikes.onNext("X5")

        compositeDisposable.add(
                Observable.intervalRange(1, 5, 1, 1, TimeUnit.SECONDS)
                        .doOnNext { l -> println("onNext: $l") }
                        .elementAt(2)
                        .subscribeBy(
                                onSuccess = { s -> println("You're out! $s")},
                                onComplete = { println("complete")},
                                onError = { t -> println("Error: $t")}
                        )
        )
    }

    // available in Flowable, Observable, Maybe, and Single
    private fun operatorFilter() {
        //Filters items emitted by a reactive source by only emitting those that satisfy a specified predicate.

        compositeDisposable.add(
                Observable.fromIterable(listOf(1,2,3,4,5,6,7,8,9,10))
                        .filter { number ->
                            number > 5
                        }.subscribe {
                            println(it)
                        }
        )

        compositeDisposable.add(
                Maybe.fromSingle<Int>(Single.just(1))
                        .filter { number -> number > 4 }
                        .subscribe ({
                            println("$it is fit with predicate")
                        }, {
                            println("Error occured: $it")
                        }){
                            println("complete")
                        }
        )
    }

    // available in Flowable and Observable
    private fun operatorSkip() {
        // Drops the first n items emitted by a reactive source, and emits the remaining items.

        compositeDisposable.add(
                Observable.just(1,2,3,4,5,6,7,8,9,10)
                        .skip(4)
                        .subscribe(::println)
        )
        // print 5,6,7,8,9,10
    }

    private fun operatorSkipWhile() {
        // However, unlike filter, which will
        //filter elements for the life of the subscription, skipWhile will only skip up until
        //something is not skipped, and then it will let everything else through from that point
        //on. Also, with skipWhile, returning true will cause the element to be skipped, and
        //returning false will let it through: it’s the using the value in the opposite way to
        //filter.

        compositeDisposable.add(
                Observable.just(2,2,3,4)
                        .skipWhile { number -> number % 2 == 0 }
                        .subscribe { println(it) }
        )
        // print 3, 4
    }

    private fun operatorSkipUntil() {
        // skip subscribing first observable until the second observable emit item(s)
        val subject = PublishSubject.create<String>()
        val trigger = PublishSubject.create<String>()

        compositeDisposable.add(
                subject.skipUntil(trigger)
                        .subscribe {
                            println(it)
                        }
        )
        subject.onNext("A")
        subject.onNext("B")

        trigger.onNext("X") // second observable emits an item

        subject.onNext("C")
        // print: C

        // second example
        val observable1 = Observable.create<Int> { emitter ->
            for(i in 0 .. 6) {
                Thread.sleep(1000)
                emitter.onNext(i)
            }

            emitter.onComplete()
        }

        val observable2 = Observable
                .timer(3, TimeUnit.SECONDS)
                //.flatMap { Observable.just(11,12,13,14,15,16) }

        compositeDisposable.add(
                observable1.skipUntil(observable2)
                        .subscribe({
                            i -> println("$i")
                        }, {
                            t -> println("$t")
                        }, {
                            println("on Complete")
                        })
        )
    }

    // available in Flowable and Observable
    private fun operatorTake() {
        // inverse of skip()
        // take the first n items of elements

        compositeDisposable.add(
                Observable.just(1,2,3,4,5,6)
                        .take(3)
                        .subscribe {
                            println(it)
                        }
        )
    }

    private fun operatorTakeWhile() {
        // inverse of skipWhile()

        compositeDisposable.add(
                Observable.fromIterable(listOf(1,2,3,4,5,6,7,8,9,10,1))
                        .takeWhile { number -> number < 5 }
                        .subscribe { println(it) }
        )
    }

    private fun operatorTakeUntil() {
        //inverse of skipUntil()

        val subject = PublishSubject.create<String>()
        val trigger = PublishSubject.create<String>()

        compositeDisposable.add(
                subject.takeUntil(trigger)
                        .subscribe(::println)
        )

        subject.onNext("1")
        subject.onNext("2")

        trigger.onNext("X")

        subject.onNext("3") // not printed
    }

    private fun operatorDistinctUntilChanged() {
        // prevenet duplicate contiguous items from getting through

        val subject = PublishSubject.create<String>()

        compositeDisposable.add(
                subject.distinctUntilChanged()
                        .subscribe(::println)
        )

        subject.onNext("Dog")
        subject.onNext("Cat")
        subject.onNext("Cat") // skipped
        subject.onNext("Dog") // printed, as it is different with previous contiguous item
        subject.onNext("dog") // printed, as Dog != dog
        subject.onNext("cAt") // printed

        // variant of distinctUntilChanged
        compositeDisposable.add(
                Observable.just("ABC", "BCD", "CDE", "FGH", "IJK", "JKL", "LMN")
                        .distinctUntilChanged { first, second ->
                            second.toList().any { it in first.toList() }
                        }
                        .subscribe(::println)
        )
    }
}
