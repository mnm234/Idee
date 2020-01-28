package com.example.gfriend.Login

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gfriend.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginAcivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val signInFragment = SignInFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        manifestFont = Typeface.createFromAsset(assets, "MANIFESTO.ttf")
        gordonFont = Typeface.createFromAsset(assets, "gordon__.ttf")
        fragmentTransaction.replace(R.id.container, signInFragment)
        fragmentTransaction.commit()
        }
    companion object{
        lateinit var manifestFont :Typeface
        lateinit var gordonFont : Typeface
    }
    }
