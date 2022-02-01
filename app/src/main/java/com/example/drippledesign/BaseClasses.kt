package com.example.drippledesign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

typealias BindLayout<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    abstract val bindLayout: BindLayout<VB>
    val binding: VB get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = bindLayout.invoke(inflater, container, false)
        observeViews()
        return requireNotNull(_binding).root
    }

    abstract fun observeViews()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

abstract class BaseRecyclerAdapter<T : Any, VB : ViewBinding, VH : BaseViewHolder>(
    callback: DiffUtil.ItemCallback<T>) : ListAdapter<T, VH>(callback) {

    abstract var bind: (item: T, holder: BaseViewHolder, itemCount: Int) -> Unit


    override fun onBindViewHolder(holder: VH, position: Int) {
        bind(getItem(position), holder, itemCount)
    }

    override fun getItemCount() = currentList.size

    override fun submitList(items: List<T>?) {
        super.submitList(items ?: emptyList())
    }
}

abstract class BaseViewHolder constructor(viewBinding: ViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root)

