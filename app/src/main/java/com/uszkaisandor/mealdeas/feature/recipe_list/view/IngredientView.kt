package com.uszkaisandor.mealdeas.feature.recipe_list.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.uszkaisandor.mealdeas.R
import com.uszkaisandor.mealdeas.databinding.IngredientViewBinding

class IngredientView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding = IngredientViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun bind(name: String) {
        binding.name.text = binding.root.context.getString(R.string.ingredient_name, name)
    }

}