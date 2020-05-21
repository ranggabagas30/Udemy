package com.androidtutz.anushka.rxdemo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final static String TAG="myApp";
    private String greeting="Hello From RxJava";
    private Observable<String> myObservable;
    private Observer<String> myObserver;
    private DisposableObserver<String> myDisposableObserver;
    private DisposableObserver<String> myDisposableObserver2;

    // from Array
    private String[] greetings = {"hello A", "hello B", "hello C"};
    private Observable<String> myObservableArrayString;
    private DisposableObserver<String> myDisposableObserverArrayString;

    private Integer[] numbers = {1,2,3,4};
    private Observable<Integer> myObservableArrayInteger;
    private DisposableObserver<Integer> myDisposableObserverArrInteger;

    private TextView textView;
    private Disposable disposable;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.tvGreeting);

        compositeDisposable = new CompositeDisposable();

//        myObservable=Observable.just(greeting);
//
//        compositeDisposable.add(
//                myObservable
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeWith(getObserver())
//        );

//        myObservableArrayString = Observable.fromArray(greetings);
//        compositeDisposable.add(
//                myObservableArrayString
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeWith(getObserverArrayString())
//        );
//
//        myObservableArrayInteger = Observable.fromArray(numbers);
//        compositeDisposable.add(
//                myObservableArrayInteger
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeWith(getObserverArrayInteger())
//        );

        // observable range(n,m) = n, n+1, n+2, ... n+m-1
//        int n = 1;
//        int m = 20;
//        compositeDisposable.add(
//                Observable.range(n, m)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeWith(
//                           new DisposableObserver<Integer>() {
//                               @Override
//                               public void onNext(Integer integer) {
//                                   Log.d(TAG, "onNext: "+ integer);
//                               }
//
//                               @Override
//                               public void onError(Throwable e) {
//                                   Log.e(TAG, "onError: ",e );
//                               }
//
//                               @Override
//                               public void onComplete() {
//                                   Log.d(TAG, "onComplete: range operator");
//                               }
//                           }
//                    )
//        );
//
//        // observable create
//        compositeDisposable.add(
//                Observable.create(new ObservableOnSubscribe<Student>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Student> emitter) throws Exception {
//                        ArrayList<Student> students = generateStudentList();
//                        for (Student student : students) {
//                            emitter.onNext(student);
//                        }
//                        emitter.onComplete();
//                    }
//                })
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeWith(new DisposableObserver<Student>() {
//                        @Override
//                        public void onNext(Student student) {
//                            Log.d(TAG, "onNext: " + student.getName());
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.e(TAG, "onError: " + e.getMessage(), e);
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            Log.d(TAG, "onComplete: create operator");
//                        }
//                    })
//        );
//
//        // operator map
//        compositeDisposable.add(
//                Observable.create(new ObservableOnSubscribe<Student>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Student> emitter) throws Exception {
//                        ArrayList<Student> students = generateStudentList();
//                        for (Student student : students) {
//                            emitter.onNext(student);
//                        }
//                        emitter.onComplete();
//                    }
//                })
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .map(new Function<Student, Student>() {
//                            @Override
//                            public Student apply(Student student) throws Exception {
//                                student.setName(student.getName().toUpperCase());
//                                return student;
//                            }
//                        })
//                        .subscribeWith(new DisposableObserver<Student>() {
//                            @Override
//                            public void onNext(Student student) {
//                                Log.d(TAG, "onNext: " + student.getName());
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "onError: " + e.getMessage(), e);
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "onComplete: map operator");
//                            }
//                        })
//        );

        // operator flatMap
//        compositeDisposable.add(
//                Observable.create(new ObservableOnSubscribe<Student>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Student> emitter) throws Exception {
//                        ArrayList<Student> students = generateStudentList();
//                        for (Student student : students) {
//                            emitter.onNext(student);
//                        }
//                        emitter.onComplete();
//                    }
//                })
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .flatMap(new Function<Student, ObservableSource<Student>>() {
//                            @Override
//                            public ObservableSource<Student> apply(Student student) throws Exception {
//                                Student student1 = new Student(student.getName() + " santoso", student.getEmail(), student.getAge(), student.getRegistrationDate());
//                                Student student2 = new Student(student.getName() + " new member", student.getEmail(), student.getAge(), student.getRegistrationDate());
//                                return Observable.just(student, student1, student2);
//                            }
//                        })
//                        .subscribeWith(new DisposableObserver<Student>() {
//                            @Override
//                            public void onNext(Student student) {
//                                Log.d(TAG, "onNext: " + student.getName());
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "onError: " + e.getMessage(), e);
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "onComplete: flatMap operator");
//                            }
//                        })
//        );
//
//        // operator concatMap. To prevent interleaving output which is a flatMap's drawback
//        compositeDisposable.add(
//                Observable.create(new ObservableOnSubscribe<Student>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Student> emitter) throws Exception {
//                        ArrayList<Student> students = generateStudentList();
//                        for (Student student : students) {
//                            emitter.onNext(student);
//                        }
//                        emitter.onComplete();
//                    }
//                })
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .concatMap(new Function<Student, ObservableSource<Student>>() {
//                            @Override
//                            public ObservableSource<Student> apply(Student student) throws Exception {
//                                Student student1 = new Student(student.getName() + " santoso", student.getEmail(), student.getAge(), student.getRegistrationDate());
//                                Student student2 = new Student(student.getName() + " new member", student.getEmail(), student.getAge(), student.getRegistrationDate());
//                                return Observable.just(student, student1, student2);
//                            }
//                        })
//                        .subscribeWith(new DisposableObserver<Student>() {
//                            @Override
//                            public void onNext(Student student) {
//                                Log.d(TAG, "onNext: " + student.getName());
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "onError: " + e.getMessage(), e);
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "onComplete: concatMap operator");
//                            }
//                        })
//        );

        // operator buffer
//        compositeDisposable.add(
//                Observable.range(1,18)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .buffer(4)
//                    .subscribeWith(new DisposableObserver<List<Integer>>() {
//                        @Override
//                        public void onNext(List<Integer> integers) {
//                            Log.d(TAG, "onNext: ");
//                            for (Integer i : integers) {
//                                Log.d(TAG, i + " ");
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.e(TAG, "onError: " + e.getMessage(), e);
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            Log.d(TAG, "onComplete: buffer operator");
//                        }
//                    })
//        );
//
//        // operator buffer with skip
//        compositeDisposable.add(
//                Observable.range(1,18)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .buffer(1, 2)
//                        .subscribeWith(new DisposableObserver<List<Integer>>() {
//                            @Override
//                            public void onNext(List<Integer> integers) {
//                                Log.d(TAG, "onNext: ");
//                                for (Integer i : integers) {
//                                    Log.d(TAG, i + " ");
//                                }
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "onError: " + e.getMessage(), e);
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "onComplete: buffer with skip operator");
//                            }
//                        })
//        );

        // operator filter
