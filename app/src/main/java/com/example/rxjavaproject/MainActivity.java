package com.example.rxjavaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Button btnJust;
    Button btnFromArray;
    Button btnRange;
    Button btnStudentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnJust = findViewById(R.id.btnJust);
        btnFromArray = findViewById(R.id.btnFromArr);
        btnRange = findViewById(R.id.btnRange);
        btnStudentClass = findViewById(R.id.btnStudentClass);

        btnRange.setOnClickListener(view -> {
            Observable.range(20, 20).subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    Log.d("Amol", "onSubscribe: ");
                }

                @Override
                public void onNext(@NonNull Integer integer) {
                    Log.d("Amol", "Range: " + integer);
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });

        });
        btnJust.setOnClickListener(view -> {
            Observable<String> nameObservable = Observable.just("Amol");
            Observer<String> nameObserver = new Observer<String>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    Log.d("Amol", "My Name" + "onSubscribe");
                }

                @Override
                public void onNext(@NonNull String s) {
                    Log.d("Amol", "My Name" + s);
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            nameObservable.subscribe(nameObserver);
        });
        btnFromArray.setOnClickListener(view -> {
            String[] arr = {"Amol", "Abhishek", "Aditya", "Kunal", "Yash", "Akash"};
            Observable<String> fromArrayObservable = Observable.fromArray(arr);
            Observer<String> fromArrayObserver = new Observer<String>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    Log.d("Amol", " onSubscribe ");
                }

                @Override
                public void onNext(@NonNull String s) {
                    Log.d("Amol", " StringFromArray " + s);
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            fromArrayObservable.subscribe(fromArrayObserver);
        });
        btnStudentClass.setOnClickListener(view -> {
            Observable<Student> studentObservable = Observable.
                    fromIterable(getStudentList())
                    .filter(student -> student.getStudentName().length() > 6)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            Observer<Student> studentObserver = new Observer<Student>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    Log.d("Amol", " studentList " + "onSubscribe");
                }

                @Override
                public void onNext(@NonNull Student student) {
                    Log.d("Amol", "studentList " + student.getStudentName() + " " + student.getStudentId());
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {
                    Log.d("studentList", "onSubscribe");
                }
            };
            studentObservable.subscribe(studentObserver);
        });


    }

    private List<Student> getStudentList() {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0 || i % 3 == 0) {
                Student student = new Student(i, "Amol");
                studentList.add(student);
            } else {
                Student student = new Student(i, "Prateek");
                studentList.add(student);
            }
        }
        return studentList;
    }
}