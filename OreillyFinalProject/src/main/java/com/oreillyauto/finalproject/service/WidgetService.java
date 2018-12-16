package com.oreillyauto.finalproject.service;

import java.util.List;

import com.oreillyauto.finalproject.domain.Widget;
import com.oreillyauto.finalproject.domain.WidgetProperty;
import com.oreillyauto.finalproject.dto.Text;

public interface WidgetService {

    List<Widget> getAllParentGames();

    List<WidgetProperty> getAllChildGames();

    String sendText(Text text);

    List<Widget> getGameByDate(String newDate);


}