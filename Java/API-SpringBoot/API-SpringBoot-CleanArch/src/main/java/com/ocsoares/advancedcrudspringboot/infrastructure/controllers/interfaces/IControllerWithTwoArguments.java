package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces;

public interface IControllerWithTwoArguments<R, P, A, E extends Exception> {
    R handle(P parameter, A secondParameter) throws E;
}
