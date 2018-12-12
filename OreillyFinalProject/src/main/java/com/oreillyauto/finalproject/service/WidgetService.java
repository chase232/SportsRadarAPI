package com.oreillyauto.finalproject.service;

import java.util.List;

import com.oreillyauto.finalproject.domain.Widget;
import com.oreillyauto.finalproject.domain.WidgetProperty;

public interface WidgetService {

    List<Widget> getAllParentGames();

    List<WidgetProperty> getAllChildGames();

}