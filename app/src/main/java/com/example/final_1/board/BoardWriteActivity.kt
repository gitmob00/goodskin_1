package com.example.final_1.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.final_1.R
import com.example.final_1.utils.FBAuth
import com.example.final_1.utils.FBRef
import com.example.final_1.databinding.ActivityBoardwriteBinding
import com.example.final_1.sign.SignupModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class BoardwriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardwriteBinding
    private val TAG = BoardwriteActivity::class.java.simpleName
    private var imageUrl: Uri? = null
    private var isUploadImage= false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_boardwrite)

        binding.writebtn.setOnClickListener {

            val title = binding.title.text.toString()
            val content = binding.content.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()
            val key = FBRef.boardRef.push().key.toString()
            // 이미지 URL이 없는 경우 빈 문자열로 설정
            val imageUrlToSave = if (isUploadImage) imageUrl.toString() else ""

            if (uid.isNotEmpty()) {
                FBAuth.getNickname { nickname ->
                    // 게시물 업로드
                    FBRef.boardRef
                        .child(key)
                        .setValue(BoardModel(title, content, uid, time, imageUrlToSave))
                    Toast.makeText(this, "게시물 업로드 완료", Toast.LENGTH_SHORT).show()

                    // 설정 데이터 업데이트
                    FBRef.settingRef.child(uid).setValue(SignupModel(nickname))

                    if (isUploadImage == true) {
                        uploadImage(key)
                    }
                    finish()
                }
            }else{
                Toast.makeText(this, "UID를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
            }

            //
//            FBRef.boardRef
//                .child(key)
//                .setValue(BoardModel(title, content, uid, time, imageUrlToSave))
//            Toast.makeText(this, "게시물 업로드 완료", Toast.LENGTH_SHORT).show()
//
//            FBRef.settingRef
//                .child(uid)
//                .setValue(SignupModel(nickname))
//
//            if (isUploadImage == true) {
//                uploadImage(key)
//            }
//            finish()

        }
        
        binding.image.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,100)
            isUploadImage = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode,data)
        if(resultCode == RESULT_OK && requestCode == 100){
            binding.image.setImageURI(data?.data)
        }
    }

    private fun uploadImage(key:String){

        // Get the data from an ImageView as bytes
        val storage = Firebase.storage
        val storageRef = storage.reference.child("$key.png")
        //val mountainsRef = storageRef.child(key+".png")
        val imageView = binding.image

        imageView.isDrawingCacheEnabled = true // 캐시 활성화
        //imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = storageRef.putBytes(data)
        uploadTask.addOnSuccessListener {
            // 이미지 업로드 성공 시 URL 가져오기
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                // 이미지 URL을 데이터베이스에 저장
                FBRef.boardRef.child(key).child("imageUrl").setValue(uri.toString())
            }.addOnFailureListener {
                // 이미지 URL을 가져오는 데 실패했을 때
                Toast.makeText(this, "Failed to retrieve image URL", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            // 이미지 업로드 실패 시
            Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show()
        }
    }
}

