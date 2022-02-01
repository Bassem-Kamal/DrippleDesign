package com.example.drippledesign

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.drippledesign.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : BaseFragment<FragmentMainBinding>() {
    override val bindLayout:BindLayout<FragmentMainBinding>
        get() = FragmentMainBinding::inflate

    override fun observeViews() {
        binding.viewPager.adapter =
            activity?.let { FragmentAdapter(it.supportFragmentManager, lifecycle) }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Home"
                1 -> tab.text = "How To"
                2 -> tab.text = "Profile"
            }
        }.attach()
    }
}

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> HowToFragment()
            2 -> ProfileFragment()
            else -> HomeFragment()
        }
    }
}