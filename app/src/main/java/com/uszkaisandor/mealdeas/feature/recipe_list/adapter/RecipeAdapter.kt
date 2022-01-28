package com.uszkaisandor.mealdeas.feature.recipe_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.uszkaisandor.mealdeas.data.entities.Recipe
import com.uszkaisandor.mealdeas.databinding.RecipeViewBinding
import com.uszkaisandor.mealdeas.feature.recipe_list.view.IngredientView

class RecipeAdapter(private var dataset: List<Recipe>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RecipeViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RecipeViewHolder).bind(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

    fun submitList(items: List<Recipe>?) {
        dataset = items ?: emptyList()
        notifyDataSetChanged()
    }

    inner class RecipeViewHolder(private val binding: RecipeViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.apply {
                recipePhoto.load(recipe.imageUri)
                recipeName.text = recipe.name
                ingredientContainer.apply {
                    this.removeAllViews()
                    recipe.ingredients.forEach {
                        val ingredientView = IngredientView(binding.root.context)
                        ingredientView.bind(it.name)
                        this.addView(ingredientView)
                    }
                }
            }
        }

    }

}