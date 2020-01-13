package com.egiwon.publictransport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.egiwon.publictransport.data.response.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    val favouriteList = mutableListOf<Item>()

    val setFavoriteSubject: BehaviorSubject<Item> = BehaviorSubject.create()
    val removeFavoriteSubject: BehaviorSubject<Item> = BehaviorSubject.create()

    private val compositeDisposable = CompositeDisposable()

    fun Disposable.addDisposable() {
        compositeDisposable.add(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_bus_station, R.id.navigation_favorite
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)

        setFavoriteSubject.subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                favouriteList.add(it)
            }.addDisposable()

        removeFavoriteSubject.subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                favouriteList.remove(it)
            }.addDisposable()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
