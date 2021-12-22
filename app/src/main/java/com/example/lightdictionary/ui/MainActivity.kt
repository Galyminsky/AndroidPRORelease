package com.example.lightdictionary.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lightdictionary.R
import com.example.model.LoadWordsState
import com.example.model.WordEntity
import com.example.lightdictionary.presenter.main.MainController
import com.example.lightdictionary.utils.viewById
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getOrCreateScope
import org.koin.core.scope.Scope

private const val SEARCH_INPUT_FRAGMENT_TAG = "SEARCH_INPUT_FRAGMENT_TAG"
private const val SPLASH_SCREEN_COUNTDOWN_DURATION = 2000L
private const val SPLASH_SCREEN_COUNTDOWN_INTERVAL = 1000L
private const val SPLASH_SCREEN_SLIDE_LEFT_DURATION = 1000L

class MainActivity : AppCompatActivity(), MainController.View, SearchInputFragment.Controller, KoinScopeComponent {
    private val adapter: MainAdapter by lazy { MainAdapter(::onItemClicked) }

    override val scope: Scope
        get() = getOrCreateScope().value
    private val model = scope.get<MainController.BaseViewModel>()

    private val wordsRecyclerView by viewById<RecyclerView>(R.id.words_recycler_view)
    private val searchFab by viewById<FloatingActionButton>(R.id.search_fab)
    private val progressLayout by viewById<FrameLayout>(R.id.progress_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model.searchLiveData.observe(this) { renderSearchData(it) }
        model.loadStateLiveData.observe(this) { renderLoadState(it) }
        model.detailLiveData.observe(this) { renderDetailData(it) }

        wordsRecyclerView.adapter = adapter
        wordsRecyclerView.layoutManager = LinearLayoutManager(this)

        searchFab.setOnClickListener { model.onSearchClicked() }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setSplashScreenAnimation()
            setSplashScreenDuration()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_app_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> false
        }
    }

    override fun renderSearchData(state: Boolean) {
        if (state) {
            showSearchInputScreen()
            model.onSearchScreenOpened()
        }
    }

    override fun renderDetailData(word: WordEntity?) {
        if (word != null) {
            showDetailScreen(word)
            model.onDetailScreenOpened()
            searchFab.hide()
        }
    }

    override fun renderLoadState(state: LoadWordsState) {
        when (state) {
            is LoadWordsState.Success -> {
                showLoading(false)
                showWords(state.words)
            }
            is LoadWordsState.Error -> {
                showLoading(false)
                state.error.localizedMessage?.let { showError(it) }
                    ?: showError(state.error.message.toString())
            }
            is LoadWordsState.Loading -> {
                showLoading(true)
            }
        }
    }

    private fun showSearchInputScreen() {
        SearchInputFragment.newInstance().show(supportFragmentManager, SEARCH_INPUT_FRAGMENT_TAG)
    }

    private fun showDetailScreen(word: WordEntity) {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, DetailFragment.newInstance(word))
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        searchFab.show()
    }

    private fun showError(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun showWords(words: List<WordEntity>) {
        adapter.updateList(words)
    }

    private fun showLoading(isLoading: Boolean) {
        progressLayout.visibility = when (isLoading) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    override fun setNewWord(word: String) {
        model.onGetInputWord(word)
    }

     private fun onItemClicked(word: WordEntity) {
         model.onRecycleItemClicked(word)
     }

    override fun onDestroy() {
        scope.close()
        super.onDestroy()
    }

    private fun setSplashScreenDuration() {
        var isHideSplashScreen = false

        object : CountDownTimer(SPLASH_SCREEN_COUNTDOWN_DURATION, SPLASH_SCREEN_COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                isHideSplashScreen = true
            }
        }.start()

        val content: View by viewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isHideSplashScreen) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setSplashScreenAnimation() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.width.toFloat()
            ).apply {
                interpolator = AnticipateInterpolator()
                duration = SPLASH_SCREEN_SLIDE_LEFT_DURATION
                doOnEnd {
                    this@MainActivity.recreate()
                    splashScreenView.remove()
                }
                start()
            }
        }
    }
}