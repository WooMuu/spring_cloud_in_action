package com.zjb.specialroutes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by zjb on 2020/1/8.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoRouteFoundException extends RuntimeException {
}
