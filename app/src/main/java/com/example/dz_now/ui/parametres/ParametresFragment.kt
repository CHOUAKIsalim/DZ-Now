package com.example.dz_now.ui.parametres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dz_now.R

class ParametresFragment : Fragment() {

    private lateinit var parametresViewModel: ParametresViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parametresViewModel =
            ViewModelProviders.of(this).get(ParametresViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_accueil, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        parametresViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}