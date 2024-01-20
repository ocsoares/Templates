package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces;

public interface IControllerWithArgument<R, P, E extends Exception> {
    R handle(P parameter) throws E;
}
