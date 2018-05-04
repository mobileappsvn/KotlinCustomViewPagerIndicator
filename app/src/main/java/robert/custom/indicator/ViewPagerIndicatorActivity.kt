package robert.custom.indicator

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast


/**
 * Created by Robert on 2018 May 03
 */
class ViewPagerIndicatorActivity : AppCompatActivity() {

    private var mViewPagerIndicator: ViewPagerCircleIndicator? = null
    private var mViewPager: ViewPager? = null

    private val mOnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            Toast.makeText(this@ViewPagerIndicatorActivity, "Page selected " + (position + 1), Toast.LENGTH_SHORT).show()
        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_pager_indicator_activity)

        mViewPagerIndicator = findViewById<View>(R.id.view_pager_indicator) as ViewPagerCircleIndicator
        mViewPager = findViewById(R.id.view_pager)

        mViewPager!!.adapter = MyPagerAdapter()
        mViewPager!!.currentItem = 0

        mViewPagerIndicator!!.setupWithViewPager(mViewPager!!)
        mViewPagerIndicator!!.addOnPageChangeListener(mOnPageChangeListener)
    }

    private inner class MyPagerAdapter : PagerAdapter() {
        override fun getCount(): Int {
            return 5
        }

        @SuppressLint("SetTextI18n")
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val textView = TextView(this@ViewPagerIndicatorActivity)
            textView.text = "Page " + (position + 1)
            textView.setTextColor(Color.WHITE)
            textView.typeface = Typeface.DEFAULT_BOLD
            textView.textSize = 20 * resources.displayMetrics.density
            textView.setPadding(20, 20, 20, 20)

            val layout = LinearLayout(this@ViewPagerIndicatorActivity)
            layout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT)
            layout.gravity = Gravity.CENTER
            layout.addView(textView)
            container.addView(layout)

            return layout
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return position.toString()
        }
    }
}

