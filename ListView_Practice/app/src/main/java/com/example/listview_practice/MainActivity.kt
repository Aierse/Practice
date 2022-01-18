package com.example.listview_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listview_practice.databinding.ActivityMainBinding
import com.example.listview_practice.datas.Room
import com.mwsniper.review_listview.adapters.RoomAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val mRoomList = ArrayList<Room>()
    lateinit var mRoomAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mRoomList.add(Room(8000, 30, "서울시 마포구", 1, "마포구 1층 방입니다."))
        mRoomList.add(Room(10000, 50, "서울시 은평구", 10, "은평구 10층 방입니다."))
        mRoomList.add(Room(3000, 80, "서울시 영등포구", 5, "영등포구 5층 방입니다."))
        mRoomList.add(Room(5000, 15, "서울시 종로구", 4, "종로구 4층 방입니다."))
        mRoomList.add(Room(9000, 20, "서울시 강서구", 8, "강서구 8층 방입니다."))
        mRoomList.add(Room(12000, 40, "서울시 강북구", 17, "강북구 17층 방입니다."))

        mRoomAdapter = RoomAdapter(this, R.layout.room_list_item, mRoomList)

        binding.roomListView.adapter = mRoomAdapter
    }
}