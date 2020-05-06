package com.aiwamob.sleeptracker.ui.tracker

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.aiwamob.sleeptracker.R
import com.aiwamob.sleeptracker.database.SleepDatabase
import com.aiwamob.sleeptracker.databinding.FragmentTrackerBinding

/**
 * A simple [Fragment] subclass.
 */
class Tracker : Fragment() {

    private lateinit var trackerBinding: FragmentTrackerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        trackerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tracker, container, false)
        //requireNotNull(this.activity).application
        val application: Application = requireNotNull(this.activity).application
        val dataSource = SleepDatabase.getInstance(application).sleepDao
        val trackerViewModelFactory = TrackerViewModelFactory(dataSource, application)
        val trackerViewModel = ViewModelProvider(this, trackerViewModelFactory).get(TrackerViewModel::class.java)

        trackerBinding.trackerViewModel = trackerViewModel
        trackerBinding.lifecycleOwner = this

        /*trackerBinding.apply {

            stopRecordButton.setOnClickListener {
                it.findNavController().navigate(TrackerDirections.actionTrackerToQuality())
            }

        }*/

        return trackerBinding.root
    }

}
