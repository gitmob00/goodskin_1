package com.example.final_1.utils

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.final_1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.SimpleTimeZone

class FBAuth : AppCompatActivity() {

    companion object {

        private lateinit var auth : FirebaseAuth

        fun getUid() : String {

            auth = FirebaseAuth.getInstance()

            return auth.currentUser?.uid.toString()

        }

        fun getTime() : String {

            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyy. MM.dd. HH:mm:ss", Locale.KOREA).format(currentDateTime)

            return dateFormat

        }


        fun getNickname(callback: (String) -> Unit) {
            val uid = getUid()
            if (uid.isNotEmpty()) {
                val userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid)
                userRef.child("nickname").get().addOnSuccessListener { snapshot ->
                    val nickname = snapshot.value as? String ?: ""
                    callback(nickname)
                }.addOnFailureListener {
                    callback("")
                }
            } else {
                callback("")
            }
        }


    }
}