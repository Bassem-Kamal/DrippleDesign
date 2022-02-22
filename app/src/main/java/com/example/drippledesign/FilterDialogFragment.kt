package com.example.drippledesign

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drippledesign.databinding.FragmentFilterDialogBinding
import com.example.drippledesign.databinding.MealItemBinding
import com.example.drippledesign.databinding.MinuteItemBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterDialogBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterDialogBinding.inflate(inflater, container, false)
        val anim: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_down_to_up)
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
            layoutManager = CustomLinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = MealsAdapter(ConstantData.mealList) { view, pos, meal ->
                scrollToCenter(this, layoutManager as CustomLinearLayoutManager, view, pos)
            }.apply { setHasStableIds(true) }
        }
    }

    private fun setUpMinutesRecyclerView() {
        binding.minutesList.apply {
            layoutManager = CustomLinearLayoutManager(context)
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            adapter = MinutesAdapter(ConstantData.minutesList) { view, pos, deliveryTime ->
                scrollToCenter(this, layoutManager as CustomLinearLayoutManager, view, pos)
            }.apply { setHasStableIds(true) }
        }
    }

    private fun scrollToCenter(
        recyclerView: RecyclerView,
        layoutManager: CustomLinearLayoutManager,
        selectedView: View,
        position: Int
    ) {
        val itemToScroll: Int = recyclerView.getChildLayoutPosition(selectedView)
        val centerOfScreen: Int = recyclerView.width / 2 - selectedView.width / 2
        recyclerView.layoutManager = context?.let { CustomLinearLayoutManager(it, centerOfScreen) }
        // layoutManager.scrollToPositionWithOffset(position, centerOfScreen)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private class MinutesAdapter(
    private val minutesList: List<DeliveryTime>,
    private val onItemClick: (View, Int, DeliveryTime) -> Unit
) :
    ListAdapter<DeliveryTime, MinutesAdapter.MinutesViewHolder>(DiffCallback) {

    private lateinit var binding: MinuteItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinutesViewHolder {
        binding = MinuteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MinutesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MinutesViewHolder, position: Int) {
        binding.minuteItemText.text = minutesList[position].minutes.toString()
        binding.root.setOnClickListener {
            onItemClick.invoke(it, position, minutesList[position])
        }
    }

    override fun getItemId(position: Int): Long {
        return minutesList[position].id.hashCode().toLong()
    }

    private fun changeItemAppearance(adapterPosition: Int) {
        // set selected item to be centered in arc layout manager
        binding.minuteItemMeasurement.setTextColor(Color.BLACK)
        binding.minuteItemText.setTextColor(Color.BLACK)

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

private class MealsAdapter(
    private val mealList: List<Meal>,
    private val onItemClick: (View, Int, Meal) -> Unit
) : ListAdapter<Meal, MealsAdapter.MealsViewHolder>(DiffCallback) {

    private lateinit var binding: MealItemBinding

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
        return MealsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        binding.mealItemName.text = mealList[position].name.uppercase()
        binding.let {
            Glide.with(it.root.context)
                .load(mealList[position].icon)
                .error(R.drawable.ic_launcher_background).into(it.mealItemIcon)
        }
        binding.root.setOnClickListener {
            onItemClick.invoke(binding.root, position, mealList[position])
        }
    }
}