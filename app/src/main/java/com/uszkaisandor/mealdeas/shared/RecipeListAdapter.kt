package com.uszkaisandor.mealdeas.shared

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.uszkaisandor.mealdeas.data.entities.Recipe
import com.uszkaisandor.mealdeas.databinding.RecipeViewBinding
import com.uszkaisandor.mealdeas.feature.recipe_list.view.IngredientView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter

class RecipeListAdapter(
    private val onItemClick: (Recipe) -> Unit,
) : PagingDataAdapter<Recipe, RecipeListAdapter.RecipeViewHolder>(RecipeComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding =
            RecipeViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(
            binding,
            onItemClick = { position ->
                val article = getItem(position)
                article?.let { safeArticle ->
                    onItemClick(safeArticle)
                }
            },
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }


    inner class RecipeViewHolder(
        private val binding: RecipeViewBinding,
        onItemClick: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                this.root.setOnClickListener {
                    if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                        onItemClick(bindingAdapterPosition)
                    }
                }
            }
        }

        fun bind(recipe: Recipe) {
            binding.apply {
                recipePhoto.load(recipe.imageUri)
                recipeName.text = recipe.name
                ingredientContainer.apply {
                    this.removeAllViews()
                    recipe.ingredients?.forEach {
                        val ingredientView = IngredientView(binding.root.context)
                        ingredientView.bind(it.name)
                        this.addView(ingredientView)
                    }
                }
            }
        }
    }

}