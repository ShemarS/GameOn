package com.example.gameon

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_login.editTextEmail
import kotlinx.android.synthetic.main.activity_login.editTextPassword

class CreateAccountActivity : AppCompatActivity() {

    private val TAG = "MAN WHAT"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null) {
            goDashboard()
        }
        textViewExistingUser.setOnClickListener() {
            goLogin()
        }

        buttonLogin.setOnClickListener{
            buttonLogin.isEnabled = false
            if(editTextEmail.text.toString().isEmpty() || editTextPassword.text.toString().isEmpty()) {
                Toast.makeText(this, "Email/password cannot be empty, please follow requirements.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //add Sql stuff
            auth.createUserWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString()).addOnCompleteListener{ task ->
                buttonLogin.isEnabled = true
                if(task.isSuccessful) {
                    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                    goDashboard()
                }
                else {
                    Log.e(TAG, "signInWithEmail failed", task.exception)
                    Toast.makeText(this, "Authentication failed: ${task.exception}", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }


    private fun goDashboard() {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
        finish()
    }

    private fun goLogin() {
        val myIntent = Intent(this, LoginActivity::class.java)
        startActivity(myIntent)
    }

}