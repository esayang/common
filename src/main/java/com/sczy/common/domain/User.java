package com.sczy.common.domain;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import com.sczy.common.dao.DaoSession;
import com.sczy.common.dao.UserDetailDao;
import com.sczy.common.dao.UserDao;

/**
 * Created by SC16004984 on 2016/10/12.
 */
@Entity
public class User implements Parcelable {
    /**
     * emptcode : SC16004984
     * emptname : **
     * remarks :
     * rolecity : 重庆
     * emptcity : 重庆
     * sessionid : 7299333a980a4f1bac23318515f34aff
     * menu_details : [{"menuid":"b68d42b6-cb3d-4a9b-93dc-69067a2e77a9","menuname":"业绩快报","menupatch":"cq_yjkb"},{"menuid":"a29cf604-2db1-468a-a186-0e4d7985829a","menuname":"CBER","menupatch":"cq_cber"}]
     * empt_details : [{"empid":"375d3e1b-51c6-4f29-97b4-4fc8acc6a5b4","empno":"SC16004984","emptname":"**","deptid":"fbf082ed-0221-4334-bff4-ed41e739cc4b","deptname":"软件开发部","deptfullname":"华西区/华西区信息中心/软件开发部","deptcode":"SC.KD01.0002.0002","samaccountname":"SC16004984","email":"SC16004984@centaline.com.cn","mobile":"","postalcode":"","sex":"男","positionname":"软件工程师","isfavorite":true}]
     */
    @Id(autoincrement = true)
    private Long id;
    private String emptcode;
    private String emptname;
    private String remarks;
    private String rolecity;
    private String emptcity;
    private String sessionid;
    @ToMany(joinProperties = {@JoinProperty(name = "id", referencedName = "userId")})
    private List<UserDetail> empt_details;

    protected User(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        emptcode = in.readString();
        emptname = in.readString();
        remarks = in.readString();
        rolecity = in.readString();
        emptcity = in.readString();
        sessionid = in.readString();
        empt_details = in.createTypedArrayList(UserDetail.CREATOR);
    }

    @Generated(hash = 1562197576)
    public User(Long id, String emptcode, String emptname, String remarks, String rolecity, String emptcity, String sessionid) {
        this.id = id;
        this.emptcode = emptcode;
        this.emptname = emptname;
        this.remarks = remarks;
        this.rolecity = rolecity;
        this.emptcity = emptcity;
        this.sessionid = sessionid;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(emptcode);
        dest.writeString(emptname);
        dest.writeString(remarks);
        dest.writeString(rolecity);
        dest.writeString(emptcity);
        dest.writeString(sessionid);
        dest.writeTypedList(empt_details);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmptcode() {
        return emptcode;
    }

    public void setEmptcode(String emptcode) {
        this.emptcode = emptcode;
    }

    public String getEmptname() {
        return emptname;
    }

    public void setEmptname(String emptname) {
        this.emptname = emptname;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRolecity() {
        return rolecity;
    }

    public void setRolecity(String rolecity) {
        this.rolecity = rolecity;
    }

    public String getEmptcity() {
        return emptcity;
    }

    public void setEmptcity(String emptcity) {
        this.emptcity = emptcity;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 595528003)
    public List<UserDetail> getEmpt_details() {
        if (empt_details == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDetailDao targetDao = daoSession.getUserDetailDao();
            List<UserDetail> empt_detailsNew = targetDao._queryUser_Empt_details(id);
            synchronized (this) {
                if (empt_details == null) {
                    empt_details = empt_detailsNew;
                }
            }
        }
        return empt_details;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 401948702)
    public synchronized void resetEmpt_details() {
        empt_details = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", emptcode='" + emptcode + '\'' +
                ", emptname='" + emptname + '\'' +
                ", remarks='" + remarks + '\'' +
                ", rolecity='" + rolecity + '\'' +
                ", emptcity='" + emptcity + '\'' +
                ", sessionid='" + sessionid + '\'' +
                ", empt_details=" + empt_details +
                '}';
    }
}
