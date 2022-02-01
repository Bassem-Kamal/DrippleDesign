package com.example.drippledesign

import android.app.Dialog
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drippledesign.databinding.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterDialogFragment : BottomSheetDialogFragment(), MinutesListListener {

    private var _binding: FragmentFilterDialogBinding? = null
    private val binding get() = _binding!!
    private val mealsAdapter = MealsAdapter(ConstantData.mealList).apply { setHasStableIds(true) }
    private val minutesAdapter = MinutesAdapter(ConstantData.minutesList, this).apply { setHasStableIds(true) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilterDialogBinding.inflate(inflater, container, false)
        val anim: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_down_to_up)
        binding.root.layoutAnimation = anim

        setUpMealsRecyclerView()
        setUpMinutesRecyclerView()

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.isFitToContents = true
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.expandedOffset = 56
            window?.setBackgroundDrawableResource(R.drawable.transparent_rectangel)
        }
    }

    private fun setUpMealsRecyclerView() {
        binding.mealsList.apply {
            layoutManager = ArcLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = mealsAdapter
        }
    }

    private fun setUpMinutesRecyclerView() {
        binding.minutesList.apply {
            layoutManager = ArcLayoutManager(context)
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            adapter = minutesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(itemView: View,position: Int) {
        Log.e(tag, "onItemClick: $position" )
        binding.minutesList.scrollToPosition(position)
     /*   val itemToScroll: Int = binding.minutesList.getChildAdapterPosition(itemView)
        val centerOfScreen: Int = binding.minutesList.width / 2 - itemView.width / 2
        val left = binding.minutesList.width / 2
        val top = binding.minutesList.width / 2
        val right = binding.minutesList.width / 2
        val bottom = binding.minutesList.width / 2
        val rect:Rect= Rect(left, top, right, bottom)
        minutesAdapter.submitList(ConstantData.minutesList)
        binding.minutesList.layoutManager?.requestChildRectangleOnScreen(
            binding.minutesList,itemView,rect,false,false)*/
      //  (binding.minutesList.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(itemToScroll, centerOfScreen)

    }
}

private class MinutesAdapter(private val minutesList: List<DeliveryTime>, val listner: MinutesListListener) :
    ListAdapter<DeliveryTime, MinutesAdapter.MinutesViewHolder>(DiffCallback) {

    private var binding: MinuteItemBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinutesViewHolder {
        binding = MinuteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MinutesViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: MinutesViewHolder, position: Int) {
        binding?.minuteItemText?.text = minutesList[position].minutes.toString()
        binding?.root?.setOnClickListener {
            listner.onItemClick(binding!!.root,position)
        }

    }
    override fun getItemId(position: Int): Long {
        return minutesList[position].id.hashCode().toLong()
    }
    private fun changeItemAppearance(adapterPosition: Int) {
        // set selected item to be centered in arc layout manager
        binding?.minuteItemMeasurement?.setTextColor(Color.BLACK)
        binding?.minuteItemText?.setTextColor(Color.BLACK)

    }

    override fun getItemCount(): Int {
        return minutesList.size
    }

    class MinutesViewHolder(binding: MinuteItemBinding) : RecyclerView.ViewHolder(binding.root)
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<DeliveryTime>() {
            override fun areItemsTheSame(oldItem: DeliveryTime, newItem: DeliveryTime): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DeliveryTime, newItem: DeliveryTime): Boolean {
                return oldItem.minutes == newItem.minutes
            }

        }
    }
}

interface MinutesListListener {
    fun onItemClick(itemView: View,position: Int)
}

private class MealsAdapter(private val mealList: List<Meal>) :
    ListAdapter<Meal, MealsAdapter.MealsViewHolder>(DiffCallback) {

    private var binding: MealItemBinding? = null

    override fun getItemId(position: Int): Long {
        return mealList[position].id.hashCode().toLong()
    }
    override fun getItemCount(): Int {
        return mealList.size
    }

    class MealsViewHolder(binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Meal>() {
            override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
                return oldItem.icon == newItem.icon
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        binding = MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealsViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        binding?.mealItemName?.text = mealList[position].name.uppercase()
        binding?.let {
            Glide.with(it.root.context)
                .load(mealList[position].icon)
                .error(R.drawable.ic_launcher_background).into(it.mealItemIcon)
        }
    }
}