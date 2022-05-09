package com.example.gameon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_search.*

class LoginActivity : AppCompatActivity() {
    private val TAG = "MAN WHAT"

    //This part of the code was used for our CS492 Computer Security class to mitigate SQL/Code injection attacks and sanitize user input before processing
    //I am leaving it here just in case our CS492 professor wants to access the most recent changes to the overall application on Github

/*    private val bannedWords = listOf("ADD", "ADD CONSTRAINT", "ALL", "ALTER ", "ALTER COLUMN", "ALTER TABLE", "AND", "ANY", "ASC", "BACKUP DATABASE", "BETWEEN",
    "CASE", "CHECK", "COLUMN", "CONSTRAINT", "CREATE", "CREATE DATABASE", "CREATE INDEX", "CREATE OR REPLACE VIEW", "CREATE TABLE", "CREATE PROCEDURE", "CREATE UNIQUE INDEX",
        "CREATE VIEW", "DATABASE", "DEFAULT", "DELETE", "DELETE", "DESC", "DISTINCT", "DROP", "DROP COLUMN", "DROP CONSTRAINT", "DROP DATABASE", "DROP DEFAULT", "DROP INDEX",
        "DROP TABLE", "DROP VIEW", "EXEC", "FOREIGN KEY", "FROM", "FULL OUTER JOIN", "GROUP BY", "HAVING", "IN", "INDEX", "INNER JOIN", "INSERT INTO", "INSERT INTO SELECT",
        "IS NULL", "IS NOT NULL", "JOIN", "LEFT JOIN", "LIKE", "LIMIT", "NOT", "NOT NULL", "OR", "ORDER BY", "OUTER JOIN", "PRIMARY KEY", "PROCEDURE", "RIGHT JOIN",
        "ROWNUM", "SELECT", "SELECT DISTINCT", "SELECT INTO", "SELECT TOP", "SET", "TABLE", "TOP", "TRUNCATE TABLE", "UNION", "UNION ALL", "UNIQUE", "UPDATE", "VALUES",
        "VIEW", "WHERE", "VAR", "VAL", "TEXTVIEW")*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null) {
            goDashboard()
        }
        loadData()



        buttonLogin.setOnClickListener{
            buttonLogin.isEnabled = false
            if(editTextEmail.text.toString().isEmpty() || editTextPassword.text.toString().isEmpty()) {
                Toast.makeText(this, "Email/password cannot be empty, please follow requirements.", Toast.LENGTH_SHORT).show()
                buttonLogin.isEnabled = true
                return@setOnClickListener
            }


            //This part of the code was used for our CS492 Computer Security class to mitigate SQL/Code injection attacks and sanitize user input before processing
            //I am leaving it here just in case our CS492 professor wants to access the most recent changes to the overall application on Github

/*            val email = editTextEmail.text.toString().uppercase()
            val password = editTextPassword.text.toString().uppercase()
            for (word in bannedWords) {
                  if(email.contains(word) || password.contains(word)){
                      Toast.makeText(this, "Oops, this email/password cannot be used, please try again!", Toast.LENGTH_SHORT).show()
                      buttonLogin.isEnabled = true
                      return@setOnClickListener
                  }
            }*/

            auth.signInWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString()).addOnCompleteListener{ task ->
                buttonLogin.isEnabled = true
                if(task.isSuccessful) {
                    saveData()
                    goDashboard()
                }
                else {
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


    private fun goDashboard() {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
        finish()
    }

    private fun saveData() {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", editTextEmail.text.toString())
        editor.putString("password", editTextPassword.text.toString())
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "")
        val password = sharedPreferences.getString("password", "")
        editTextEmail.setText(email)
        editTextPassword.setText(password)
    }

}