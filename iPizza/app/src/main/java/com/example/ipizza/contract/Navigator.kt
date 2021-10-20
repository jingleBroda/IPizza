package com.example.ipizza.contract

import androidx.fragment.app.Fragment

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {
    fun goBack()
    fun showNewScreen(f:Fragment)
    fun replaceMainMenu(f:Fragment, tagShowFragment:Boolean)
}