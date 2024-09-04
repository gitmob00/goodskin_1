package com.example.final_1.navi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.final_1.R
import com.example.final_1.board.BoardInsideActivity
import com.example.final_1.board.BoardModel
import com.example.final_1.board.BoardwriteActivity
import com.example.final_1.board.boardlistviewAdapter
import com.example.final_1.databinding.FragmentNa3Binding
import com.example.final_1.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class na3 : Fragment() {

    private lateinit var binding: FragmentNa3Binding

    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>()

    private var TAG = na3::class.java.simpleName

    private lateinit var boardRVAdapter : boardlistviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_na3, container, false)

        boardRVAdapter = boardlistviewAdapter(boardDataList)
        binding.boardlist.adapter = boardRVAdapter

        getFBBoardData()

        binding.boardlist.setOnItemClickListener { parent, view,position, id ->

            // 첫번째 방법 : listview에 있는 데이터 title,content,time 다 다른 액티비티로 전달해줘서 만들기
//            val intent = Intent(context, BoardInsideActivity::class.java)
//            intent.putExtra("title",boardDataList[position].title)
//            intent.putExtra("content",boardDataList[position].content)
//            intent.putExtra("time",boardDataList[position].time)
//            intent.putExtra("uid",boardDataList[position].uid)
//            intent.putExtra("image",boardDataList[position].imageUrl)
//            startActivity(intent)

            // 두번째 방법 : Firebase에 있는 board에 대한 uid를 기반으로 데이터를 가져오기

            val intent = Intent(context, BoardInsideActivity::class.java)
            intent.putExtra("key",boardKeyList[position])
            startActivity(intent)

        }

        binding.writebtn.setOnClickListener{
            val intent= Intent(context, BoardwriteActivity::class.java)
            startActivity(intent)
        }

        binding.navi1.setOnClickListener{
            it.findNavController().navigate(R.id.action_na3_to_na1)
        }

        binding.navi2.setOnClickListener{
            it.findNavController().navigate(R.id.action_na3_to_na2)
        }

        binding.navi4.setOnClickListener{
            it.findNavController().navigate(R.id.action_na3_to_na4)
        }

        binding.navi5.setOnClickListener{
            it.findNavController().navigate(R.id.action_na3_to_na5)
        }

        return binding.root
    }


    private fun getFBBoardData(){

        val postListener = object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear()

                for(dataModel in dataSnapshot.children){

                    Log.d(TAG,dataModel.toString())

                    val item = dataModel.getValue(BoardModel::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())
                }
                boardKeyList.reverse()
                boardDataList.reverse()
                // 역순(최신순)으로 글 나열하기

                boardRVAdapter.notifyDataSetChanged()

                Log.d(TAG, boardDataList.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "lostPost:onCancelled",databaseError.toException())
            }
        }

        FBRef.boardRef.addValueEventListener(postListener)
        FBRef.settingRef.addValueEventListener(postListener)
    }
}