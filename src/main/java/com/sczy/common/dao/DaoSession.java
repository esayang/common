package com.sczy.common.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.sczy.common.domain.User;
import com.sczy.common.domain.UserDetail;
import com.sczy.common.domain.OfflineUser;
import com.sczy.common.domain.Menu;
import com.sczy.common.domain.ContactDepartment;

import com.sczy.common.dao.UserDao;
import com.sczy.common.dao.UserDetailDao;
import com.sczy.common.dao.OfflineUserDao;
import com.sczy.common.dao.MenuDao;
import com.sczy.common.dao.ContactDepartmentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig userDetailDaoConfig;
    private final DaoConfig offlineUserDaoConfig;
    private final DaoConfig menuDaoConfig;
    private final DaoConfig contactDepartmentDaoConfig;

    private final UserDao userDao;
    private final UserDetailDao userDetailDao;
    private final OfflineUserDao offlineUserDao;
    private final MenuDao menuDao;
    private final ContactDepartmentDao contactDepartmentDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        userDetailDaoConfig = daoConfigMap.get(UserDetailDao.class).clone();
        userDetailDaoConfig.initIdentityScope(type);

        offlineUserDaoConfig = daoConfigMap.get(OfflineUserDao.class).clone();
        offlineUserDaoConfig.initIdentityScope(type);

        menuDaoConfig = daoConfigMap.get(MenuDao.class).clone();
        menuDaoConfig.initIdentityScope(type);

        contactDepartmentDaoConfig = daoConfigMap.get(ContactDepartmentDao.class).clone();
        contactDepartmentDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        userDetailDao = new UserDetailDao(userDetailDaoConfig, this);
        offlineUserDao = new OfflineUserDao(offlineUserDaoConfig, this);
        menuDao = new MenuDao(menuDaoConfig, this);
        contactDepartmentDao = new ContactDepartmentDao(contactDepartmentDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(UserDetail.class, userDetailDao);
        registerDao(OfflineUser.class, offlineUserDao);
        registerDao(Menu.class, menuDao);
        registerDao(ContactDepartment.class, contactDepartmentDao);
    }
    
    public void clear() {
        userDaoConfig.clearIdentityScope();
        userDetailDaoConfig.clearIdentityScope();
        offlineUserDaoConfig.clearIdentityScope();
        menuDaoConfig.clearIdentityScope();
        contactDepartmentDaoConfig.clearIdentityScope();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public UserDetailDao getUserDetailDao() {
        return userDetailDao;
    }

    public OfflineUserDao getOfflineUserDao() {
        return offlineUserDao;
    }

    public MenuDao getMenuDao() {
        return menuDao;
    }

    public ContactDepartmentDao getContactDepartmentDao() {
        return contactDepartmentDao;
    }

}
