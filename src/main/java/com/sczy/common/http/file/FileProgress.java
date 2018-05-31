package com.sczy.common.http.file;

import java.io.File;

import okhttp3.ResponseBody;

/**
 * @author SC16004984
 * @date 2018/4/13.
 */

public class FileProgress {
    private boolean status;
    private float inProgress;
    private long total;
    private boolean isComplete;
    private ResponseBody response;

    private File file;
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public float getInProgress() {
        return inProgress;
    }

    public void setInProgress(float inProgress) {
        this.inProgress = inProgress;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public ResponseBody getResponse() {
        return response;
    }

    public void setResponse(ResponseBody response) {
        this.response = response;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "FileProgress{" +
                "status=" + status +
                ", inProgress=" + inProgress +
                ", total=" + total +
                ", isComplete=" + isComplete +
                ", response=" + response +
                ", file=" + file +
                '}';
    }
}
