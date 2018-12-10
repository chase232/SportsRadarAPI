package com.oreillyauto.finalproject.dao;

import org.springframework.data.repository.CrudRepository;

import com.oreillyauto.finalproject.dao.custom.WidgetRepositoryCustom;
import com.oreillyauto.finalproject.domain.Widget;

public interface WidgetRepository extends CrudRepository<Widget, Integer>, WidgetRepositoryCustom {


}