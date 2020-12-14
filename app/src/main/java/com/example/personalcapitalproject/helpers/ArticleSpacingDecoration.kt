package com.example.personalcapitalproject.helpers

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ArticleSpacingDecoration(private val edgeSpacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        // The first two items (the Header and Section title) do NOT need edge spacing
        if (position == 0 || position == 1) {
            super.getItemOffsets(outRect, view, parent, state)
        } else {
            outRect.left = edgeSpacing
            outRect.right = edgeSpacing
            outRect.bottom = edgeSpacing
            outRect.top = edgeSpacing
        }
    }
}