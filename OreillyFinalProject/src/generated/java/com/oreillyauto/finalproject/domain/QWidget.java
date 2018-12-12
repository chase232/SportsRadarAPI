package com.oreillyauto.finalproject.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWidget is a Querydsl query type for Widget
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWidget extends EntityPathBase<Widget> {

    private static final long serialVersionUID = -1444088931L;

    public static final QWidget widget = new QWidget("widget");

    public final StringPath dateTime = createString("dateTime");

    public final NumberPath<java.math.BigInteger> eventID = createNumber("eventID", java.math.BigInteger.class);

    public final StringPath eventType = createString("eventType");

    public final StringPath smsSent = createString("smsSent");

    public final ListPath<WidgetProperty, QWidgetProperty> widgetPropertiesList = this.<WidgetProperty, QWidgetProperty>createList("widgetPropertiesList", WidgetProperty.class, QWidgetProperty.class, PathInits.DIRECT2);

    public QWidget(String variable) {
        super(Widget.class, forVariable(variable));
    }

    public QWidget(Path<? extends Widget> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWidget(PathMetadata metadata) {
        super(Widget.class, metadata);
    }

}

