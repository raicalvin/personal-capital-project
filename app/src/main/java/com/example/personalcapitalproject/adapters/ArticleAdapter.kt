package com.example.personalcapitalproject.adapters

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.setPadding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.personalcapitalproject.R
import com.example.personalcapitalproject.helpers.ArticleDiffUtil
import com.example.personalcapitalproject.models.Article
import com.example.personalcapitalproject.models.ArticleWrapper
import com.example.personalcapitalproject.models.ItemType

class ArticleAdapter : ListAdapter<ArticleWrapper, RecyclerView.ViewHolder>(ArticleDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item.type) {
            ItemType.HEADER_ARTICLE -> ItemType.HEADER_ARTICLE.ordinal
            ItemType.SECTION_TITLE -> ItemType.SECTION_TITLE.ordinal
            ItemType.ARTICLE -> ItemType.ARTICLE.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        return when (viewType) {
            ItemType.HEADER_ARTICLE.ordinal -> ArticleViewHolder(createArticleView(context))
            ItemType.SECTION_TITLE.ordinal -> SectionTitleViewHolder(createSectionTitleView(context))
            else -> ArticleViewHolder(createArticleView(context))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder.itemViewType) {
            ItemType.HEADER_ARTICLE.ordinal -> (holder as ArticleViewHolder).bindHeaderArticle(item.article)
            ItemType.SECTION_TITLE.ordinal -> (holder as SectionTitleViewHolder).bindSection(item)
            else -> (holder as ArticleViewHolder).bindHeaderArticle(item.article)
        }


        // If position is 0, add in the secondary textView
    }

    /** Article ViewHolder */
    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = view.findViewById<TextView>(R.id.article_title_text_view)
        fun bindData(article: Article) {
            textView.text = article.category
        }
        fun bindHeaderArticle(article: Article?) {
            article?.let {
                bindData(article)
                // TODO: Also bind the secondary summary TextView
            }
        }
    }

    /** Article Header ViewHolder */
    class ArticleHeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

    /** Section Title ViewHolder */
    class SectionTitleViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val sectionTitle = view.findViewById<TextView>(R.id.section_title_text_view)
        fun bindSection(articleWrapper: ArticleWrapper) {
            sectionTitle.text = articleWrapper.type.description
        }
    }

    private fun createArticleView(context: Context): View {
        // Parent Layout
        val layout = ConstraintLayout(context)
        layout.id = R.id.article_main_layout

        // ImageView
        val articleImageView = ImageView(context)
        articleImageView.id = R.id.article_image_view
        val articleImageViewParams = ConstraintLayout.LayoutParams(0, 0)
        articleImageViewParams.dimensionRatio = "3:2"
        articleImageView.setImageResource(R.drawable.ic_launcher_background)
        articleImageView.scaleType = ImageView.ScaleType.CENTER_CROP
        articleImageView.layoutParams = articleImageViewParams
        layout.addView(articleImageView)

        // Title TextView
        val titleTextView = TextView(context)
        titleTextView.id = R.id.article_title_text_view
        titleTextView.maxLines = 2
        titleTextView.ellipsize = TextUtils.TruncateAt.END
        val titleTextViewParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        titleTextView.layoutParams = titleTextViewParams
        layout.addView(titleTextView)

        val layoutConstraintSet = ConstraintSet()
        layoutConstraintSet.clone(layout)

        layoutConstraintSet.connect(articleImageView.id, ConstraintSet.START, layout.id, ConstraintSet.START)
        layoutConstraintSet.connect(articleImageView.id, ConstraintSet.END, layout.id, ConstraintSet.END)
        layoutConstraintSet.connect(articleImageView.id, ConstraintSet.TOP, layout.id, ConstraintSet.TOP)

        layoutConstraintSet.connect(titleTextView.id, ConstraintSet.START, layout.id, ConstraintSet.START)
        layoutConstraintSet.connect(titleTextView.id, ConstraintSet.END, layout.id, ConstraintSet.END)
        layoutConstraintSet.connect(titleTextView.id, ConstraintSet.TOP, articleImageView.id, ConstraintSet.BOTTOM)

        layoutConstraintSet.applyTo(layout)

        // Summary TextView

        return layout
    }

    private fun createSectionTitleView(context: Context): View {
        val sectionTitleTextView = TextView(context)
        sectionTitleTextView.id = R.id.section_title_text_view
        sectionTitleTextView.setPadding(24)
        return sectionTitleTextView
    }
}

