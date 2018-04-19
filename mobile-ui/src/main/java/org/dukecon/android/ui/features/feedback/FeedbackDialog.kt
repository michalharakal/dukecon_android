package com.chicagoroboto.features.sessiondetail.feedback

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.dialog_feedback.view.*
import org.dukecon.android.ui.R
import org.dukecon.android.ui.ext.getComponent
import org.dukecon.android.ui.features.eventdetail.di.EventDetailComponent
import org.dukecon.presentation.feature.feedback.FeedbackMvp
import javax.inject.Inject

class FeedbackDialog(context: Context, val sessionId: String) : Dialog(context, true, null), FeedbackMvp.View {

    @Inject lateinit var presenter: FeedbackMvp.Presenter

    lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context.getComponent<EventDetailComponent>().feedbackComponent().inject(this)
        presenter.setSessionId(sessionId)

        view = LayoutInflater.from(context).inflate(R.layout.dialog_feedback, null, false)
        view.submit.setOnClickListener {
            presenter.submit(1) //view.overall.rating)
        }

        view.cancel.setOnClickListener {
            cancel()
        }

        setContentView(view)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.onAttach(this)
    }

    override fun onDetachedFromWindow() {
        presenter.onDetach()
        super.onDetachedFromWindow()
    }

}