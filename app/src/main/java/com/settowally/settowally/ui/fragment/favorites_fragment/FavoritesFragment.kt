package com.settowally.settowally.ui.fragment.favorites_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.settowally.settowally.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private var mBinding: FragmentFavoritesBinding? = null
    private val binding get() = mBinding!!
    private val viewModel: FavoritesViewModel by viewModels()
    private val adapter: FavoritesAdapter by lazy {
        FavoritesAdapter { photo ->
            val action =
                FavoritesFragmentDirections.actionFavoritesFragmentToWallpaperDetailsFragment(photo)
            findNavController().navigate(action)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentFavoritesBinding.inflate(inflater, container, false)
        binding.favoritesRecyclerView.adapter=adapter
        viewModel.localDbResponse.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
        viewModel.getAllSavedPhotos()

        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}