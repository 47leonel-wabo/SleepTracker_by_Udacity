package com.aiwamob.sleeptracker.ui.tracker

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.aiwamob.sleeptracker.R
import com.aiwamob.sleeptracker.database.SleepDatabase
import com.aiwamob.sleeptracker.databinding.FragmentTrackerBinding
import com.aiwamob.sleeptracker.ui.SleepAdapter
import com.google.android.material.snackbar.Snackbar

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

        val sleepAdapter = SleepAdapter(SleepAdapter.SleepClickListener {
            trackerViewModel.onSleepItemSelected(it)
        })
        val sleepLayoutManager = GridLayoutManager(activity, 3)
        sleepLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when(position){
                0 -> 3
                else -> 1
            }

        }
        trackerBinding.sleepRecyclerList.apply {
            layoutManager = sleepLayoutManager
            adapter = sleepAdapter
        }
        trackerViewModel.allSleeps.observe(viewLifecycleOwner, Observer {
            it?.let {
                sleepAdapter.addHeaderAndSubmitList(it)
            }
        })

        trackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(TrackerDirections.actionTrackerToQuality(it.sleepId))
                trackerViewModel.doneNavigation()
            }
        })

        trackerViewModel.navigateToSleepDataQuality.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(TrackerDirections.actionTrackerToDetail(it))
                trackerViewModel.onSleepDataNavigationDone()
            }
        })

        trackerViewModel.showSnackBar.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    Snackbar.make(requireActivity().findViewById(android.R.id.content), "Cleaning...", Snackbar.LENGTH_SHORT).show()
                    trackerViewModel.doneShowingSnackBar()
                }
            }
        })

        return trackerBinding.root
    }

}
