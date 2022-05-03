package com.example.gameon

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_create_account.buttonLogin
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.editTextEmail
import kotlinx.android.synthetic.main.activity_login.editTextPassword

class CreateAccountActivity : AppCompatActivity() {

    private val TAG = "MAN WHAT"
    private val bannedWords = listOf("ADD", "ADD CONSTRAINT", "ALL", "ALTER ", "ALTER COLUMN", "ALTER TABLE", "AND", "ANY", "AS", "ASC", "BACKUP DATABASE", "BETWEEN",
        "CASE", "CHECK", "COLUMN", "CONSTRAINT", "CREATE", "CREATE DATABASE", "CREATE INDEX", "CREATE OR REPLACE VIEW", "CREATE TABLE", "CREATE PROCEDURE", "CREATE UNIQUE INDEX",
        "CREATE VIEW", "DATABASE", "DEFAULT", "DELETE", "DELETE", "DESC", "DISTINCT", "DROP", "DROP COLUMN", "DROP CONSTRAINT", "DROP DATABASE", "DROP DEFAULT", "DROP INDEX",
        "DROP TABLE", "DROP VIEW", "EXEC", "FOREIGN KEY", "FROM", "FULL OUTER JOIN", "GROUP BY", "HAVING", "IN", "INDEX", "INNER JOIN", "INSERT INTO", "INSERT INTO SELECT",
        "IS NULL", "IS NOT NULL", "JOIN", "LEFT JOIN", "LIKE", "LIMIT", "NOT", "NOT NULL", "OR", "ORDER BY", "OUTER JOIN", "PRIMARY KEY", "PROCEDURE", "RIGHT JOIN",
        "ROWNUM", "SELECT", "SELECT DISTINCT", "SELECT INTO", "SELECT TOP", "SET", "TABLE", "TOP", "TRUNCATE TABLE", "UNION", "UNION ALL", "UNIQUE", "UPDATE", "VALUES",
        "VIEW", "WHERE", "VAR", "VAL", "TEXTVIEW", "*", "<", ">", "/", "|", "(", ")")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null) {
            goDashboard()
        }
        textViewExistingUser.setOnClickListener() {
            goLogin()
            finish()
        }

        buttonLogin.setOnClickListener{
            buttonLogin.isEnabled = false
            if(editTextEmail.text.toString().isEmpty() || editTextPassword.text.toString().isEmpty()) {
                Toast.makeText(this, "Email/password cannot be empty, please follow requirements.", Toast.LENGTH_SHORT).show()
                buttonLogin.isEnabled = true
                return@setOnClickListener
            }

            val email = editTextEmail.text.toString().uppercase()
            val password = editTextPassword.text.toString().uppercase()
            for (word in bannedWords) {
                if(email.contains(word) || password.contains(word)){
                    Toast.makeText(this, "Oops, this email/password cannot be used, please try again!", Toast.LENGTH_SHORT).show()
                    buttonLogin.isEnabled = true
                    return@setOnClickListener
                }
            }

            auth.createUserWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString()).addOnCompleteListener{ task ->
                buttonLogin.isEnabled = true
                if(task.isSuccessful) {
                    goDashboard()
                    finish()
                }
                else {
                    Toast.makeText(this, "Sign up failed: ${task.exception}", Toast.LENGTH_SHORT).show()
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
        finish()
    }

}