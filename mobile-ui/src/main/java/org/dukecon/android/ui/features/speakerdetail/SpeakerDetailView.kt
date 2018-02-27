package org.dukecon.android.ui.features.speakerdetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.constraint.ConstraintLayout
import android.support.v4.app.ActivityCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.view_speaker_detail.view.*
import org.dukecon.android.ui.R
import org.dukecon.android.ui.ext.getActivity
import org.dukecon.android.ui.ext.getComponent
import org.dukecon.android.ui.utils.DrawableUtils
import org.dukecon.presentation.feature.speakerdetail.SpeakerDetailContract
import javax.inject.Inject


class SpeakerDetailView(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        ConstraintLayout(context, attrs, defStyle), SpeakerDetailContract.View {

    @Inject
    lateinit var presenter: SpeakerDetailContract.Presenter

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        context.getComponent<SpeakerDetailComponent>().inject(this)

        LayoutInflater.from(context).inflate(R.layout.view_speaker_detail, this, true)

        var speakerId = ""
        getActivity()?.let {
            speakerId = it.intent.getStringExtra("speaker_id")
        }

        // FIXME: the shared image is transition properly. The start/end locations are off
        //  ViewCompat.setTransitionName(image, "image_$speakerId")
        toolbar.setNavigationOnClickListener {
            if (context is Activity) {
                ActivityCompat.finishAfterTransition(context)
            }
        }

        presenter.onAttach(this)
    }

    override fun onDetachedFromWindow() {
        presenter.onDetach()
        super.onDetachedFromWindow()
    }

    fun setSpeakerId(speakerId: String) {
        presenter.setSpeakerId(speakerId)
    }

    override fun showSpeaker(speaker: org.dukecon.presentation.model.SpeakerDetailView) {
        name.text = speaker.name
        bio.text = speaker.bio

        if (speaker.twitter != null && speaker.twitter!!.isNotEmpty()) {
            twitter.visibility = VISIBLE
            twitter.text = speaker.twitter
            twitter.setCompoundDrawablesWithIntrinsicBounds(
                    DrawableUtils.create(context, R.drawable.ic_logo_twitter),
                    null,
                    null,
                    null)

            twitter.setOnClickListener {
                val twitterIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("${speaker.twitter}")
                }
                context.startActivity(twitterIntent)
            }
        } else {
            twitter.visibility = GONE
        }

        if (speaker.website?.isNotEmpty() ?: false) {
            github.visibility = VISIBLE
            github.text = speaker.website
            github.setCompoundDrawablesWithIntrinsicBounds(
                    DrawableUtils.create(context, R.drawable.ic_logo_github),
                    null,
                    null,
                    null)

            github.setOnClickListener {
                val githubIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("${speaker.website}")
                }
                context.startActivity(githubIntent)
            }
        } else {
            github.visibility = GONE
        }

        val options = RequestOptions()
                .placeholder(DrawableUtils.create(context, R.drawable.ph_speaker))

        Glide.with(context)
                .load(speaker.avatar)
                .apply(options)
                .listener(GlideAnimationListener(image, getActivity()!!))
                .into(image)
    }

    class GlideAnimationListener(val image: ImageView, val activity: Activity) : RequestListener<Drawable> {

        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            ActivityCompat.startPostponedEnterTransition(activity)
            return false
        }

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            image.setImageDrawable(resource)
            ActivityCompat.startPostponedEnterTransition(activity)
            return false
        }


    }

}