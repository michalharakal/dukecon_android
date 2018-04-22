package org.dukecon.android.ui.features.feedback

import com.chicagoroboto.features.sessiondetail.feedback.FeedbackDialog
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(FeedbackModule::class))
interface FeedbackComponent {
    fun inject(feedbackDialog: FeedbackDialog)
}