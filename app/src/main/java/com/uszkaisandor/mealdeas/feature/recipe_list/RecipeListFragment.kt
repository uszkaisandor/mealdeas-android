package com.uszkaisandor.mealdeas.feature.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.uszkaisandor.mealdeas.BaseFragment
import com.uszkaisandor.mealdeas.R
import com.uszkaisandor.mealdeas.databinding.FragmentRecipeListBinding
import com.uszkaisandor.mealdeas.feature.recipe_list.adapter.RecipeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RecipeListFragment : BaseFragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    private var currentBinding: FragmentRecipeListBinding? = null
    private val binding get() = currentBinding!!

    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentBinding = FragmentRecipeListBinding.bind(view)
        adapter = RecipeAdapter(arrayListOf())
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.recipeList.collect {
                adapter.submitList(it)
            }
        }

        viewModel.doRefresh(true)

    }

    override fun onDestroyView() {
        currentBinding = null
        super.onDestroyView()
    }

}