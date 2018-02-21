@file:JvmName("AdHelper")

package com.varunbarad.attendancetracker.mainattendance

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.varunbarad.attendancetracker.BuildConfig
import com.varunbarad.attendancetracker.databinding.ActivityMainAttendanceBinding

/**
 * Creator: Varun Barad
 * Date: 18-02-2018
 * Project: AttendanceTracker
 */
fun loadBannerAd(dataBinding: ActivityMainAttendanceBinding): Unit {
    MobileAds.initialize(dataBinding.root.context, BuildConfig.AdMobAppId)

    val adRequest = AdRequest.Builder()
            .build()

    dataBinding
            .adViewBanner
            .loadAd(adRequest)
}