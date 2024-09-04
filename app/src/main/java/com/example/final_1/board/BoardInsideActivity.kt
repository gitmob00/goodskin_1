package com.example.final_1.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.final_1.R
import com.bumptech.glide.Glide
import com.example.final_1.databinding.ActivityBoardInsideBinding
import com.example.final_1.sign.SignupModel
import com.example.final_1.utils.FBAuth
import com.example.final_1.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BoardInsideActivity : AppCompatActivity() {

    private val TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var binding : ActivityBoardInsideBinding
    private lateinit var key:String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_board_inside)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_board_inside)

        binding.boardsettingicon.setOnClickListener{
            showDialog()
        }

        // 첫번째 방법
//        val title = intent.getStringArrayExtra("title").toString()
//        val content = intent.getStringArrayExtra("content").toString()
//        val time = intent.getStringArrayExtra("time").toString()
//        val uid = intent.getStringArrayExtra("uid").toString()
//        val image = intent.getStringArrayExtra("image").toString()
//
//        binding.titleArea.text = title
//        binding.contentArea.text = content
//        binding.timeArea.text = time
//        binding.uidArea.text = uid

        // 두번째 방법
        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)
        getUserData(key)
    }

    private fun showDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(androidx.core.R.layout.custom_dialog,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            //.setTitle("게시글 수정/삭제")

        //mBuilder.show()
        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener{
            Toast.makeText(this,"수정하기",Toast.LENGTH_LONG).show()

            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)
        }
        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener{
            FBRef.boardRef.child(key).removeValue()
            Toast.makeText(this,"삭제 완료",Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun getImageData(key: String) {

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key+".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.image

        storageReference.downloadUrl.addOnCompleteListener(){ task ->
            if(task.isSuccessful){
                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)
            }else{
                Log.e(TAG, "Image download failed", task.exception)
            }
        }
    }

    private fun getBoardData(key:String) {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)
                    Log.d(TAG,dataModel!!.title)

                    // null 값 체크 생성 추가해야 됨
                    binding.titleArea.text = dataModel!!.title
                    binding.contentArea.text = dataModel!!.content
                    binding.timeArea.text = dataModel!!.time
                    //binding.imageArea.text = dataModel!!.image

                    val myUid = FBAuth.getUid()
                    val writerUid = dataModel.uid

                    if(myUid.equals(writerUid)){
                        //내가 쓴 글
                        binding.boardsettingicon.isVisible = true
                    }else{
                        //남이 쓴 글
                    }
                    getUserData(dataModel.uid)
                } catch (e: Exception) {
                    Log.d(TAG,"삭제완료1")
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "lostPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }

    fun getUserData(uid: String) {
        //val userData = SignupModel(uid = uid, nickname = nickzname, email = email)
        //FBRef.settingRef.child(uid).setValue(userData)
        val settingpostListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val dataModel2 = dataSnapshot.getValue(SignupModel::class.java)

                    if (dataModel2 != null) {
                        binding.nickArea.text = dataModel2.nickname
                        Log.d(TAG, "Nickname: ${dataModel2.nickname}")
                    } else {
                        Log.w(TAG, "Setting data is null for UID: $uid")
                    }
                }catch(e: Exception){
                    Log.d(TAG,"삭제완료2")
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadSettingData:onCancelled", databaseError.toException())
            }
        }
        FBRef.settingRef.child(uid).addValueEventListener(settingpostListener)
    }
}


