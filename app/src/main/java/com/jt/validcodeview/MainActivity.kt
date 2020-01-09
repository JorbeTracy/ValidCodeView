package com.jt.validcodeview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}
