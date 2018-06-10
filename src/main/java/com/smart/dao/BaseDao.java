package com.smart.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


public class BaseDao<T> {
    private Class<T> entityClass;
    private HibernateTemplate hibernateTemplate;

    /*
     * 通过反射获得泛型类参数类型
     */
    public BaseDao() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }


    //查询PO
    /**
     * 根据ID加载PO实例
     */
    public T load(Serializable id){
        return (T)getHibernateTemplate().load(entityClass,id);
    }


    /**
     * 根据ID获取PO实例
     */
    public T get(Serializable id){
        return (T) getHibernateTemplate().get(entityClass, id);
    }

    /**
     * 加载PO所有实例
     */
    public List<T> loadAll(){
        return getHibernateTemplate().loadAll(entityClass);
    }






    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }
}

