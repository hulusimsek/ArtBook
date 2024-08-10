package com.hulusimsek.a3_artbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hulusimsek.a3_artbook.R
import com.hulusimsek.a3_artbook.adapter.ArtRecyclerAdapter
import com.hulusimsek.a3_artbook.databinding.FragmentArtsBinding
import javax.inject.Inject

class ArtFragment @Inject constructor(
    val RecyclerAdapter : ArtRecyclerAdapter
) : Fragment(R.layout.fragment_arts) {
    private var fragmentBinding : FragmentArtsBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding

        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }


    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}