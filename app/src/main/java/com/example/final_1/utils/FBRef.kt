package com.example.final_1.utils

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.final_1.R
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class FBRef : AppCompatActivity() {

    companion object{

        //private val database = Firebase.database

        private val database = FirebaseDatabase.getInstance()

        //n1
        val monthRef = database.getReference("month")

        //n2

        //n3
        val boardRef = database.getReference("board")

        //n4
        val cosmeticRef = database.getReference("cosmetic")

        //na5
        val settingRef = database.getReference("Users")

    }
}