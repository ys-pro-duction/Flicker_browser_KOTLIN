package com.ys_production.flickerbrowser

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerClickListener(
    context: Context, private val listner: Clickevent,
    private val recyclerView: RecyclerView
) : RecyclerView.OnItemTouchListener {
    interface Clickevent{
        fun recyclerSingleClick(position: Int)
        fun recyclerLongClick(position: Int)
    }
    private val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.OnGestureListener{
        override fun onDown(e: MotionEvent): Boolean {
            Log.d(TAG, "onDown: start")
            return false }
        override fun onShowPress(e: MotionEvent) {
            Log.d(TAG, "onShowPress: start")}

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Log.d(TAG, "onSingleTapUp: start")
            val child = recyclerView.findChildViewUnder(e.x,e.y)
            return if (child != null) {
                listner.recyclerSingleClick(recyclerView.getChildAdapterPosition(child))
                true
            }else{
                false
            }
        }

        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float,
        ): Boolean {
            Log.d(TAG, "onScroll: start")
            return false }

        override fun onLongPress(e: MotionEvent) {
            Log.d(TAG, "onLongPress: start")
            val child = recyclerView.findChildViewUnder(e.x,e.y)
            if (child != null) {
                listner.recyclerLongClick(recyclerView.getChildAdapterPosition(child))
            }
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float,
        ): Boolean {
            Log.d(TAG, "onFling: start ")
            return false }

    })
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.d(TAG, "onInterceptTouchEvent: start")
        return gestureDetector.onTouchEvent(e)
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        Log.d(TAG, "onTouchEvent: start")
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        Log.d(TAG, "onRequestDisallowInterceptTouchEvent: $disallowIntercept")
    }

    companion object {
        private const val TAG = "RecyclerClickListener"
    }
}
