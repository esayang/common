package com.sczy.common.http.file;

/**
 * @author SC16004984
 * @date 2018/1/23.
 */

public class FileProgressEvent {
    private long total;
    private long progress;
    public FileProgressEvent(long progress, long total) {
        this.total = total;
        this.progress = progress;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }
}
