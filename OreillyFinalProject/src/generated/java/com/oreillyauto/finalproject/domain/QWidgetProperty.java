package com.oreillyauto.finalproject.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWidgetProperty is a Querydsl query type for WidgetProperty
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWidgetProperty extends EntityPathBase<WidgetProperty> {

    private static final long serialVersionUID = -1444263790L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWidgetProperty widgetProperty = new QWidgetProperty("widgetProperty");

    public final StringPath eventID = createString("eventID");

    public final StringPath eventKey = createString("eventKey");

    public final StringPath eventValue = createString("eventValue");

    public final StringPath propertyID = createString("propertyID");

    public final QWidget widget;

    public QWidgetProperty(String variable) {
        this(WidgetProperty.class, forVariable(variable), INITS);
    }

    public QWidgetProperty(Path<? extends WidgetProperty> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWidgetProperty(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWidgetProperty(PathMetadata metadata, PathInits inits) {
        this(WidgetProperty.class, metadata, inits);
    }

    public QWidgetProperty(Class<? extends WidgetProperty> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.widget = inits.isInitialized("widget") ? new QWidget(forProperty("widget")) : null;
    }

}

