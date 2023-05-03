package woowacourse.movie.contract

import androidx.fragment.app.Fragment

interface MainContract {
    interface View {
        val presenter: Presenter
        fun changeFragmentByItemID(itemId: Int)
        fun replaceFragment(fragment: Fragment)
        fun setSelectedFragmentView(itemId: Int)
        fun getIntentNavigationItemId(): Int
    }

    interface Presenter {
        fun onClickBottomNavigationItem(itemId: Int): Boolean
        fun updateFragmentView()
    }
}