package com.goteny.melo.navigator.annotation;

import com.goteny.melo.navigator.PageListener;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

@Inherited
@Target({TYPE, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageAnno
{

    Class<? extends PageListener> value();
}
