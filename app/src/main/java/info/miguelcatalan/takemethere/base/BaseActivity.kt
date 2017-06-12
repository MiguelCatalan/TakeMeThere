package info.miguelcatalan.takemethere.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<VIEW : BaseView, out PRESENTER : BasePresenter<VIEW>> : AppCompatActivity(), BaseView {

    private var presenter: PRESENTER? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        setupViews()
        injectDependencies()
        initializePresenter()
    }

    private fun initializePresenter() {
        if (presenter == null) {
            presenter = createPresenter()
            presenter!!.apply {
                setView(view())
                initialize()
            }
        }
    }

    abstract fun getLayoutRes(): Int

    open fun setupViews() {}

    open fun injectDependencies() {}

    open fun getPresenter(): PRESENTER {
        return presenter!!
    }

    abstract fun createPresenter(): PRESENTER

    abstract fun view(): VIEW

}