package com.example.ipizza.fragment.EndOrderFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.example.ipizza.R
import com.example.ipizza.contract.navigator


class EndOrderFragment : Fragment() {

    lateinit var root:View
    lateinit var backButton:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_end_order, container, false)

        backButton = root.findViewById(R.id.backMenuButton)

        backButton.setOnClickListener(){
            navigator().goBack()
        }

        return root
    }


}