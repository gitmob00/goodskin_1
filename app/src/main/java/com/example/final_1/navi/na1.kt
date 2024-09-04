package com.example.final_1.navi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.final_1.R
import com.example.final_1.databinding.FragmentNa1Binding



class na1 : Fragment() {

    private lateinit var binding: FragmentNa1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_na1, container, false)

        binding.navi2.setOnClickListener{
            it.findNavController().navigate(R.id.action_na1_to_na2)
        }

        binding.navi3.setOnClickListener{
            it.findNavController().navigate(R.id.action_na1_to_na3)
        }

        binding.navi4.setOnClickListener{
            it.findNavController().navigate(R.id.action_na1_to_na4)
        }

        binding.navi5.setOnClickListener{
            it.findNavController().navigate(R.id.action_na1_to_na5)
        }


        // Inflate the layout for this fragment
        return binding.root
    }

}