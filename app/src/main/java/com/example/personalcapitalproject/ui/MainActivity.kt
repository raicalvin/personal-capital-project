package com.example.personalcapitalproject.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personalcapitalproject.R
import com.example.personalcapitalproject.adapters.ArticleAdapter
import com.example.personalcapitalproject.helpers.hogwarts
import com.example.personalcapitalproject.viewmodels.BlogViewModel

class MainActivity : AppCompatActivity() {

    // private val viewModel: BlogViewModel by viewModels()
    // private val viewMode: BlogViewModel = ViewModelProvider(this).get(BlogViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setTitle() for the title of the page
        // getActionBar().setIcon(R.drawable.refresh_icon)

        /** Parent Layout */
        val mainLayout = ConstraintLayout(this)
        val layoutParamsMain = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mainLayout.layoutParams = layoutParamsMain
        mainLayout.background = getDrawable(R.color.design_default_color_secondary_variant)
        setContentView(mainLayout)

        /** RecyclerView */
        val recyclerView = RecyclerView(this)
        recyclerView.id = R.id.main_recycler_view
        val rvParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
        rvParams.setMargins(24, 24, 24, 24)
        recyclerView.layoutParams = rvParams

        mainLayout.addView(recyclerView)

        val rvConstraintSet = ConstraintSet()
        rvConstraintSet.clone(mainLayout) // clone the mainLayout constraints
        rvConstraintSet.connect(recyclerView.id, ConstraintSet.START, mainLayout.id, ConstraintSet.START)
        rvConstraintSet.connect(recyclerView.id, ConstraintSet.END, mainLayout.id, ConstraintSet.END)
        rvConstraintSet.connect(recyclerView.id, ConstraintSet.BOTTOM, mainLayout.id, ConstraintSet.BOTTOM)
        rvConstraintSet.connect(recyclerView.id, ConstraintSet.TOP, mainLayout.id, ConstraintSet.TOP)
        rvConstraintSet.applyTo(mainLayout)

        /** RecyclerView Setup */
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0 -> 2
                    else -> 1
                }
            }
        }
        recyclerView.layoutManager = layoutManager
        val articleAdapter = ArticleAdapter()
        recyclerView.adapter = articleAdapter

        /** ViewModel Observations */
        val viewModel: BlogViewModel = ViewModelProvider(this).get(BlogViewModel::class.java)
        viewModel.viewCreated()
        viewModel.blogResponse().observe(this, Observer { response ->
            response?.let {
                articleAdapter.submitList(response.items)
            }
        })
    }
}

// Steps:
// 1. Create the RecyclerView
// 2. Create the ListAdapter
// 3. Wrap the items in a class with three properties: headline, section divider, article
// 4. Construct the RV with a Grid Layout