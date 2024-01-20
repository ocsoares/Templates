package com.ocsoares.advancedcrudspringboot.application.usecases.interfaces;

public interface IUseCaseWithTwoArguments<R, P, A, E extends Exception> {
    R execute(P parameter, A secondParameter) throws E;
}
