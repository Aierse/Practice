//package com.example.enhancedviewer
//
//
//import androidx.annotation.NonNull
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//
//class VisiblePositionChangeListener : RecyclerView.OnScrollListener() {
//    interface OnChangeListener {
//        fun onFirstVisiblePositionChanged(position: Int)
//        fun onLastVisiblePositionChanged(position: Int)
//        fun onFirstInvisiblePositionChanged(position: Int)
//        fun onLastInvisiblePositionChanged(position: Int)
//    }
//
//    private var firstVisiblePosition: Int = 0
//    private var lastVisiblePosition: Int = 0
//    private lateinit var listener: OnChangeListener
//    private lateinit var layoutManager: LinearLayoutManager
//
//    fun VisiblePositionChangeListener (linearLayoutManager: LinearLayoutManager, listener: OnChangeListener) {
//        this.listener = listener
//        this.firstVisiblePosition = RecyclerView.NO_POSITION
//        this.lastVisiblePosition = RecyclerView.NO_POSITION; this.layoutManager = linearLayoutManager
//    }
//    fun VisiblePositionChangeListener(linearLayoutManager: LinearLayoutManager, listener: OnChangeListener) {
//        this.listener = listener
//        this.firstVisiblePosition = RecyclerView.NO_POSITION;
//        this.lastVisiblePosition = RecyclerView.NO_POSITION;
//        this.layoutManager = linearLayoutManager;
//    }
//
//    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//        super.onScrolled(recyclerView, dx, dy)
//        var firstPosition: Int = layoutManager.findFirstVisibleItemPosition()
//        var lastPosition: Int = layoutManager.findLastVisibleItemPosition ()
//
//        if (firstVisiblePosition == RecyclerView.NO_POSITION || lastVisiblePosition == RecyclerView.NO_POSITION) {
//            firstVisiblePosition = firstPosition
//            lastVisiblePosition = lastPosition
//            return;
//        }
//
//        if (firstPosition < firstVisiblePosition) {
//            if (firstVisiblePosition - firstPosition > 1) {
//                for (i in 1..firstVisiblePosition - firstPosition) {
//                    listener.onFirstInvisiblePositionChanged(firstVisiblePosition - 1)
//                }
//            }
//            else {
//                listener.onFirstVisiblePositionChanged(firstPosition)
//            }
//            firstVisiblePosition = firstPosition
//        }
//
//        else if (firstPosition > firstVisiblePosition) {
//            if (firstPosition - firstVisiblePosition > 1) {
//                for (i in firstPosition - firstVisiblePosition downTo 1) {
//                    listener.onFirstInvisiblePositionChanged(firstPosition - 1)
//                }
//            }
//            else {
//                listener.onFirstInvisiblePositionChanged(firstVisiblePosition);
//            }
//            firstVisiblePosition = firstPosition;
//        }
//
//        if (lastPosition > lastVisiblePosition) {
//            if (lastPosition - lastVisiblePosition > 1) {
//                for (int i = 1; i < lastPosition - lastVisiblePosition + 1; i++) {
//                    listener.onLastVisiblePositionChanged(lastVisiblePosition + i);
//                }
//            }
//            else {
//                listener.onLastVisiblePositionChanged(lastPosition);
//            }
//            lastVisiblePosition = lastPosition
//        }
//        else if (lastPosition < lastVisiblePosition) {
//            if (lastVisiblePosition - lastPosition > 1) {
//                for (int i = 0; i < lastVisiblePosition - lastPosition; i++) {
//                    listener.onLastInvisiblePositionChanged(lastVisiblePosition - i);
//                }
//            }
//            else {
//                listener.onLastInvisiblePositionChanged(lastVisiblePosition);
//            }
//            lastVisiblePosition = lastPosition;
//        }
//    }
//}
