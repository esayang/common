package com.sczy.common.domain;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by SC16004984 on 2016/10/25.
 */

@Entity
public class UserDetail implements Parcelable {

    @Id
    private Long id;
    private Long userId;
    private String empid;
    private String empno;
    private String emptname;
    private String deptid;
    private String deptname;
    private String deptfullname;
    private String deptcode;
    private String samaccountname;
    private String email;
    private String mobile;
    private String postalcode;
    private String sex;
    private String positionname;

    protected UserDetail(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readLong();
        }
        empid = in.readString();
        empno = in.readString();
        emptname = in.readString();
        deptid = in.readString();
        deptname = in.readString();
        deptfullname = in.readString();
        deptcode = in.readString();
        samaccountname = in.readString();
        email = in.readString();
        mobile = in.readString();
        postalcode = in.readString();
        sex = in.readString();
        positionname = in.readString();
    }

    @Generated(hash = 1294845174)
    public UserDetail(Long id, Long userId, String empid, String empno,
            String emptname, String deptid, String deptname, String deptfullname,
            String deptcode, String samaccountname, String email, String mobile,
            String postalcode, String sex, String positionname) {
        this.id = id;
        this.userId = userId;
        this.empid = empid;
        this.empno = empno;
        this.emptname = emptname;
        this.deptid = deptid;
        this.deptname = deptname;
        this.deptfullname = deptfullname;
        this.deptcode = deptcode;
        this.samaccountname = samaccountname;
        this.email = email;
        this.mobile = mobile;
        this.postalcode = postalcode;
        this.sex = sex;
        this.positionname = positionname;
    }

    @Generated(hash = 1767819458)
    public UserDetail() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        if (userId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(userId);
        }
        dest.writeString(empid);
        dest.writeString(empno);
        dest.writeString(emptname);
        dest.writeString(deptid);
        dest.writeString(deptname);
        dest.writeString(deptfullname);
        dest.writeString(deptcode);
        dest.writeString(samaccountname);
        dest.writeString(email);
        dest.writeString(mobile);
        dest.writeString(postalcode);
        dest.writeString(sex);
        dest.writeString(positionname);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserDetail> CREATOR = new Creator<UserDetail>() {
        @Override
        public UserDetail createFromParcel(Parcel in) {
            return new UserDetail(in);
        }

        @Override
        public UserDetail[] newArray(int size) {
            return new UserDetail[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getEmpno() {
        return empno;
    }

    public void setEmpno(String empno) {
        this.empno = empno;
    }

    public String getEmptname() {
        return emptname;
    }

    public void setEmptname(String emptname) {
        this.emptname = emptname;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getDeptfullname() {
        return deptfullname;
    }

    public void setDeptfullname(String deptfullname) {
        this.deptfullname = deptfullname;
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    public String getSamaccountname() {
        return samaccountname;
    }

    public void setSamaccountname(String samaccountname) {
        this.samaccountname = samaccountname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPositionname() {
        return positionname;
    }

    public void setPositionname(String positionname) {
        this.positionname = positionname;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "id=" + id +
                ", userId=" + userId +
                ", empid='" + empid + '\'' +
                ", empno='" + empno + '\'' +
                ", emptname='" + emptname + '\'' +
                ", deptid='" + deptid + '\'' +
                ", deptname='" + deptname + '\'' +
                ", deptfullname='" + deptfullname + '\'' +
                ", deptcode='" + deptcode + '\'' +
                ", samaccountname='" + samaccountname + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", postalcode='" + postalcode + '\'' +
                ", sex='" + sex + '\'' +
                ", positionname='" + positionname + '\'' +
                '}';
    }
}
