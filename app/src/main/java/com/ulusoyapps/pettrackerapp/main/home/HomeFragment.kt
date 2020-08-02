/*
 * Copyright 2020 Cagatay Ulusoy (Ulus Oy Apps). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License") you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 *  See the License for the specific language governing permissions and limitations under the License.
 */

package com.ulusoyapps.pettrackerapp.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ulusoyapps.pettrackerapp.R
import com.ulusoyapps.pettrackerapp.databinding.FragmentHomeBinding
import com.ulusoyapps.pettrackerapp.main.home.epoxy.HomeEpoxyController
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlinx.android.synthetic.main.app_bar.*

class HomeFragment : DaggerFragment() {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        page_title.text = getString(R.string.my_pets)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
        val controller = HomeEpoxyController(viewModel)
        binding.recyclerView.setController(controller)

        with(viewModel) {

            getAllPets()

            petsAndTrackers.observe(viewLifecycleOwner, Observer { petsAndTrackers ->
                controller.updateData(petsAndTrackers)
            })

            nextScreen.observe(viewLifecycleOwner, Observer { screen ->
                HomeFragmentDirections.run {
                    when (screen) {
                        is TrackPetScreen -> {
                            // actionHomeFragmentToTrackFragment(screen.petName)
                            null
                        }
                        is AddPetScreen -> if (screen.petName == null) {
                            actionHomeFragmentToAddPetFragment()
                        } else {
                            actionHomeFragmentToAddPetFragmentForEdit(screen.petName)
                        }
                        NoAction -> null
                        AddTrackerScreen -> actionHomeFragmentToAddTrackerFragment()
                    }?.let { action ->
                        this@HomeFragment.findNavController().navigate(action)
                        onNavigatedToNextScreen()
                    }
                }
            })
        }
    }
}
