package com.aiwamob.sleeptracker.ui.quality

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aiwamob.sleeptracker.R
import com.aiwamob.sleeptracker.database.SleepDao
import com.aiwamob.sleeptracker.database.SleepDatabase
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

        val application = requireNotNull(this.activity).application
        val argument = arguments?.let { QualityArgs.fromBundle(it) }
        val sleepId = argument?.sleepId
        val sleepDao = SleepDatabase.getInstance(application).sleepDao
        val viewModelFactory = sleepId?.let { QualityViewModelFactory(it, sleepDao) }
        val qualityVModel = viewModelFactory?.let {
            ViewModelProvider(this,it).get(QualityViewModel::class.java)
        }

        qualityBinding.apply {
            qualityViewModel = qualityVModel
            lifecycleOwner = this@Quality
        }


        qualityVModel?.sleepQualityToTracker?.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    this.findNavController().navigate(QualityDirections.actionQualityToTracker())
                    qualityVModel.doneNavigation()
                }
            }
        })

        return qualityBinding.root
    }

}
