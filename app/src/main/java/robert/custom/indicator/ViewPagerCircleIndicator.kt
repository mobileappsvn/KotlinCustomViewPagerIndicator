package robert.custom.indicator

import android.content.Context
import android.graphics.Color
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import java.util.*

/**
 * Created by Robert on 2018 May 03
 */
class ViewPagerCircleIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private var mItemColor = Color.WHITE
    private var mItemSelectedColor = Color.WHITE
    private var mPageCount: Int = 0
    private var mSelectedIndex: Int = 0
    private var mItemScale = NO_SCALE
    private var mItemSize = DEF_VALUE
    private var mDelimiterSize = DEF_VALUE
    private var mItemIcon = DEF_VALUE

    private val mIndexImages = ArrayList<ImageView>()
    private var mListener: ViewPager.OnPageChangeListener? = null

    init {
        orientation = LinearLayoutCompat.HORIZONTAL
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.ViewPagerCircleIndicator, 0, 0)
        try {
            mItemSize = attributes.getDimensionPixelSize(R.styleable.ViewPagerCircleIndicator_itemSize, DEF_VALUE)
            mItemScale = attributes.getFloat(R.styleable.ViewPagerCircleIndicator_itemScale, NO_SCALE)
            mItemSelectedColor = attributes.getColor(R.styleable.ViewPagerCircleIndicator_itemSelectedTint, Color.WHITE)
            mItemColor = attributes.getColor(R.styleable.ViewPagerCircleIndicator_itemTint, Color.WHITE)
            mDelimiterSize = attributes.getDimensionPixelSize(R.styleable.ViewPagerCircleIndicator_delimiterSize, DEF_VALUE)
            mItemIcon = attributes.getResourceId(R.styleable.ViewPagerCircleIndicator_itemIcon, DEF_ICON)
        } finally {
            attributes.recycle()
        }
        if (isInEditMode) {
            createEditModeLayout()
        }
    }

    private fun createEditModeLayout() {
        for (i in 0..4) {
            val boxedItem = createBoxedItem(i)
            addView(boxedItem)
            if (i == 1) {
                val item = boxedItem.getChildAt(0)
                val layoutParams = item.layoutParams
                layoutParams.height *= mItemScale.toInt()
                layoutParams.width *= mItemScale.toInt()
                item.layoutParams = layoutParams
            }
        }
    }

    fun setupWithViewPager(viewPager: ViewPager) {
        setPageCount(viewPager.adapter.count)
        viewPager.addOnPageChangeListener(OnPageChangeListener())
    }

    fun addOnPageChangeListener(listener: ViewPager.OnPageChangeListener) {
        mListener = listener
    }

    private fun setSelectedIndex(selectedIndex: Int) {
        if (selectedIndex < 0 || selectedIndex > mPageCount - 1) {
            return
        }

        val unselectedView = mIndexImages[mSelectedIndex]
        unselectedView.animate().scaleX(NO_SCALE).scaleY(NO_SCALE).setDuration(300).start()
        unselectedView.setColorFilter(mItemColor, android.graphics.PorterDuff.Mode.SRC_IN)

        val selectedView = mIndexImages[selectedIndex]
        selectedView.animate().scaleX(mItemScale).scaleY(mItemScale).setDuration(300).start()
        selectedView.setColorFilter(mItemSelectedColor, android.graphics.PorterDuff.Mode.SRC_IN)

        mSelectedIndex = selectedIndex
    }

    private fun setPageCount(pageCount: Int) {
        mPageCount = pageCount
        mSelectedIndex = 0
        removeAllViews()
        mIndexImages.clear()

        for (i in 0 until pageCount) {
            addView(createBoxedItem(i))
        }

        setSelectedIndex(mSelectedIndex)
    }

    private fun createBoxedItem(position: Int): FrameLayout {
        val box = FrameLayout(context)
        val item = createItem()
        box.addView(item)
        mIndexImages.add(item)

        val boxParams = LinearLayoutCompat.LayoutParams(
                (mItemSize * mItemScale).toInt(),
                (mItemSize * mItemScale).toInt()
        )
        if (position > 0) {
            boxParams.setMargins(mDelimiterSize, 0, 0, 0)
        }
        box.layoutParams = boxParams
        return box
    }

    private fun createItem(): ImageView {
        val index = ImageView(context)
        val indexParams = FrameLayout.LayoutParams(
                mItemSize,
                mItemSize
        )
        indexParams.gravity = Gravity.CENTER
        index.layoutParams = indexParams
        index.setImageResource(mItemIcon)
        index.scaleType = ImageView.ScaleType.FIT_CENTER
        index.setColorFilter(mItemColor, android.graphics.PorterDuff.Mode.SRC_IN)
        return index
    }

    private inner class OnPageChangeListener : ViewPager.OnPageChangeListener {

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            if (mListener != null) {
                mListener!!.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        }

        override fun onPageSelected(position: Int) {
            setSelectedIndex(position)
            if (mListener != null) {
                mListener!!.onPageSelected(position)
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            if (mListener != null) {
                mListener!!.onPageScrollStateChanged(state)
            }
        }
    }

    companion object {

        private val NO_SCALE = 1f
        private val DEF_VALUE = 10
        private val DEF_ICON = R.drawable.white_circle
    }
}