package by.alexandr7035.affinidi_id.presentation.common

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BasePagerAdapter(
    private val fragments: List<Class<out Fragment>>,
    parentFragment: Fragment
): FragmentStateAdapter(parentFragment) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position].newInstance()
    }
}