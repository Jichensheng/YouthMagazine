package com.jcs.magazine.widget.viewpagerindicator;

public interface IconPagerAdapter {
    /**
     * Get icon representing the page at {@code index} in the adapter.
     */
    int getIconResId(int index);

    // From YzuPagerAdapter
    int getCount();
}
