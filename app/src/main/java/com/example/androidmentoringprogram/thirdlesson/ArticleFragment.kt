package com.example.androidmentoringprogram.thirdlesson

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.androidmentoringprogram.R

class ArticleFragment : Fragment() {

    companion object{
        const val FRAGMENT_TITLE_INTENT = "titleInputExtra"
        const val FRAGMENT_SOURCE_INTENT = "sourceInputExtra"
        const val FRAGMENT_DESC_INTENT = "descInputExtra"
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
                view.findViewById<TextView>(R.id.articleFragmentTitle).text = it
            }
            getStringExtra(FRAGMENT_SOURCE_INTENT).let {
                view.findViewById<TextView>(R.id.articleFragmentSource).text = it
            }
            getStringExtra(FRAGMENT_DESC_INTENT).let {
                view.findViewById<TextView>(R.id.articleFragmentDesc).text = it
            }
        }
    }

}