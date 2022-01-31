package com.uszkaisandor.mealdeas.shared

import androidx.recyclerview.widget.DiffUtil
import com.uszkaisandor.mealdeas.data.entities.Recipe

class RecipeComparator : DiffUtil.ItemCallback<Recipe>() {

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }
}