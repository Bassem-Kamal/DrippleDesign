package com.example.drippledesign

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drippledesign.databinding.FragmentResultBinding
import com.example.drippledesign.databinding.ListItemBinding

class ResultFragment : BaseFragment<FragmentResultBinding>() {

    override val bindLayout: BindLayout<FragmentResultBinding>
        get() = FragmentResultBinding::inflate

    private val resultItemsAdapter = ResultItemsAdapter(ConstantData.savedItemsList)

    override fun observeViews() {
        binding.listItem.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = resultItemsAdapter
        }
        binding.up.setOnClickListener {
            it.findNavController().navigate(R.id.action_resultFragment_to_mainFragment)
        }
        binding.fabFilter.setOnClickListener {
            it.findNavController().navigate(R.id.action_resultFragment_to_filtterDialogFragment)
        }
    }
}

class ResultItemsAdapter(private val listItems: List<SavedItem>) :
    ListAdapter<SavedItem, ResultItemsAdapter.HomeImageViewHolder>(ImageDiffCallback) {
    private var binding: ListItemBinding? = null

    class HomeImageViewHolder(listItemBinding: ListItemBinding) :
        RecyclerView.ViewHolder(listItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeImageViewHolder {
        binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeImageViewHolder(binding!!)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: HomeImageViewHolder, position: Int) {
        val imageUri = listItems[position].imageUrl

        binding?.name?.text = listItems[position].name
        binding?.price?.text = listItems[position].price
        binding?.rate?.text = listItems[position].rate.toString()
        binding?.count?.text = listItems[position].count.toString()

        binding?.let {
            Glide.with(it.root.context)
                .load(imageUri)
                .error(R.drawable.ic_launcher_background).into(it.avatar)
        }
    }

    companion object {
        val ImageDiffCallback = object : DiffUtil.ItemCallback<SavedItem>() {
            override fun areItemsTheSame(oldItem: SavedItem, newItem: SavedItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SavedItem, newItem: SavedItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}
