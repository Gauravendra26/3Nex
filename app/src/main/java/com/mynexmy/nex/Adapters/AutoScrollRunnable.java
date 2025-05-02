package com.mynexmy.nex;

import android.os.Handler;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AutoScrollRunnable implements Runnable {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    public AutoScrollRunnable(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }

    @Override
    public void run() {
        if (layoutManager != null) {
            int currentPosition = layoutManager.findFirstVisibleItemPosition();
            int nextPosition = (currentPosition + 1) % recyclerView.getAdapter().getItemCount();
            recyclerView.smoothScrollToPosition(nextPosition);
            recyclerView.postDelayed(this, 2000); // Scroll every 2 seconds
        }
    }
}
