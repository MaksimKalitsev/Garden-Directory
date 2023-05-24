package ua.zp.moviedbportfolioproject.ui.view_custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import ua.zp.moviedbportfolioproject.databinding.ItemSearchBinding

class SearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    interface Callback {
        fun onQueryChanged(query: String)
    }

    private val binding: ItemSearchBinding

    private var callback: Callback? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = ItemSearchBinding.inflate(inflater, this, true)
//        binding.edtSearchText.addTextChangedListener {
//            it?.let { query ->
//                callback?.onQueryChanged(query.toString())
//            }
//        }
        binding.edtSearchText.doAfterTextChanged {
            checkEnabledEditText()
            it?.takeIf { binding.edtSearchText.hasFocus() }
                ?.let { query ->
                    callback?.onQueryChanged(query.toString())
                }
        }
        binding.ivClearText.setOnClickListener {
            binding.edtSearchText.text.clear()
        }
        checkEnabledEditText()
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }
    private fun checkEnabledEditText() = with(binding){
        ivClearText.isVisible = edtSearchText.text.isNullOrEmpty().not()
    }
}