package ua.zp.gardendirectory.ui.view_custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import ua.zp.gardendirectory.R
import ua.zp.gardendirectory.databinding.ItemSearchBinding

class SearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): FrameLayout(context, attrs, defStyle) {

    private val binding: ItemSearchBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = ItemSearchBinding.inflate(inflater, this, true)
    }

}