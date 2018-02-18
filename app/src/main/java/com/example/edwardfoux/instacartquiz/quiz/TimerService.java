package com.example.edwardfoux.instacartquiz.quiz;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TimerService extends Service {
    private CompositeDisposable disposableConsumer = new CompositeDisposable();

    public static final long QUIZ_INTERVAL = 30L; //30 seconds

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startCountDown();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        disposableConsumer.dispose();
        super.onDestroy();
    }

    private void startCountDown() {
        Consumer<Long> consumer = (aLong) -> {
            BroadcastRelay.getInstance().onBroadcastUpdate(aLong);

            if (aLong > QUIZ_INTERVAL) {
                TimerService.this.stopSelf();
            }
        };

        Disposable disposable = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .takeUntil(aLong -> aLong > QUIZ_INTERVAL)
                .subscribe(consumer);
        disposableConsumer.add(disposable);
    }
}
