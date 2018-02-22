@file:JvmName("AdHelper")

package com.varunbarad.attendancetracker.mainattendance

import android.content.Context
import android.widget.FrameLayout
import com.inmobi.ads.InMobiBanner
import com.inmobi.sdk.InMobiSdk
import com.varunbarad.attendancetracker.BuildConfig
import com.varunbarad.attendancetracker.databinding.ActivityMainAttendanceBinding

/**
 * Creator: Varun Barad
 * Date: 18-02-2018
 * Project: AttendanceTracker
 */
fun initializeSdk(context: Context): Unit {
    InMobiSdk.init(context, BuildConfig.InMobiAccountId)
}

fun loadBannerAd(dataBinding: ActivityMainAttendanceBinding): Unit {
    initializeSdk(dataBinding.root.context)

    val bannerAd = InMobiBanner(
            dataBinding.root.context,
            BuildConfig.InMobiPlacementIdMainAttendance.toLong()
    ).apply {
        this.setRefreshInterval(60)
    }

    val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
    )

    dataBinding
            .adViewContainer
            .addView(bannerAd, layoutParams)

    bannerAd.load(dataBinding.root.context)
}