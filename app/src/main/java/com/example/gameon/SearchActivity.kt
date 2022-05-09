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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
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




}