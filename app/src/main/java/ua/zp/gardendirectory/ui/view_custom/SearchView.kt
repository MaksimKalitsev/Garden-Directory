package ua.zp.gardendirectory.ui.view_custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import ua.zp.gardendirectory.R
import ua.zp.gardendirectory.databinding.ItemSearchBinding

class SearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): FrameLayout(context, attrs, defStyle) {

    interface Callback {
        fun onQueryChanged(query: String)
    }

    private val binding: ItemSearchBinding

    private var callback: Callback? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = ItemSearchBinding.inflate(inflater, this, true)
        binding.edtSearchText.addTextChangedListener {
            it?.let { query ->
                callback?.onQueryChanged(query.toString())
            }
        }
    }
    fun setCallback(callback: Callback) {
        this.callback = callback
    }
}