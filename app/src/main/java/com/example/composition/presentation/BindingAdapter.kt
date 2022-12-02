package com.example.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.text.BoringLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.Results

@BindingAdapter("minCountAnswers")
fun bindRequestAnswers(textView: TextView, count: Int) {
    textView.text =
        String.format(textView.context.getString(R.string.NeedCountOfRightAnswers), count)
}

@BindingAdapter("minPercentAnswers")
fun bindMinPercenrAnswers(textView: TextView, count: Int) {
    textView.text =
        String.format(textView.context.getString(R.string.NeedPercentsOfRightAnswers), count)
}

@BindingAdapter("countRightAnswers")
fun bindCountRightAnswers(textView: TextView, count: Int) {
    textView.text = String.format(textView.context.getString(R.string.CountOfRightAnswers), count)
}

@BindingAdapter("currentPercents")
fun bindCurrentPercents(textView: TextView, result: Results) {
    var percent = 0
    if (result.countOfQuestions != 0) {
        percent =
            (result.countOfRightAnswers / result.countOfQuestions.toDouble() * GameViewModel.PERCENTS).toInt()
    }
    textView.text =
        String.format(textView.context.getString(R.string.PercentOfRightAnswers), percent)
}

@BindingAdapter("image")
fun bindImage(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(if (winner) {
        R.drawable.goodsmile
    } else R.drawable.badsmile)
}

@BindingAdapter("numberAsText")
fun numberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("enoughCount")
fun enoughCount(textView: TextView, enough: Boolean) {
    textView.setTextColor(getColor(textView.context, enough))
}

@BindingAdapter("enoughPercents")
fun enoughPercents(progressBar: ProgressBar, enough: Boolean) {
    progressBar.progressTintList = ColorStateList.valueOf(getColor(progressBar.context, enough))
}

interface OnOptionclickListener {
    fun onOptionclick(option: Int)
}

@BindingAdapter("onOptionclicklistener")
fun onOptionclickListenerFun (textView: TextView, onOptionclickListener: OnOptionclickListener) {
    textView.setOnClickListener {
        onOptionclickListener.onOptionclick(textView.text.toString().toInt())
    }
}


private fun getColor(context: Context, state: Boolean): Int {
    val color = if (state) {
        android.R.color.holo_green_light
    } else android.R.color.holo_red_light
    return ContextCompat.getColor(context, color)
}

