package com.oreillyauto.finalproject.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.oreillyauto.finalproject.dao.custom.WidgetRepositoryCustom;
import com.oreillyauto.finalproject.domain.Widget;

public interface WidgetParentRepository extends CrudRepository<Widget, Integer>, WidgetRepositoryCustom {

    


}