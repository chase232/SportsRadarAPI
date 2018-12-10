package com.oreillyauto.widgetmanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oreillyauto.widgetmanager.dao.WidgetRepository;
import com.oreillyauto.widgetmanager.domain.Widget;
import com.oreillyauto.widgetmanager.service.WidgetService;

@Service("widgetService")
public class WidgetServiceImpl implements WidgetService {

    @Autowired
    WidgetRepository widgetRepo;

    @Override
    public Widget getWidgetById(Integer id) {
        return widgetRepo.getWidgetById(id);
    }

    @Override
    public List<Widget> getAllSoda() {
        return widgetRepo.getAllSoda();
    }

    @Override
    public void saveSoda(Widget widget) {
        widgetRepo.save(widget);
        
    }

    @Override
    public Widget getWidgetByName(String name) {
        return widgetRepo.getWidgetByName(name);
    }

    @Override
    public void deleteWidgetByWidgetId(Widget widget) {
        widgetRepo.delete(widget);
        
    }

    @Override
    public List<Widget> getSortedSoda() {
        return widgetRepo.getSortedSoda();
    }

    @Override
    public List<Widget> getAllSodaForAjax() {
        return (List<Widget>)widgetRepo.findAll();
    }

}
