package com.sriyank.javatokotlindemo.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import android.os.Bundle
import com.sriyank.javatokotlindemo.R
import android.content.SharedPreferences
import android.content.Intent
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.sriyank.javatokotlindemo.activities.DisplayActivity
import com.sriyank.javatokotlindemo.activities.MainActivity
import com.sriyank.javatokotlindemo.app.Constants
import com.sriyank.javatokotlindemo.app.isNotEmpty

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG : String = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


    }

    fun saveName(view: View) {
        if(etName.isNotEmpty(inputLayoutName)){
            val personName = etName.text.toString()
            val sharedPrerenced = getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            val editor = sharedPrerenced.edit()
            editor.putString(Constants.KEY_PERSON_NAME, personName)
            editor.apply()
        }

    }
    fun listRepositories(view: View) {
        if(etRepoName.isNotEmpty(inputLayoutRepoName)){
            val queryRepo = etRepoName.text.toString()
            val repoLanguage = etLanguage.text.toString()

            val intent = Intent(this@MainActivity,DisplayActivity::class.java)
            intent.putExtra(Constants.KEY_QUERY_TYPE,Constants.SEARCH_BY_REPO)
            intent.putExtra(Constants.KEY_REPO_SEARCH,queryRepo)
            intent.putExtra(Constants.KEY_LANGUAGE,repoLanguage)

            startActivity(intent);
        }

    }
    fun listUserRepositories(view: View) {

        if(etGithubUser.isNotEmpty(inputLayoutGithubUser)){
            val gitHubUser = etGithubUser.text.toString()

            val intent = Intent(this@MainActivity,DisplayActivity::class.java)
            intent.putExtra(Constants.KEY_QUERY_TYPE,Constants.SEARCH_BY_USER)
            intent.putExtra(Constants.KEY_GITHUB_USER,gitHubUser)


            startActivity(intent);
        }

    }




}