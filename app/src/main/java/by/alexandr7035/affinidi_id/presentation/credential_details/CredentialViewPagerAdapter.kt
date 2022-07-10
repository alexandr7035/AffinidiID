package by.alexandr7035.affinidi_id.presentation.credential_details

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.alexandr7035.affinidi_id.presentation.credential_details.claims.CredentialClaimsFragment
import by.alexandr7035.affinidi_id.presentation.credential_details.more_info.CredentialProofFragment
import java.lang.IllegalStateException

class CredentialViewPagerAdapter(
    private val tabsCount: Int,
    private val parentFragment: Fragment
): FragmentStateAdapter(parentFragment) {
    override fun getItemCount(): Int {
        return tabsCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                CredentialClaimsFragment()
            }

            1 -> {
                CredentialProofFragment()
            }

            else -> throw IllegalStateException("Too many pages, some not implemented. Check viewpager & adapter")
        }
    }

}