package net.leo.libreraparaimgenes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(private val movies: List<Movie>) :
    SliderViewAdapter<SliderAdapter.SliderViewHolder>() {

    override fun getCount(): Int = movies.size

    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.slider_item, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        val movie = movies[position]
        viewHolder.imageView.setImageResource(movie.imageResourceId)
    }

    class SliderViewHolder(itemView: View) : ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)
    }
}
