package org.dukecon.presentation.feature.info

import io.reactivex.observers.DisposableSingleObserver
import org.dukecon.domain.features.info.GetLibrariesUseCase
import org.dukecon.domain.model.Library
import org.dukecon.presentation.mapper.LibraryMapper
import org.dukecon.presentation.model.LibraryView
import javax.inject.Inject

class InfoPresenter @Inject constructor(
        val getLibrariesUseCase: GetLibrariesUseCase,
        val libraryMapper: LibraryMapper,
        val webNavigator: WebNavigator) : InfoContract.Presenter {


    private var view: InfoContract.View? = null

    override fun onAttach(view: InfoContract.View) {
        this.view = view
        getLibrariesUseCase.execute(LibrariesSubscriber())

    }

    override fun onDetach() {
        this.view = null
    }

    override fun onLibraryClicked(library: LibraryView) {
        webNavigator.navigateToUrl(library.url)
    }

    private fun handleGetLibrariesSuccess(libraries: List<Library>) {
        view?.showLibraries(libraries.map { libraryMapper.mapToView(it) })
    }


    inner class LibrariesSubscriber : DisposableSingleObserver<List<Library>>() {

        override fun onSuccess(t: List<Library>) {
            handleGetLibrariesSuccess(t)
        }

        override fun onError(exception: Throwable) {
        }
    }

}

