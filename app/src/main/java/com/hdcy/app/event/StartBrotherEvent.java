package com.hdcy.app.event;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by WeiYanGeorge on 2016-08-18.
 */

public class StartBrotherEvent {
    public SupportFragment targetFragment;

    public StartBrotherEvent(SupportFragment targetFragment) {
        this.targetFragment = targetFragment;
    }
}
