package org.dukecon.android.ui.features.info

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.chicagoroboto.features.info.InfoModule
import org.dukecon.android.ui.ext.getAppComponent
import org.dukecon.presentation.feature.info.InfoContract
import org.dukecon.presentation.model.LibraryView
import javax.inject.Inject

class InfoView(context: Context, attrs: AttributeSet? = null) : RecyclerView(context, attrs), InfoContract.View {

    @Inject
    lateinit var presenter: InfoContract.Presenter

    private val adapter: InfoAdapter

    init {
        context.getAppComponent().infoComponent(InfoModule(context)).inject(this)

        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        adapter = InfoAdapter({ library: LibraryView ->
            presenter.onLibraryClicked(library)
        })
        super.setAdapter(adapter)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.onAttach(this)
    }

    override fun onDetachedFromWindow() {
        presenter.onDetach()
        super.onDetachedFromWindow()
    }

    override fun showLibraries(libraries: List<LibraryView>) {
        adapter.setLibraries(libraries)
        adapter.notifyDataSetChanged()
    }
}