package com.example.final_1

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.final_1.sign.choose
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.util.logging.Handler

class StartView : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_view)

        if(auth.currentUser?.uid ==null){
            Log.d("StartView","null")

            android.os.Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, choose::class.java))
                finish()
            }, 2000)


        }else{
            Log.d("StartView","not null")

            android.os.Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000)

        }


    }
}
