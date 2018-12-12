package com.oreillyauto.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oreillyauto.finalproject.dao.WidgetChildRepository;
import com.oreillyauto.finalproject.dao.WidgetParentRepository;
import com.oreillyauto.finalproject.domain.Widget;
import com.oreillyauto.finalproject.domain.WidgetProperty;


@Service("widgetService")
public class WidgetServiceImpl implements WidgetService {

    @Autowired
    WidgetParentRepository widgetParentRepo;
    
    @Autowired
    WidgetChildRepository widgetChildRepo;

    @Override
    public List<Widget> getAllParentGames() {
        return (List<Widget>)widgetParentRepo.findAll();
    }
    
    @Override
    public List<WidgetProperty> getAllChildGames() {
        return (List<WidgetProperty>)widgetChildRepo.findAll();
    }

}
