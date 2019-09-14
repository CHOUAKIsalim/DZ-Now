package com.example.dz_now.ui.enregistres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dz_now.R

class EnregistresFragment : Fragment() {

    private lateinit var enregistresViewModel: EnregistresViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        enregistresViewModel =
            ViewModelProviders.of(this).get(EnregistresViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_accueil, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        enregistresViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}