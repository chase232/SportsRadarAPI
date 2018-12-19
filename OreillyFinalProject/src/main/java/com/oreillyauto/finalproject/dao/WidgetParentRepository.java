// Interface: WidgetParentRepository
// Purpose:   Uses spring data to perform desired functions

package com.oreillyauto.finalproject.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.oreillyauto.finalproject.dao.custom.WidgetRepositoryCustom;
import com.oreillyauto.finalproject.domain.Widget;

public interface WidgetParentRepository extends CrudRepository<Widget, Integer>, WidgetRepositoryCustom {

    List<Widget> findByDateTime(String newDate);
    Widget findByEventID(BigInteger bigInt);
    List<Widget> findBySmsSent(String yes);  
}