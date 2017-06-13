package info.miguelcatalan.takemethere.base

import java.lang.ref.WeakReference

abstract class BasePresenter<VIEW : BaseView> {

    private lateinit var view: WeakReference<VIEW>

    fun setView(view: VIEW?) {
        this.view = WeakReference<VIEW>(view)
    }

    fun getView(): VIEW {
        return view.get()!!
    }

    abstract fun initialize()
}