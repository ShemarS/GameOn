package com.example.gameon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val TAG = "MAN WHAT"

    private val bannedWords = listOf("ADD", "ADD CONSTRAINT", "ALL", "ALTER ", "ALTER COLUMN", "ALTER TABLE", "AND", "ANY", "AS", "ASC", "BACKUP DATABASE", "BETWEEN",
    "CASE", "CHECK", "COLUMN", "CONSTRAINT", "CREATE", "CREATE DATABASE", "CREATE INDEX", "CREATE OR REPLACE VIEW", "CREATE TABLE", "CREATE PROCEDURE", "CREATE UNIQUE INDEX",
        "CREATE VIEW", "DATABASE", "DEFAULT", "DELETE", "DELETE", "DESC", "DISTINCT", "DROP", "DROP COLUMN", "DROP CONSTRAINT", "DROP DATABASE", "DROP DEFAULT", "DROP INDEX",
        "DROP TABLE", "DROP VIEW", "EXEC", "FOREIGN KEY", "FROM", "FULL OUTER JOIN", "GROUP BY", "HAVING", "IN", "INDEX", "INNER JOIN", "INSERT INTO", "INSERT INTO SELECT",
        "IS NULL", "IS NOT NULL", "JOIN", "LEFT JOIN", "LIKE", "LIMIT", "NOT", "NOT NULL", "OR", "ORDER BY", "OUTER JOIN", "PRIMARY KEY", "PROCEDURE", "RIGHT JOIN",
        "ROWNUM", "SELECT", "SELECT DISTINCT", "SELECT INTO", "SELECT TOP", "SET", "TABLE", "TOP", "TRUNCATE TABLE", "UNION", "UNION ALL", "UNIQUE", "UPDATE", "VALUES",
        "VIEW", "WHERE", "VAR", "VAL", "TEXTVIEW")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null) {
            goDashboard()
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

            auth.signInWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString()).addOnCompleteListener{ task ->
                buttonLogin.isEnabled = true
                if(task.isSuccessful) {
                    goDashboard()
                }
                else {
                    Toast.makeText(this, "Login failed: ${task.exception}", Toast.LENGTH_SHORT).show()

                }
            }
        }

    }

    private fun goDashboard() {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
        finish()
    }

}