package com.example.gfriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.gfriend.Login.LoginAcivity
import android.graphics.Typeface
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    private val handler = Handler()
    private val runnable = Runnable {

        // MainActivityへ遷移させる
//        val intent = Intent(this, LoginActivity::class.java)
        val intent = Intent(this, LoginAcivity::class.java)
        startActivity(intent)
        // ここでfinish()を呼ばないとMainActivityでAndroidの戻るボタンを押すとスプラッシュ画面に戻ってしまう
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val customFont = Typeface.createFromAsset(assets, "gordon__.ttf")
        splash_Text.typeface = customFont
        handler.postDelayed(runnable, 2000)
    }
    override fun onStop() {
        super.onStop()
        // スプラッシュ画面中にアプリを落とした時にはrunnableが呼ばれないようにする
        // これがないとアプリを消した後にまた表示される
        handler.removeCallbacks(runnable)
    }
}
