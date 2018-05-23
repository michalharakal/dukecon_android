package org.dukecon.android.ui.features.info

import dagger.Subcomponent
import org.dukecon.android.ui.features.info.InfoModule
import org.dukecon.android.ui.features.info.InfoView

@Subcomponent(modules = arrayOf(InfoModule::class))
interface InfoComponent {
    fun inject(infoView: InfoView)
}