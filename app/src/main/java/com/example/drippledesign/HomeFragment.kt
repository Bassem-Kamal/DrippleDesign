package com.example.drippledesign
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drippledesign.databinding.FragmentHomeBinding
import com.example.drippledesign.databinding.ListImageBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val naturalFoodAdapter = NaturalFoodAdapter(ConstantData.naturalFoodList)
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun observeViews() {
        binding.naturalFoodList.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = naturalFoodAdapter
        }
    }
}

class NaturalFoodAdapter(private val imagesList: List<NaturalFood>) :
    ListAdapter<NaturalFood, NaturalFoodAdapter.HomeImageViewHolder>(ImageDiffCallback) {
    private var binding: ListImageBinding? = null

    class HomeImageViewHolder(listImageBinding: ListImageBinding) :
        RecyclerView.ViewHolder(listImageBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeImageViewHolder {
        binding = ListImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeImageViewHolder(binding!!)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    override fun onBindViewHolder(holder: HomeImageViewHolder, position: Int) {
        val imageUri = imagesList[position].imageResource
        binding?.let {
            Glide.with(it.root.context)
                .load(imageUri)
                .error(R.drawable.ic_launcher_background).into(it.imageView)
        }
        binding?.imageView?.setOnClickListener {
            it.findNavController().navigate(R.id.action_global_resultFragment)
        }
    }

    companion object {
        val ImageDiffCallback = object : DiffUtil.ItemCallback<NaturalFood>() {
            override fun areItemsTheSame(oldItem: NaturalFood, newItem: NaturalFood): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NaturalFood, newItem: NaturalFood): Boolean {
                return oldItem.imageResource == newItem.imageResource
            }

        }
    }
}

data class NaturalFood(val id:Int, val imageResource:String)