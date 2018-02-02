package com.example.sky.ui.fragment

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.view.View
import android.widget.ImageView
import com.example.sky.R
import com.example.sky.helper.FilePersistenceHelper
import com.example.sky.helper.TransitionHelper
import com.example.sky.model.Photo
import com.example.sky.network.getStackTrace
import com.example.sky.ui.activity.PhotoDetailActivity
import timber.log.Timber
import android.support.design.widget.Snackbar


abstract class BaseFragment : Fragment(){

    private var loadingView: View? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun startDetailActivityWithTransition(activity: Activity, firstViewToAnimate: View, secondViewToAnimate: View, photo: Photo) {
        val animationBundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                *TransitionHelper.createSafeTransitionParticipants(activity,
                        false,
                        Pair(firstViewToAnimate, activity.getString(R.string.transition_recents_photo_image)),
                        Pair(secondViewToAnimate, activity.getString(R.string.transition_recents_photo_id))
                ))
                .toBundle()
        try {
            FilePersistenceHelper.writeBitmapToFile(activity, ((firstViewToAnimate as ImageView).drawable as BitmapDrawable).bitmap)
        } catch (e: TypeCastException) {
            Timber.d(getStackTrace(e)) // This happens when the image hasn't loaded yet, not saving is enough
        }
        val startIntent = PhotoDetailActivity.getStartIntent(activity, photo)
        startActivity(startIntent, animationBundle)
    }

    protected fun setUpLoadingAndError(loadingView: View, rootView: CoordinatorLayout){
        this.loadingView = loadingView
        coordinatorLayout = rootView
    }

    protected fun showLoading(show: Boolean){
        loadingView?.visibility = if (show) View.VISIBLE else View.GONE
    }

    protected fun showError(message: String){
        coordinatorLayout?.let {
            val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)
            snackbar.setAction(getString(R.string.dismiss)) { snackbar.dismiss() }
            snackbar.show()
        }
    }

}