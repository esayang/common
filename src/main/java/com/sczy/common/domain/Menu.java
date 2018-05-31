package com.sczy.common.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sczy.common.dao.util.StringConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author SC16004984
 * @date 2018/5/24 0024.
 */
@Entity
public class Menu implements Parcelable{
    /**
     * rowid : 菜单id
     * title : 名称
     * sortnum : 排序号
     * type : 菜单类别
     * icon : 图标名称
     * iconpatch : 图标的地址
     * sharepatch : 分享地址
     * workcount : 角标数量
     * apipatch
     */

    @Id
    private Long id;
    @SerializedName(value = "rowid")
    private String rowId;
    private String title;
    @SerializedName(value = "sortnum")
    private String sortNum;
    private String type;
    private String icon;
    @SerializedName(value = "iconpatch")
    private String iconPatch;
    @SerializedName(value = "sharepatch")
    private String sharePatch;
    @SerializedName(value = "workcount")
    private String workCount;
    @SerializedName(value = "androidkey")
    private String androidKey;
    @SerializedName(value = "androidpatch")
    private String androidPatch;
    private String remark;
    @Convert(columnType = String.class, converter = StringConverter.class)
    @SerializedName(value = "apipatch")
    private List<String> apiPatch;
    @SerializedName(value = "menustate") //100：正常、-1：未开放、1：测试
    private int menuState;
    private String classic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconPatch() {
        return iconPatch;
    }

    public void setIconPatch(String iconPatch) {
        this.iconPatch = iconPatch;
    }

    public String getSharePatch() {
        return sharePatch;
    }

    public void setSharePatch(String sharePatch) {
        this.sharePatch = sharePatch;
    }

    public String getWorkCount() {
        return workCount;
    }

    public void setWorkCount(String workCount) {
        this.workCount = workCount;
    }

    public String getAndroidKey() {
        return androidKey;
    }

    public void setAndroidKey(String androidKey) {
        this.androidKey = androidKey;
    }

    public String getAndroidPatch() {
        return androidPatch;
    }

    public void setAndroidPatch(String androidPatch) {
        this.androidPatch = androidPatch;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getApiPatch() {
        return apiPatch;
    }

    public void setApiPatch(List<String> apiPatch) {
        this.apiPatch = apiPatch;
    }

    public int getMenuState() {
        return menuState;
    }

    public void setMenuState(int menuState) {
        this.menuState = menuState;
    }

    public String getClassic() {
        return classic;
    }

    public void setClassic(String classic) {
        this.classic = classic;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.rowId);
        dest.writeString(this.title);
        dest.writeString(this.sortNum);
        dest.writeString(this.type);
        dest.writeString(this.icon);
        dest.writeString(this.iconPatch);
        dest.writeString(this.sharePatch);
        dest.writeString(this.workCount);
        dest.writeString(this.androidKey);
        dest.writeString(this.androidPatch);
        dest.writeString(this.remark);
        dest.writeStringList(this.apiPatch);
        dest.writeInt(this.menuState);
        dest.writeString(this.classic);
    }

    public Menu() {
    }

    protected Menu(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.rowId = in.readString();
        this.title = in.readString();
        this.sortNum = in.readString();
        this.type = in.readString();
        this.icon = in.readString();
        this.iconPatch = in.readString();
        this.sharePatch = in.readString();
        this.workCount = in.readString();
        this.androidKey = in.readString();
        this.androidPatch = in.readString();
        this.remark = in.readString();
        this.apiPatch = in.createStringArrayList();
        this.menuState = in.readInt();
        this.classic = in.readString();
    }

    @Generated(hash = 1299467684)
    public Menu(Long id, String rowId, String title, String sortNum, String type,
            String icon, String iconPatch, String sharePatch, String workCount,
            String androidKey, String androidPatch, String remark,
            List<String> apiPatch, int menuState, String classic) {
        this.id = id;
        this.rowId = rowId;
        this.title = title;
        this.sortNum = sortNum;
        this.type = type;
        this.icon = icon;
        this.iconPatch = iconPatch;
        this.sharePatch = sharePatch;
        this.workCount = workCount;
        this.androidKey = androidKey;
        this.androidPatch = androidPatch;
        this.remark = remark;
        this.apiPatch = apiPatch;
        this.menuState = menuState;
        this.classic = classic;
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel source) {
            return new Menu(source);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };
}
