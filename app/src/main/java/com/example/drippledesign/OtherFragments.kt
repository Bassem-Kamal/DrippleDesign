package com.example.drippledesign

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.drippledesign.databinding.FragmentHowToBinding
import com.example.drippledesign.databinding.FragmentProfileBinding

class HowToFragment : BaseFragment<FragmentHowToBinding>() {

    override val bindLayout: BindLayout<FragmentHowToBinding>
        get() = FragmentHowToBinding::inflate

    override fun observeViews() {
    }
}

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val bindLayout: BindLayout<FragmentProfileBinding>
        get() = FragmentProfileBinding::inflate

    override fun observeViews() {
    }
}