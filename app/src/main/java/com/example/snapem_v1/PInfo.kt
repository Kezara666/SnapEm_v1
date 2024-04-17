package com.example.snapem_v1

import android.graphics.drawable.Drawable
import android.util.Log


class PInfo {
    var appname = ""
    var pname = ""
    var versionName = ""
    var versionCode = 0
    var icon: Drawable? = null
    public fun prettyPrint() {
        Log.v("errorrrr",appname +"\t" + pname + "\t" + versionName + "\t" + versionCode);
    }
}