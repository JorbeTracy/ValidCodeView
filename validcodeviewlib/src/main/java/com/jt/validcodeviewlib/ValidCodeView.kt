package com.jt.validcodeviewlib

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.InputFilter
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat

/**
 * View of SMS verification
 * @author Jorbe
 * @date 2020/1/9 9:14
 */
class ValidCodeView @JvmOverloads constructor(
    context: Context,
    var attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var mPaint = Paint()

    private var mCodeCount = 4    // code count, can be modified

    private var mLineColor = ContextCompat.getColor(context, R.color.num_verify_def_under_line)    // the underline color

    private var mLineWidth = resources.getDimensionPixelOffset(R.dimen.valid_code_text_size)    // the underline width

    private var mTextColor = ContextCompat.getColor(context, R.color.num_verify_def_text_color)    // code text color

    private var mTextSize = resources.getDimensionPixelOffset(R.dimen.valid_code_text_size)    // code text size

    private var mDotColor = ContextCompat.getColor(context, R.color.num_verify_def_dot_color)    // dot color in pwd mode

    private var mTextMode = ValidCodeMode.TYPE_NORMAL.value    // default mode

    private var mWidth = 0

    private var mHeight = 0

    init {

        initAttrs()
        initPaint()
        initConfig()
    }

    private fun initAttrs() {

        val array = context.obtainStyledAttributes(attrs, R.styleable.ValidCodeView)

        mCodeCount = array.getInteger(R.styleable.ValidCodeView_code_count, 4)
        mTextMode = array.getInteger(R.styleable.ValidCodeView_text_mode, ValidCodeMode.TYPE_NORMAL.value)
        mTextColor = array.getColor(R.styleable.ValidCodeView_text_color, ContextCompat.getColor(context, R.color.num_verify_def_text_color))
        mTextSize = array.getDimensionPixelOffset(R.styleable.ValidCodeView_text_size, resources.getDimensionPixelOffset(R.dimen.valid_code_text_size))
        mDotColor = array.getColor(R.styleable.ValidCodeView_dot_color, ContextCompat.getColor(context, R.color.num_verify_def_dot_color))
        mLineColor = array.getColor(R.styleable.ValidCodeView_line_color, ContextCompat.getColor(context, R.color.num_verify_def_under_line))
        mLineWidth = array.getDimensionPixelOffset(R.styleable.ValidCodeView_line_width, resources.getDimensionPixelOffset(R.dimen.valid_code_line_with))

        array.recycle()
    }

    private fun initPaint() {
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true
    }

    private fun initConfig() {

        setBackgroundColor(ContextCompat.getColor(context, R.color.color_transparent))
        isCursorVisible = false
        isSelected = false
        filters = arrayOf(InputFilter.LengthFilter(mCodeCount))

        // shield the select action callback
        customSelectionActionModeCallback = object : ActionMode.Callback {

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return false
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mWidth = w
        mHeight = h

        if (mLineWidth <= 10) {    // line width cannot be less than 10px
            mLineWidth = mWidth * 3 / mCodeCount / 4    // the default underline width
        }
    }

    override fun onDraw(canvas: Canvas?) {

        drawLine(canvas)

        when(mTextMode) {
            ValidCodeMode.TYPE_NORMAL.value -> drawText(canvas)
            ValidCodeMode.TYPE_PASSWORD.value -> drawDot(canvas)
        }
    }

    private fun drawLine(canvas: Canvas?) {

        mPaint.color = mLineColor
        mPaint.strokeWidth = 2F

        for (i in 0 until mCodeCount) {
            canvas?.drawLine(
                ((i + 0.5F) * (mWidth / mCodeCount) - mLineWidth * 0.5F),
                mHeight.toFloat(),
                ((i + 0.5F) * (mWidth / mCodeCount) + mLineWidth * 0.5F),
                mHeight.toFloat(),
                mPaint
            )
        }
    }

    private fun drawText(canvas: Canvas?) {

        mPaint.color = mTextColor
        mPaint.textSize = mTextSize.toFloat()
        mPaint.textAlign = Paint.Align.CENTER

        if(text != null) {

            for (i in 0 until text!!.length) {
                canvas?.drawText(
                    text!![i].toString(),
                    (i + 0.5F) * (mWidth / mCodeCount),
                    3 * mHeight / 4F,
                    mPaint
                )
            }
        }
    }

    private fun drawDot(canvas: Canvas?) {

        mPaint.color = mDotColor

        for(i in 0 until text!!.length) {
            canvas?.drawCircle(
                (i + 0.5F) * (mWidth / mCodeCount),
                5 * mHeight / 8F,
                10F,
                mPaint
            )
        }
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        invalidate()
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        if(selStart == selEnd) {
            setSelection(text!!.length)
        }
    }

    fun setTextMode(mode: ValidCodeMode) {
        this.mTextMode = mode.value
        invalidate()
    }

    enum class ValidCodeMode(var value: Int) {

        TYPE_NORMAL(0),
        TYPE_PASSWORD(1)
    }
}