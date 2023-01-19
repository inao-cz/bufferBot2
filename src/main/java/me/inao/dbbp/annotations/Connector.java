package me.inao.dbbp.annotations;

import me.inao.dbbp.enums.ConnectorType;

public @interface Connector {
    ConnectorType type() default ConnectorType.HTTP;
    String name();

}
