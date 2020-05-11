package com.aiwamob.sleeptracker.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aiwamob.sleeptracker.R
import com.aiwamob.sleeptracker.database.SleepDatabase
import com.aiwamob.sleeptracker.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class Detail : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        val argument = arguments?.let { DetailArgs.fromBundle(it) }
        val id = argument!!.sleepID

        val application = requireNotNull(this.activity).application
        val sleepDao = SleepDatabase.getInstance(application).sleepDao
        val detailFactory = DetailViewModelFactory(sleepDao, id)
        val detailVM = ViewModelProvider(this, detailFactory).get(DetailViewModel::class.java)

        binding.apply {
            detailViewModel = detailVM
            executePendingBindings()
        }

        detailVM.sleepElt.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.aSleep = it
            }
        })

        detailVM.deletionCompleted.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    this.findNavController().navigate(DetailDirections.actionDetailToTracker())
                    detailVM.sleepDeleteDone()
                }
            }
        })

        return binding.root
    }

}
