package com.livetv.android.model;

public class LiveProgram extends VideoStream {
    private String epg_ahora;
    private String epg_despues;
    private String iconUrl;

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String iconUrl2) {
        this.iconUrl = iconUrl2;
    }

    public String getEpg_ahora() {
        return this.epg_ahora;
    }

    public void setEpg_ahora(String epg_ahora2) {
        this.epg_ahora = epg_ahora2;
    }

    public String getEpg_despues() {
        return this.epg_despues;
    }

    public void setEpg_despues(String epg_despues2) {
        this.epg_despues = epg_despues2;
    }
}
