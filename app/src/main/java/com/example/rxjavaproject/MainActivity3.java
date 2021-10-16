package com.example.rxjavaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // RxJava-|| You Part
        Observable<Task> taskObservable = Observable.create(new ObservableOnSubscribe<Task>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Task> emitter) throws Throwable {
                List<Task> taskList = getTaskList();
                for (Task task :taskList){
                    if (!emitter.isDisposed())emitter.onNext(task);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        Observer<Task> observer = new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task task) {
         //       Log.d("Amol", "onNext: "+task.getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        taskObservable.subscribe(observer);

        //using map
        StudentResponse studentResponse = new StudentResponse(getStudentList());
        Observable<Student>  studentObservable = Observable.just(studentResponse)
                .flatMap(new Function<StudentResponse, Observable<Student>>() {
                    @Override
                    public Observable<Student> apply(StudentResponse studentResponse) throws Throwable {
                        List<Student> studentList= studentResponse.getStudentList();
                        return Observable.fromIterable(studentList).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
                    }
                }).filter(new Predicate<Student>() {
                    @Override
                    public boolean test(Student student) throws Throwable {
                        return student.getMarks()>75;
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        Observer<Student> studentObserver = new Observer<Student>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Student student) {
                Log.d("Amol", "onNext: "+student.getStudentName()+" "+student.getMarks());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        studentObservable.subscribe(studentObserver);

    }


    private List<Student> getStudentList(){
        List<Student> studentList = new ArrayList<>();
        for (int i=0; i<5; i++){
            Student student1 = new Student(i+10, "Amol"+i,100); studentList.add(student1);
            Student student2 = new Student(i+20, "Kunal"+i,70); studentList.add(student2);
            Student student3 = new Student(i+30, "Deepak"+i,80); studentList.add(student3);
            Student student4 = new Student(i+40, "Piyush"+i,90); studentList.add(student4);
            Student student5 = new Student(i+50, "Akash"+i,60); studentList.add(student5);
            Student student6 = new Student(i+60, "Abhishek"+i,95); studentList.add(student6);
        }
        return studentList;
    }
    private List<Task> getTaskList() {
        List<Task> taskList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                Task task = new Task(i, "Task - " + i, true);
                taskList.add(task);
            } else {
                Task task = new Task(i, "Task - " + i, false);
                taskList.add(task);
            }
        }
        return taskList;
    }
}