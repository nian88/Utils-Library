package com.aryanmo.utils


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aryanmo.utils.utils.FLAG
import com.aryanmo.utils.utils.log.logE

abstract class AdvanceBaseFragment : BaseFragment(){

    private lateinit var view: View

    fun AdvanceBaseFragment(s: String) {
        TAG = s
    }

    override fun setMenuVisibility(visible: Boolean) {
        super.setMenuVisibility(visible)
        if (visible) {
            visibleUI()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userVisibleHint = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        view = inflater.inflate(inflate(), container, false)

        customizeUI()

        return view
    }

    override fun getView(): View {
        return view
    }

    protected open fun customizeUI(){
        if (logLifeCycle)
            logE("$TAG : customizeUI()", "${FLAG}-LifeCycle")
    }

    protected open fun visibleUI(){
        if (logLifeCycle)
            logE("$TAG : visibleUI()", "${FLAG}-LifeCycle")
    }

    protected abstract fun inflate(): Int
}
