package com.example.androidmentoringprogram.firstlesson

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewpagerStateAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0->{
                return FirstFragment()
            }
            1->{
                return SecondFragment()
            }
            2->{
                return RecyclerViewFragment()
            }
        }
        return FirstFragment()
    }

}