package com.jt.validcodeview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.jt.validcodeviewlib.ValidCodeView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var modeFlag = 0

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
        return if(modeFlag == 0) {
            modeFlag = 1
            ValidCodeView.ValidCodeMode.TYPE_NORMAL
        } else {
            modeFlag = 0
            ValidCodeView.ValidCodeMode.TYPE_PASSWORD
        }
    }

    private fun checkInput() {
        val code = valid_view.text.toString()
        if(code.length < 4) {
            toast("请输入完整验证码")
        } else {
            toast(code)
        }
    }
}
