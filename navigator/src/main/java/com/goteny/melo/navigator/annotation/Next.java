package com.goteny.melo.navigator.annotation;

import com.goteny.melo.navigator.PageListener;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Inherited
@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Next
{

    Class<? extends PageListener> value();
}
