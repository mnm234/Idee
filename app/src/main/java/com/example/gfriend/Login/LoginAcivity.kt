package com.example.gfriend.Login

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
        fragmentTransaction.replace(R.id.container, signInFragment)
        fragmentTransaction.commit()
        }
    }
