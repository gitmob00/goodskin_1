package com.example.final_1.contentsList

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_1.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ContentsListActivity : AppCompatActivity() {

    lateinit var myRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_list)

        val items = ArrayList<ContentModel>()
        val rvAdapter = ContentRVAdapter(baseContext,items)

        val database = Firebase.database
        val category = intent.getStringExtra("category")

        //val myRef = database.getReference("contents")

        if(category == "ca1"){
            myRef = database.getReference("contents1")
        }else if(category == "ca2"){
            myRef = database.getReference("contents2")
        }

        val postListener = object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //val post = dataSnapshot.getValue<Post>()
                for(dataModel in dataSnapshot.children){
                    val item = dataModel.getValue(ContentModel::class.java)
                    items.add(item!!)
                }
                rvAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("ContentsListActivity", "loadPost:onCancelled",databaseError.toException())
            }
        }

        myRef.addValueEventListener(postListener)

//        myRef.push().setValue(
//            ContentModel("title1","image1","url1")
//        )

        val rv : RecyclerView = findViewById(R.id.rv)

//        items.add(ContentModel("title1","imageUrl1","url1"))
//        items.add(ContentModel("title2","imageUrl2","url2"))
//        items.add(ContentModel("title3","imageUrl3","url3"))
        //컨텐츠 리스트 만들기 Glide 1 강의에서 이미지 추가


        rv.adapter = rvAdapter

        rv.layoutManager=GridLayoutManager(this,2)

        rvAdapter.itemClick = object : ContentRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                Toast.makeText(baseContext,items[position].title,Toast.LENGTH_LONG).show()

                val intent = Intent(this@ContentsListActivity,ContentShowActivity::class.java)
                intent.putExtra("url",items[position].webUrl)
                startActivity(intent)
            }
        }
        val myRef_2 = database.getReference("contents2")

    }
}