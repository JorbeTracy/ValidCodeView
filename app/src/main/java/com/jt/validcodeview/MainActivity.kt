package com.jt.validcodeview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.jt.validcodeviewlib.ValidCodeView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_tran_mode.setOnClickListener {
            valid_view.setTextMode(getTextMode())
        }
        valid_view.setOnEditorActionListener { _, actionId, _ ->
            when(actionId) {
                EditorInfo.IME_ACTION_DONE -> checkInput()
            }
            true
        }
    }

    private fun getTextMode(): ValidCodeView.ValidCodeMode {
        return if(valid_view.getTextMode() == ValidCodeView.ValidCodeMode.TYPE_NORMAL.value) {
            ValidCodeView.ValidCodeMode.TYPE_PASSWORD
        } else {
            ValidCodeView.ValidCodeMode.TYPE_NORMAL
        }
    }

    private fun checkInput() {
        val code = valid_view.text.toString()
        if(code.length < valid_view.getCodeSize()) {
            toast("请输入${valid_view.getCodeSize()}位验证码")
        } else {
            toast(code)
        }
    }
}
