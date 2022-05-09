package com.example.gameon

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private val FILE_NAME = "TaskList"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }


    override fun onResume() {
        super.onResume()
        loadData()
    }



    fun onSearchClicked(view: View) {
        editTextSearch.hideKeyboard()
        val searchTerm = editTextSearch.text.toString()
        if(searchTerm.isEmpty()) {
            showSearchAlert()
            return
        }
        val myIntent = Intent()
        myIntent.putExtra("Search", searchTerm)
        saveData()
        setResult(Activity.RESULT_OK, myIntent)
        finish()
    }

    private fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun showSearchAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Search term missing")
        builder.setMessage("search term cannot be empty. Please enter a search term")
        builder.setIcon(android.R.drawable.ic_delete)
        builder.setNegativeButton("Okay"){ dialog, which -> }
        val dialog = builder.create()
        dialog.show()
    }


    private fun saveData() {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", editTextSearch.text.toString())
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val savedName = sharedPreferences.getString("name", "")
        editTextSearch.setText(savedName)
    }

}