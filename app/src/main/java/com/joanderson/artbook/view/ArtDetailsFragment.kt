package com.joanderson.artbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.joanderson.artbook.R
import com.joanderson.artbook.databinding.FragmentArtDetailsBinding
import com.joanderson.artbook.util.Status
import com.joanderson.artbook.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    val glide : RequestManager
) : Fragment(R.layout.fragment_art_details){

    lateinit var viewModel : ArtViewModel

    private var fragmentBinding : FragmentArtDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.ivArt.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.btSaveNewArt.setOnClickListener {
            viewModel.makeArt(
                binding.tvArtName.text.toString(),
                binding.tvArtistName.text.toString(),
                binding.TvArtYear.text.toString()
            )
        }
    }

    private fun subscribeToObservers() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner) { url ->
            fragmentBinding?.let {
                glide.load(url).into(it.ivArt)
            }
        }

        viewModel.insertArtMessage.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Added successfully", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message?: "Unknown Error", Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {

                }
            }
        }
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}