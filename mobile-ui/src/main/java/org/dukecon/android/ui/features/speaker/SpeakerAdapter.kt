package org.dukecon.android.ui.features.speaker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_speaker.view.*
import org.dukecon.android.ui.R
import org.dukecon.android.ui.utils.DrawableUtils
import org.dukecon.presentation.model.SpeakerView
import com.bumptech.glide.request.RequestOptions



internal class SpeakerAdapter(val wrapsWidth: Boolean = true, val onSpeakerClickedListener: ((speaker: SpeakerView, view: View) -> Unit)) :
        RecyclerView.Adapter<SpeakerAdapter.ViewHolder>() {

    val speakers: MutableList<SpeakerView> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_speaker, parent, false)
        if (!wrapsWidth) {
            v.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        }
        return ViewHolder(v, onSpeakerClickedListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(speakers[position])
    }

    override fun getItemCount(): Int {
        return speakers.size
    }

    internal class ViewHolder(itemView: View, private val onSpeakerClickedListener: ((speaker: SpeakerView, view: View) -> Unit)) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var speaker: SpeakerView? = null
        val image: ImageView
        val name: TextView
        val title: TextView

        init {
            image = itemView.image
            name = itemView.name
            title = itemView.title
            itemView.setOnClickListener(this)
        }

        fun bind(speaker: SpeakerView) {
            this.speaker = speaker
            name.text = speaker.name
            title.text = speaker.title

            val options = RequestOptions()
                    .placeholder(DrawableUtils.create(itemView.context, R.drawable.ph_speaker))

            Glide.with(itemView.context)
                    .load(speaker.avatar)
                    .apply(options)
                    .into(image)
        }

        override fun onClick(v: View?) {
            val sp = speaker
            if (sp != null) {
                onSpeakerClickedListener(sp, image)
            }
        }
    }
}