package com.zivapp.newsapplication.utils

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.MaterialToolbar
import com.zivapp.newsapplication.R

fun Fragment.getCompatColor(colorId: Int) = ContextCompat.getColor(requireContext(), colorId)
fun Fragment.getCompatDrawable(drawId: Int) = ContextCompat.getDrawable(requireContext(), drawId)
fun Fragment.getCompatColorStateList(colorId: Int) =
    ContextCompat.getColorStateList(requireContext(), colorId)

fun View.getCompatColor(colorId: Int) = ContextCompat.getColor(context, colorId)
fun View.getCompatDrawable(drawId: Int) = ContextCompat.getDrawable(context, drawId)
fun View.getCompatColorStateList(colorId: Int) = ContextCompat.getColorStateList(context, colorId)

fun SwipeRefreshLayout.setupSwipeRefresh(action: () -> Unit) = this.apply {
    setColorSchemeColors(getCompatColor(R.color.purple_200))
    setOnRefreshListener {
        action()
    }
}

fun String.shouldSeparate(afterCreatedDateAt: String): Boolean {
    val beforeCreatedDateAt: String = this
    val dateBefore = DateUtils.formatGMTToUTCDateStr(beforeCreatedDateAt)
    val dateAfter = DateUtils.formatGMTToUTCDateStr(afterCreatedDateAt)

    return dateBefore != dateAfter
}

fun MaterialToolbar.setupToolbar(fragment: Fragment? = null) = this.apply {
    navigationIcon = ContextCompat.getDrawable(this.context, R.drawable.ic_arrow_back)
    setNavigationIconTint(ContextCompat.getColor(this.context, R.color.black))
    contentInsetStartWithNavigation = 0
    setNavigationOnClickListener {
        fragment?.run {
            this.findNavController().navigateUp()
            return@setNavigationOnClickListener
        }
        findNavController().navigateUp()
        return@setNavigationOnClickListener
    }
}