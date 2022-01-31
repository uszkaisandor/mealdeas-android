package com.uszkaisandor.mealdeas.feature.recipe_list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uszkaisandor.mealdeas.BaseFragment
import com.uszkaisandor.mealdeas.databinding.FragmentRecipeListBinding
import com.uszkaisandor.mealdeas.shared.RecipeListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RecipeListFragment : BaseFragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    private var currentBinding: FragmentRecipeListBinding? = null
    private val binding get() = currentBinding!!

    private lateinit var adapter: RecipeListAdapter

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

        val adapter = RecipeListAdapter(
            onItemClick = { recipe ->
                // todo something with item
            },
        )

        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY


        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.recipeList.collectLatest { data ->
                adapter.submitData(data)
            }
        }

    }

    override fun onDestroyView() {
        currentBinding = null
        super.onDestroyView()
    }

}