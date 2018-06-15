package com.smart.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    /**
     *执行HQL查询
     * @param sql
     * @return 查询结果
     */
    public List find(String sql){
        return this.getHibernateTemplate().find(sql);
    }

    /**
     * 执行带参的HQL查询
     * @param params
     * @return 查询结果
     */
    public List find(String sql,Object... params){
        return this.getHibernateTemplate().find(sql,params);
    }

    /**
     * 对延迟加载的PO实体执行初始化
     * @param entity
     */
     public void initialize(Object entity){
         this.getHibernateTemplate().initialize(entity);
     }



    /**
     * 分页查询函数，使用HQL
     * @param pageNo 页号，从1开始
     */
    public Page pagedQuery(String sql,int pageNo,int pageSize,Object... params){
        //get the total number
        String countQuerySql = "select count(1) "+removeSelect(sql);
        List countList = getHibernateTemplate().find(countQuerySql,params);
        long totalCount = (Long)countList.get(0);
        //get the start index
        int startIndex = Page.getStartOfPage(pageNo,pageSize);
        //get the specified pageNo data
        Query query =  createQuery(sql,params);
        List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

        return new Page(startIndex,totalCount,pageSize,list);
    }

    /**
     * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
     * 留意可以连续设置,如下：
     * <pre>
     * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
     * </pre>
     * 调用方式如下：
     * <pre>
     *        dao.createQuery(hql)
     *        dao.createQuery(hql,arg0);
     *        dao.createQuery(hql,arg0,arg1);
     *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
     * </pre>
     *
     * @param params 可变参数.
     */
    public Query createQuery(String sql,Object... params){
        Query query = getSession().createQuery(sql);
        for (int i = 0; i < params.length; i++) {
            ((Query) query).setParameter(i, params[i]);
        }

        return query;
    }


    /**
     * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
     *
     * @see #pagedQuery(String,int,int,Object[])
     */
    private static String removeSelect(String hql) {
        Assert.hasText(hql,"");
        int beginPos = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
        return hql.substring(beginPos);
    }

    /**
     * 去除hql的orderby 子句，用于pagedQuery.
     *
     * @see #pagedQuery(String,int,int,Object[])
     */
    private static String removeOrders(String hql) {
        Assert.hasText(hql,"");
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }
    /**
     * 保存PO实体
     * @param   entity
     */
    public void save(T entity){
        getHibernateTemplate().save(entity);
    }

    /**
     * 删除PO实体
     */
    public void remove(T entity){
        getHibernateTemplate().delete(entity);
    }

    /**
     * 删除Table数据
     */
    public void removeAll(String tableName){
        getSession().createSQLQuery("truncate TABLE " + tableName + "").executeUpdate();
    }

    /**
     * 修改PO实体
     */
    public void update(T entity){
        getHibernateTemplate().update(entity);
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    @Autowired
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public  Session getSession() {
        return hibernateTemplate.getSessionFactory().getCurrentSession();
    }
}

