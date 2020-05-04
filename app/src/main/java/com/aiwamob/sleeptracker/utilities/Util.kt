package com.aiwamob.sleeptracker.utilities

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun makeSnackBar(view: View, message: String){
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}