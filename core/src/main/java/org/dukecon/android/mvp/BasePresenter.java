package org.dukecon.android.mvp;

public interface BasePresenter<T extends BaseView> {

    void onAttach();

    void onDetach();
}
