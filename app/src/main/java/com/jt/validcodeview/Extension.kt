package com.jt.validcodeview

import android.app.Activity
import android.widget.Toast

/**
 *
 * @author Tu
 * @date 2020/1/14 16:37
 */
fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}