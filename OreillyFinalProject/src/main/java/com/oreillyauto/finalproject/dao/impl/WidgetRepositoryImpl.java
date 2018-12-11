package com.oreillyauto.finalproject.dao.impl;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.oreillyauto.finalproject.dao.custom.WidgetRepositoryCustom;
import com.oreillyauto.finalproject.domain.Widget;

@Repository
public class WidgetRepositoryImpl extends QuerydslRepositorySupport implements WidgetRepositoryCustom {
    
    public WidgetRepositoryImpl() {
        super(Widget.class);
    }
}










