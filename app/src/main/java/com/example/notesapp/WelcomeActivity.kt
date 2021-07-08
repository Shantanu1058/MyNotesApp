package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_layout)
        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)

    }
}