package com.example.sky.helper

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.support.annotation.IdRes
import android.support.v4.util.Pair
import android.view.View


object TransitionHelper{

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun createSafeTransitionParticipants(activity: Activity,
                                         includeStatusBar: Boolean,
                                         vararg others: Pair<View, String>
    ): Array<Pair<View, String>> {
        // Avoid system UI glitches as described here:
        // https://plus.google.com/+AlexLockwood/posts/RPtwZ5nNebb

        return ArrayList<Pair<View, String>>(3).apply {
            if (includeStatusBar) {
                addViewById(activity, android.R.id.statusBarBackground, this)
            }
            addViewById(activity, android.R.id.navigationBarBackground, this)
            addAll(others.toList())

        }.toTypedArray()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addViewById(activity: Activity,
                            @IdRes viewId: Int,
                            participants: ArrayList<Pair<View, String>>) {
        val view = activity.window.decorView.findViewById<View>(viewId)
        view?.transitionName?.let { participants.add(Pair(view, it)) }
    }

}