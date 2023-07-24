package by.alexandr7035.affinidi_id.core.extensions

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

fun ViewPager2.setupTabLayout(
    tabLayout: TabLayout,
    tabs: List<String>
) {
    TabLayoutMediator(tabLayout, this) { tab, position ->
        tab.text = tabs[position]
    }.attach()
}

fun ViewPager2.setupTabLayoutNoText(
    tabLayout: TabLayout
) {
    TabLayoutMediator(tabLayout, this) { _, _ ->
    }.attach()
}