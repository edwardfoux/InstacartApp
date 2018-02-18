package com.example.edwardfoux.instacartquiz.quiz;

import com.jakewharton.rxrelay2.PublishRelay;

class BroadcastRelay {
    private PublishRelay<Long> relay = PublishRelay.create();

    private static BroadcastRelay INSTANCE;

    static BroadcastRelay getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BroadcastRelay();
        }
        return INSTANCE;
    }

    void onBroadcastUpdate(Long value) {
        relay.accept(value);
    }

    PublishRelay<Long> getRelay() {
        return relay;
    }
}
