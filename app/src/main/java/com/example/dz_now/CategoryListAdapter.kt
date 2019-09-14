package com.example.dz_now

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dz_now.entites.Category


class CategoryListAdapter(
    val context: Context,
    var list: List<Category>
) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoryViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.category_item, p0, false)
        return CategoryViewHolder(v)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: CategoryViewHolder, p1: Int) {
        val category: Category= list[p1]
        p0.titre.text = category.name
        if(category.image !=null && category.image != "null" && category.image != "")
        p0.image.setImageDrawable(context.getDrawable(R.drawable.img1))
        else
        p0.image.setImageDrawable(context.getDrawable(R.drawable.img1))
//        p0.image.alpha = 0.7F
    }


    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titre = itemView.findViewById(R.id.titre) as TextView
        val image = itemView.findViewById(R.id.image_view) as ImageView
    }
}