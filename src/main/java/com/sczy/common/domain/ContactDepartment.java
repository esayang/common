package com.sczy.common.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.sczy.common.dao.DaoSession;
import com.sczy.common.dao.ContactDepartmentDao;

/**
 * @author SC16004984
 * @date 2018/5/29 0029.
 */
@Entity
public class ContactDepartment {
    /**
     * deptid : 7de6e086-32f9-44ad-bdfb-2ed0b38a6476
     * deptname : 二级市场统筹发展中心
     * parentid : null
     * deptcode : ZY.KD08.0008
     * fullname : null
     * mode : null
     * employeecount : 2
     * children : []
     */
    @Id
    private String deptid;
    private String deptname;
    private String parentid;
    private Long parentId;
    private String deptcode;
    private String fullname;
    private String mode;
    private int employeecount;

    @ToMany(referencedJoinProperty = "parentid")
    private List<ContactDepartment> children;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1002268160)
    private transient ContactDepartmentDao myDao;

    @Generated(hash = 174538445)
    public ContactDepartment(String deptid, String deptname, String parentid,
            Long parentId, String deptcode, String fullname, String mode,
            int employeecount) {
        this.deptid = deptid;
        this.deptname = deptname;
        this.parentid = parentid;
        this.parentId = parentId;
        this.deptcode = deptcode;
        this.fullname = fullname;
        this.mode = mode;
        this.employeecount = employeecount;
    }

    @Generated(hash = 1141538911)
    public ContactDepartment() {
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

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getEmployeecount() {
        return employeecount;
    }

    public void setEmployeecount(int employeecount) {
        this.employeecount = employeecount;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 648066500)
    public List<ContactDepartment> getChildren() {
        if (children == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContactDepartmentDao targetDao = daoSession.getContactDepartmentDao();
            List<ContactDepartment> childrenNew = targetDao
                    ._queryContactDepartment_Children(deptid);
            synchronized (this) {
                if (children == null) {
                    children = childrenNew;
                }
            }
        }
        return children;
    }

    @Override
    public String toString() {
        return "ContactDepartment{" +
                ", deptid='" + deptid + '\'' +
                ", deptname='" + deptname + '\'' +
                ", parentid='" + parentid + '\'' +
                ", parentId=" + parentId +
                ", deptcode='" + deptcode + '\'' +
                ", fullname='" + fullname + '\'' +
                ", mode='" + mode + '\'' +
                ", employeecount=" + employeecount +
                ", children=" + children +
                '}';
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1590975152)
    public synchronized void resetChildren() {
        children = null;
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
    @Generated(hash = 1331557164)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getContactDepartmentDao() : null;
    }


}
