package com.livetv.android.listeners;

public interface DownloaderListener {
    void onDownloadComplete(String str);

    void onDownloadError(int i);
}
