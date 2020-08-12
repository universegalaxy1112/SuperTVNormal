package com.livetv.android.model;

import java.util.ArrayList;
import java.util.List;

public class LiveTVCategory extends BaseCategory {
    private List<LiveProgram> livePrograms = new ArrayList();
    private int position;
    private int totalChannels;

    public int getTotalChannels() {
        return this.totalChannels;
    }

    public void setTotalChannels(int totalChannels2) {
        this.totalChannels = totalChannels2;
    }

    public List<LiveProgram> getLivePrograms() {
        return this.livePrograms;
    }

    public void setLivePrograms(List<LiveProgram> livePrograms2) {
        this.livePrograms = livePrograms2;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position2) {
        this.position = position2;
    }
}
