package info.miguelcatalan.takemethere.search

import info.miguelcatalan.takemethere.R
import info.miguelcatalan.takemethere.base.BaseActivity

class SearchActivity : BaseActivity<SearchView, SearchPresenter>(), SearchView {

    override fun getLayoutRes(): Int {
        return R.layout.activity_search
    }

    override fun createPresenter(): SearchPresenter {
        return SearchPresenter()
    }

    override fun view(): SearchView {
        return this
    }

}