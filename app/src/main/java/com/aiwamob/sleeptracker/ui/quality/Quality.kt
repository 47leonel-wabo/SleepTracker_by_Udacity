package com.aiwamob.sleeptracker.ui.quality

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.aiwamob.sleeptracker.R
import com.aiwamob.sleeptracker.databinding.FragmentQualityBinding

/**
 * A simple [Fragment] subclass.
 */
class Quality : Fragment() {

    private lateinit var qualityBinding: FragmentQualityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        qualityBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_quality, container, false)


        return qualityBinding.root
    }

}
