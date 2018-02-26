package org.dukecon.presentation

/**
 * Interface class to act as a base for any class that is to take the role of the IPresenter in the
 * Model-BaseView-IPresenter pattern.
 */
interface BasePresenter<T> {

    fun onAttach(view: T)
    fun onDetach()

}