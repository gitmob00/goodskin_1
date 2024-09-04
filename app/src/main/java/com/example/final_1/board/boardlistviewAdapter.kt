package com.example.final_1.board

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.final_1.R
import com.bumptech.glide.Glide
import com.example.final_1.utils.FBAuth

class boardlistviewAdapter(val boardList : MutableList<BoardModel>) : BaseAdapter() {

    override fun getCount(): Int {
        return boardList.size
    }

    override fun getItem(position: Int): Any {
        return boardList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        //if(view == null){
            view = LayoutInflater.from(parent?.context).inflate(R.layout.board_listview,parent,false)
        //}

        val itemView = view?.findViewById<LinearLayout>(R.id.itemView)

        val title = view?.findViewById<TextView>(R.id.title)
        val content = view?.findViewById<TextView>(R.id.content)
        val time = view?.findViewById<TextView>(R.id.time)
        val image = view?.findViewById<ImageView>(R.id.image)

        if(boardList[position].uid.equals(FBAuth.getUid())){
            itemView?.setBackgroundColor(Color.parseColor("#999999"))
        }

        title!!.text = boardList[position].title
        content!!.text = boardList[position].content
        time!!.text = boardList[position].time
        //그림 추가 코드
        val boardItem = boardList[position]

        //그림 추가코드
        // Set image using Glide or another image loading library
//        image?.let{
//            Glide.with(view?.context!!)
//                .load(boardItem.imageUrl) // Load image from URL
//                .into(it)
//        }

        if (boardItem.imageUrl != null && boardItem.imageUrl.isNotEmpty()) {
            image?.let {
                Glide.with(view!!.context)
                    .load(boardItem.imageUrl)
                    .placeholder(R.drawable.baseline_invert_colors_24_b) // 기본 이미지
                    .error(R.drawable.ic_launcher_foreground) //오류이미지
                    .into(image!!)
            }
        }else{
            image?.setImageResource(R.drawable.baseline_invert_colors_24_b) // 기본 이미지 설정
        }

//        Glide.with(view!!.context) // view는 null이 아님을 보장
//            .load(boardItem.imageUrl)
//            .into(image!!)

        return view
    }
}