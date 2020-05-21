package com.androidtutz.anushka.rxdemo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.ReplaySubject;

public class RxSubjectActivity extends AppCompatActivity {

    private static final String TAG = RxSubjectActivity.class.getSimpleName(); 
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_subject);

        compositeDisposable = new CompositeDisposable();

        //asyncSubject();
        //asyncSubject2();
        //behaviorSubject();
        //behaviorSubject2();
        replaySubject();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void asyncSubject() {
        // AsyncSubject
        // emit the last item even after completion of data flow
        Observable<String> observableString = Observable.just("JAVA", "KOTLIN", "XML", "JSON");
        AsyncSubject<String> asyncSubject = AsyncSubject.create();  // role as a pipe between observable and observer

        observableString.subscribe(asyncSubject);

        asyncSubject.subscribe(getFirstObserver());
        asyncSubject.subscribe(getSecondObserver());
        asyncSubject.subscribe(getThirdObserver());
    }

    private void asyncSubject2() {
        // AsyncSubject
        // emit the last item even after completion of data flow

        // here async subject act as an observable and has its own subscriber / observer
        AsyncSubject<String> asyncSubject = AsyncSubject.create();  // role as a pipe between observable and observer

        asyncSubject.subscribe(getFirstObserver());

        asyncSubject.onNext("JAVA");
        asyncSubject.onNext("KOTLIN");
        asyncSubject.onNext("XML");

        asyncSubject.subscribe(getSecondObserver());

        asyncSubject.onNext("JSON");
        asyncSubject.onComplete();

        asyncSubject.subscribe(getThirdObserver());
    }

    private void behaviorSubject() {
        // BehaviorSubject
        // emit the last item before subscription happens and their succeeding values data flow
        Observable<String> observableString = Observable.just("JAVA", "KOTLIN", "XML", "JSON");
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();  // role as a pipe between observable and observer

        observableString.subscribe(behaviorSubject);

        behaviorSubject.subscribe(getFirstObserver());
        behaviorSubject.subscribe(getSecondObserver());
        behaviorSubject.subscribe(getThirdObserver());
    }

    private void behaviorSubject2() {
        // BehaviorSubject
        // emit the last item before subscription happens and their succeeding values data flow

        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();  // role as a pipe between observable and observer

        behaviorSubject.subscribe(getFirstObserver());

        behaviorSubject.onNext("JAVA");
        behaviorSubject.onNext("KOTLIN");
        behaviorSubject.onNext("XML");

        behaviorSubject.subscribe(getSecondObserver());

        behaviorSubject.onNext("JSON");
        behaviorSubject.onComplete();

        behaviorSubject.subscribe(getThirdObserver());
    }

    private void replaySubject() {

        ReplaySubject<String> replaySubject = ReplaySubject.createWithSize(2); // buffer size = 2
        replaySubject.onNext("1");
        replaySubject.onNext("2");
        replaySubject.onNext("3");

        compositeDisposable.add(
                replaySubject.subscribe(
                        new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                System.out.println("1) " + s);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(TAG, "1) " + throwable.getMessage(), throwable);
                            }
                        }
                )
        );

        compositeDisposable.add(
                replaySubject.subscribe(
                        new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                System.out.println("2) " + s);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(TAG, "2) " + throwable.getMessage(), throwable);
                            }
                        }
                )
        );

        // print:
        // 1) 2
        // 1) 3
        // 2) 2
        // 2) 3

        replaySubject.onNext("4");
        replaySubject.onError(new RuntimeException("Error!"));

        compositeDisposable.add(
                replaySubject.subscribe(
                        new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Log.d(TAG, "3) " + s);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(TAG, "3) " + throwable.getMessage(), throwable);
                            }
                        }
                )
        );

        // print :
        // 1) 4
        // 2) 4
        // 3) 3 -> from buffer
        // 3) 4

        // with error exception at line 154, will print
        // 1) 4 -> from buffer -> still hanging around
        // 2) 4 -> from buffer
        // 1) java.lang.RuntimeException: Error! -> lastly emitted
        // 2) java.lang.RuntimeException: Error! -> lastly emitted
        // 3) 3 -> from buffer
        // 3) 4 -> from buffer
        // 3) java.lang.RuntimeException: Error! -> lastly emitted

    }

    private Observer<String> getFirstObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "first observer onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "first observer onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "first observer onError", e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "first observer onComplete: ");
            }
        };
    }

    private Observer<String> getSecondObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "second observer onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "second observer onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "second observer onError", e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "second observer onComplete: ");
            }
        };
    }

    private Observer<String> getThirdObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "third observer onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "third observer onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "third observer onError", e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "third observer onComplete: ");
            }
        };
    }
}
