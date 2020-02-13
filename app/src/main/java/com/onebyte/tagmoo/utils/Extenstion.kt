package com.onebyte.tagmoo.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.util.Base64
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.showLoadingDialog() : Dialog? {
//    val view = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null)
//    val alertDialog = AlertDialog.Builder(this).setView(view).setCancelable(false)
//    val dialog = alertDialog.create()
//    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//    view.loading.loadImage(R.drawable.loading)
//    dialog.show()

    return null
}

//fun Context.showDialog( resID: String, positive:(dialog: AlertDialog?) -> Unit, negative:(dialog: AlertDialog?) -> Unit){
//    val view = LayoutInflater.from(this).inflate(R.layout.dialog_view, null)
//    val alertDialog = AlertDialog.Builder(this).setView(view).setCancelable(false)
//    val dialog = alertDialog.create()
//    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//    //  view.dialogIcon.setImageResource(drawableRes)
//    view.dialogText.text = resID
//    view.positiveBtn.setOnClickListener{positive(dialog)}
//    //if (drawableRes == R.drawable.dialog_check) view.negativeButton.visibility =View.GONE
//    view.negativeButton.setOnClickListener{negative(dialog)}
//    dialog.show()
//}

fun ImageView.loadImage(@DrawableRes drawableRes: Int) {
    Glide.with(this).load(drawableRes).fitCenter().into(this)
}

fun ImageView.laodImage(url: String){
    Glide.with(this).load(url).into(this)
}

fun navigateActivity(activityClass: Class<out Activity>, activity: Activity, isFinish: Boolean){
    val intent = Intent(activity, activityClass)
    activity.startActivity(intent)
    if(isFinish)
        activity.finish()
}

fun navigateActivityWithBoolExtra(activityClass: Class<out Activity>, activity: Activity, key: String, value: Boolean,isFinish: Boolean){
    val intent = Intent(activity, activityClass)
    intent.putExtra(key, value)
    activity.startActivity(intent)
    if(isFinish)
        activity.finish()
}

