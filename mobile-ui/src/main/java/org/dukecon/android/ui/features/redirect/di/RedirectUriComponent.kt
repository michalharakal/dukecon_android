package org.dukecon.android.ui.features.eventdetail.di

import dagger.Subcomponent
import org.dukecon.android.ui.features.redirect.RedirectUriReceiverActivity

@Subcomponent(modules = arrayOf(RedirectUrilModule::class))
interface RedirectUriComponent {
    fun inject(redirectUriReceiverActivity: RedirectUriReceiverActivity)
}
