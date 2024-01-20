package com.ocsoares.advancedcrudspringboot.application.usecases.interfaces;

public interface IUseCaseWithArgument<R, P, E extends Exception> {
    R execute(P parameter) throws E;
}
