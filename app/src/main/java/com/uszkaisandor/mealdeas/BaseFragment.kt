package com.uszkaisandor.mealdeas

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment {

    constructor()

    constructor(layoutId: Int) {
        Fragment(layoutId)
    }

}
