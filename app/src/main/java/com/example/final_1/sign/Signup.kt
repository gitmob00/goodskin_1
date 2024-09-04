package com.example.final_1.sign

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.final_1.MainActivity
import com.example.final_1.R
import com.example.final_1.databinding.ActivitySignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        binding=DataBindingUtil.setContentView(this,R.layout.activity_signup)

        binding.buttonSignup.setOnClickListener{

            var isGotoJoin = true

            val email = binding.emailEt.text.toString()
            val password1 = binding.passET.text.toString()
            val password2 = binding.confirmPassEt.text.toString()
            val nickname = binding.nicknameET.text.toString()

            if(email.isEmpty()){
                Toast.makeText(this," 이메일을 입력해주세요",Toast.LENGTH_SHORT).show()
                isGotoJoin = false
            }

            if(password1.isEmpty()){
                Toast.makeText(this," 비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
                isGotoJoin = false
            }

            if(password2.isEmpty()){
                Toast.makeText(this," 비밀번호 재입력을 입력해주세요",Toast.LENGTH_SHORT).show()
                isGotoJoin = false
            }

            if(password1.length < 6){
                Toast.makeText(this," 비밀번호를 6자리 이상 입력해주세요",Toast.LENGTH_SHORT).show()
                isGotoJoin = false
            }

            if(!password1.equals(password2)){
                Toast.makeText(this," 비밀번호를 똑같이 입력해주세요",Toast.LENGTH_SHORT).show()
                isGotoJoin = false
            }

            if(nickname.isEmpty()){
                Toast.makeText(this," 별명를 입력해주세요",Toast.LENGTH_SHORT).show()
                isGotoJoin = false
            }

            if(isGotoJoin){
                auth.createUserWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // 회원가입 성공
                            Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()

                            // 회원가입 후 추가 작업: 사용자 정보를 Firebase Realtime Database에 저장
                            val uid = auth.currentUser?.uid
                            if (uid != null) {
                                val userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid)
                                val userMap = HashMap<String, Any>()
                                userMap["id"] = email  // 이메일을 ID로 사용
                                userMap["password"] = password1  // 비밀번호 저장 (암호화 권장)
                                userMap["uid"] = uid
                                userMap["nickname"] = nickname

                                // 로그 추가
                                Log.d("Signup", "Saving user data: $userMap")

                                userRef.setValue(userMap).addOnCompleteListener { saveTask ->
                                    if (saveTask.isSuccessful) {
                                        // 사용자 정보 저장 성공
                                        Toast.makeText(this, "사용자 정보 저장 성공", Toast.LENGTH_SHORT).show()

                                        // 메인 액티비티로 이동
                                        val intent = Intent(this, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        startActivity(intent)
                                    } else {
                                        // 사용자 정보 저장 실패
                                        Log.e("Signup", "사용자 정보 저장 실패: ${saveTask.exception?.message}")
                                        Toast.makeText(this, "사용자 정보 저장 실패", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                // 현재 사용자 ID를 가져오는 데 실패한 경우
                                Toast.makeText(this, "사용자 정보를 저장할 수 없습니다", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // 회원가입 실패
                            Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        }
                            }
                        }
                    }
            binding.textView.setOnClickListener {
                val intent = Intent(this,Signin::class.java)
                startActivity(intent)
            }
    }
}


/*.addOnCompleteListener(this) { task ->
    if (task.isSuccessful) {
        Toast.makeText(this,"회원가입 성공",Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)

    } else {
        Toast.makeText(this,"회원가입 실패",Toast.LENGTH_SHORT).show()
    }
}*/

