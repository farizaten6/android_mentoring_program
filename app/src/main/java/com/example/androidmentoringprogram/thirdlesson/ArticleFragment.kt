package com.example.androidmentoringprogram.thirdlesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.androidmentoringprogram.R
import com.squareup.picasso.Picasso

class ArticleFragment : Fragment() {

    companion object{
        const val FRAGMENT_TITLE_INTENT = "titleInputExtra"
        const val FRAGMENT_SOURCE_INTENT = "sourceInputExtra"
        const val FRAGMENT_DESC_INTENT = "descInputExtra"
        const val FRAGMENT_IMAGE_URL_INTENT = "imageUrlInputExtra"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.intent?.apply {
            getStringExtra(FRAGMENT_TITLE_INTENT).let {
                view.findViewById<TextView>(R.id.newsItemTitle).text = it
            }
            getStringExtra(FRAGMENT_SOURCE_INTENT).let {
                view.findViewById<TextView>(R.id.articleFragmentSource).text = it
            }
            getStringExtra(FRAGMENT_DESC_INTENT).let {
                view.findViewById<TextView>(R.id.articleFragmentDesc).text = it
            }
            getStringExtra(FRAGMENT_IMAGE_URL_INTENT).let {
                val imageView: ImageView = view.findViewById(R.id.articleFragmentImage)
                val picasso = Picasso.get()
                picasso.load(it).into(imageView)
            }
        }
    }

}