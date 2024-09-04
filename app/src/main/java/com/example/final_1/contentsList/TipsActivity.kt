package com.example.final_1.contentsList

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.final_1.R
import com.example.final_1.databinding.ActivityTipsBinding

class TipsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTipsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tips)

        binding.ca1.setOnClickListener{
            val intent = Intent(this, ContentsListActivity::class.java)
            intent.putExtra("category","ca1")
            startActivity(intent)
        }

        binding.ca2.setOnClickListener{
            val intent = Intent(this, ContentsListActivity::class.java)
            intent.putExtra("category","ca2")
            startActivity(intent)
        }
    }
}