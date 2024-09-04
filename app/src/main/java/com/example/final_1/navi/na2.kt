package com.example.final_1.navi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.final_1.R
import com.example.final_1.databinding.FragmentNa2Binding


class na2 : Fragment() {

    private lateinit var binding: FragmentNa2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_na2, container, false)

        binding.navi1.setOnClickListener{
            it.findNavController().navigate(R.id.action_na2_to_na1)
        }

        binding.navi3.setOnClickListener{
            it.findNavController().navigate(R.id.action_na2_to_na3)
        }

        binding.navi4.setOnClickListener{
            it.findNavController().navigate(R.id.action_na2_to_na4)
        }

        binding.navi5.setOnClickListener{
            it.findNavController().navigate(R.id.action_na2_to_na5)
        }



        return binding.root
    }

}