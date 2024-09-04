package com.example.final_1.navi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.final_1.R
import com.example.final_1.board.BoardModel
import com.example.final_1.contentsList.TipsActivity
import com.example.final_1.databinding.FragmentNa5Binding
import com.example.final_1.sign.SignupModel
import com.example.final_1.sign.choose
import com.example.final_1.utils.FBRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class na5 : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentNa5Binding
    //private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        //database = FirebaseDatabase.getInstance().reference
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_na5, container, false)

        val currentUser = auth.currentUser
        currentUser?.let {
            val userId = it.uid
            Log.d("na5", "Current User ID: $userId")

            FBRef.settingRef.child(userId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val signup = dataSnapshot.getValue(SignupModel::class.java)

                    if (signup != null) {
                        Log.d("na5", "Nickname success: ${signup.nickname}")
                        binding.username.text = "${signup.nickname}님" // 닉네임 설정
                    } else {
                        Log.w("na5", "SignupModel is null - fail")
                        binding.username.text = "user님"
                    }
                    //binding.username.text = "${signup?.nickname ?: "user"}님" // 닉네임 설정
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // 읽기 실패 시 처리

                    Log.w("na5", "DatabaseError - fail: ${databaseError.toException()}")
                    binding.username.text = "user님"
                }
            })
        }

        binding.logout.setOnClickListener {
            auth.signOut() // 로그아웃 수행

            //Toast.makeText(this,"로그아웃",Toast.LENGTH_LONG).show()

            val intent = Intent(context, choose::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish() // 현재 액티비티 종료
        }

//        binding.bookmark.setOnClickListener {
//            val intent = Intent(requireContext(), TipsActivity::class.java)
//            startActivity(intent)
//        }

        binding.tips.setOnClickListener {
            val intent = Intent(requireContext(), TipsActivity::class.java)
            startActivity(intent)
        }

        binding.navi1.setOnClickListener{
            it.findNavController().navigate(R.id.action_na5_to_na1)
        }

        binding.navi2.setOnClickListener{
            it.findNavController().navigate(R.id.action_na5_to_na2)
        }

        binding.navi3.setOnClickListener{
            it.findNavController().navigate(R.id.action_na5_to_na3)
        }

        binding.navi4.setOnClickListener{
            it.findNavController().navigate(R.id.action_na5_to_na4)
        }

        return binding.root
    }

}