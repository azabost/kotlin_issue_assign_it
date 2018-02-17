package com.azabost.kotlinissueapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import androidx.view.isVisible
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.observeOnMainTread(): Observable<T> =
    observeOn(AndroidSchedulers.mainThread())

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val someObservable = Observable.interval(1, TimeUnit.SECONDS).map { it % 2 == 0L }

        someObservable.bindToLifecycle(this).observeOnMainTread().subscribe {

            Log.d("visibility", it.toString())

            // This doesn't compile
            helloText.isVisible = it

            // This works:
            /*
            if (it) {
                helloText.isVisible = true
            } else {
                helloText.isVisible = false
            }
            */
        }
    }
}
