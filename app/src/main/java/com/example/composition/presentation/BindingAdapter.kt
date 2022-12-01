package com.example.composition.presentation

import android.text.BoringLayout
import android.widget.ImageView
import android.widget.TextView
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
    val percent = (result.countOfRightAnswers / result.countOfQuestions) * GameViewModel.PERCENTS
    textView.text =
        String.format(textView.context.getString(R.string.PercentOfRightAnswers), percent)
}

@BindingAdapter("image")
fun bindImage(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(if (winner) {
        R.drawable.goodsmile
    } else R.drawable.badsmile)
}

