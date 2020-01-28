package com.example.gfriend.Login

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gfriend.MainActivity
import com.example.gfriend.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import android.app.ProgressDialog







class SignInFragment : Fragment() {
    private var mAuth: FirebaseAuth? = null
    var m_ProgressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        arguments?.let {
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_title.typeface = LoginAcivity.gordonFont
        signInButton.setOnClickListener {
            Log.d("signIn","start!")
            // プログレスダイアログの生成
            this.m_ProgressDialog = ProgressDialog(this.activity)
            // プログレスダイアログの設定
            this.m_ProgressDialog!!.setMessage("Loading...")  // メッセージをセット
            // プログレスダイアログの表示
            this.m_ProgressDialog!!.show()
            mAuth?.signInWithEmailAndPassword(username.text.toString(), password.text.toString())
                ?.addOnCompleteListener(LoginAcivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("signIn", "signInWithEmail:success")
                        if (this.m_ProgressDialog != null && this.m_ProgressDialog!!.isShowing) {
                            this.m_ProgressDialog!!.dismiss()
                        }
                        val user = mAuth?.currentUser
//                        Log.d(TAG, user.toString())
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("signIn", "signInWithEmail:failure", task.exception)
                        if (this.m_ProgressDialog != null && this.m_ProgressDialog!!.isShowing) {
                            this.m_ProgressDialog!!.dismiss()
                        }
                        Toast.makeText(this.context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
        toSignUpButton.setOnClickListener {
            val signUpFragment = SignUpFragment()
            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.container,signUpFragment)
            fragmentTransaction?.commit()
        }
    }

    companion object
}
