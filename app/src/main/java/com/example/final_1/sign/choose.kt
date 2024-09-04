package com.example.final_1.sign

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.final_1.MainActivity
import com.example.final_1.R
import com.example.final_1.databinding.ActivityChooseBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class choose : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityChooseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_choose)
        auth = Firebase.auth

        binding = DataBindingUtil.setContentView(this,R.layout.activity_choose)

        binding.button3.setOnClickListener{
            val intent = Intent(this, Signin::class.java)
            startActivity(intent)
        }

        binding.button4.setOnClickListener{
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        binding.textView.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"익명 로그인 성공", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)

                    } else {
                        Toast.makeText(this,"익명 로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}