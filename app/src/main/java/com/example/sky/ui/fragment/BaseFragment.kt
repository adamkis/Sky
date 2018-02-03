package com.example.sky.ui.fragment

import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import com.example.sky.R


abstract class BaseFragment : Fragment(){

    private var loadingView: View? = null
    private var coordinatorLayout: CoordinatorLayout? = null

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