//        compositeDisposable.add(
//                Observable.range(1,18)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .filter(new Predicate<Integer>() {
//                            @Override
//                            public boolean test(Integer integer) throws Exception {
//                                return integer % 3 == 0;
//                            }
//                        })
//                        .subscribeWith(new DisposableObserver<Integer>() {
//                            @Override
//                            public void onNext(Integer integer) {
//                                Log.d(TAG, "onNext: " + integer);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "onError: " + e.getMessage(), e);
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "onComplete: filter operator");
//                            }
//                        })
//        );

        // operator distinct
//        Observable<Integer> numbersObservable = Observable.just(1, 2, 3, 7, 5, 3, 5, 5, 4, 4);
//        compositeDisposable.add(
//                numbersObservable
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .distinct()
//                    .subscribeWith(new DisposableObserver<Integer>() {
//                        @Override
//                        public void onNext(Integer integer) {
//                            Log.d(TAG, "onNext: " + integer);
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.e(TAG, "onError: " + e.getMessage(), e);
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            Log.d(TAG, "onComplete: distinct operator");
//                        }
//                    })
//        );

        // operator skip n items at first emit or skipLast
        Observable<Integer> numbersObservable = Observable.just(1, 2, 3, 7, 5, 3, 5, 5, 4, 4);
        compositeDisposable.add(
                numbersObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        //.skip(6)
                        .skipLast(6)
                        .subscribeWith(new DisposableObserver<Integer>() {
                            @Override
                            public void onNext(Integer integer) {
                                Log.d(TAG, "onNext: " + integer);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError: " + e.getMessage(), e);
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete: filter operator");
                            }
                        })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private DisposableObserver getObserver() {
        myDisposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext invoked: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError invoked", e);

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete invoked");
            }
        };
        return myDisposableObserver;
    }

    private DisposableObserver getObserverArrayString() {
        myDisposableObserverArrayString = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext invoked: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError invoked", e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete invoked");
            }
        };
        return myDisposableObserverArrayString;
    }

    private DisposableObserver getObserverArrayInteger() {
        myDisposableObserverArrInteger = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "onNext: invoked: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError invoked", e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete invoked");
            }
        };
        return myDisposableObserverArrInteger;
    }

    private ArrayList<Student> generateStudentList() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(
                "Rangga Bagas",
                "rangga.bagas0@gmail.com",
                23,
"01-01-2020"
        ));

        students.add(new Student(
                "Rangga Vega",
                "ranggave@gmail.com",
                20,
                "02-12-2019"
        ));

        students.add(new Student(
                "Rangga Maheswara",
                "ranggamaheswara@gmail.com",
                19,
                "23-11-2018"
        ));
        return students;
    }

    private class Student {
        private String name;
        private String email;
        private int age;
        private String registrationDate;

        public Student(String name, String email, int age, String registrationDate) {
            this.name = name;
            this.email = email;
            this.age = age;
            this.registrationDate = registrationDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getRegistrationDate() {
            return registrationDate;
        }

        public void setRegistrationDate(String registrationDate) {
            this.registrationDate = registrationDate;
        }
    }
}
