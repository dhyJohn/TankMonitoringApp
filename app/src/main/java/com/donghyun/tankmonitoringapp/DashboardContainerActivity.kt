package com.donghyun.tankmonitoringapp

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.donghyun.tankmonitoringapp.adapter.TankAdapter
import com.example.tankmonitoring.HistoryFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DashboardContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_container)

        // ① Toolbar 설정
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        // 타이틀은 XML에 app:title로 지정했기 때문에 여기서 생략 가능



        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabTitles = listOf("실시간업데이트", "이력조회")
        val tabIcons = listOf(R.drawable.monitoring, R.drawable.file)
        val tabIconColors = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_selected), intArrayOf(-android.R.attr.state_selected)),
            intArrayOf(
                ContextCompat.getColor(this, R.color.blue), //selected icon color
                ContextCompat.getColor(this, R.color.gray)
            )
        )
        tabLayout.tabIconTint = tabIconColors
        // **TabLayout과 ViewPager2 연결**
        viewPager.adapter = MyAdapter(this)
        viewPager.offscreenPageLimit = tabTitles.size
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
            tab.setIcon(tabIcons[position])
        }.attach()



    }

    class MyAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        val fragments: List<Fragment>

        init {
            fragments = listOf(DashboardFragment(), HistoryFragment())
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }


    }
}
