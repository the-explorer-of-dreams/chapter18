package com.smart.service;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.unitils.UnitilsTestNG;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

@SpringApplicationContext({"xiaochun-dao.xml","xiaochun-service.xml"})
public class BaseServiceTest extends UnitilsTestNG {
    @SpringBean("hibernateTemplate")
    public HibernateTemplate hibernateTemplate;
}
