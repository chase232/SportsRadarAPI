// Class:       WidgetRepositoryImpl
// Purpose:     Implements QueryDsl calls and database work

package com.oreillyauto.finalproject.dao.impl;

import java.math.BigInteger;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.oreillyauto.finalproject.dao.custom.WidgetRepositoryCustom;
import com.oreillyauto.finalproject.domain.QWidget;
import com.oreillyauto.finalproject.domain.Widget;

@Repository
public class WidgetRepositoryImpl extends QuerydslRepositorySupport implements WidgetRepositoryCustom {
    
    public WidgetRepositoryImpl() {
        super(Widget.class);
    }

}











