package com.example.demo1.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class MyHandlerExceptionResolve extends AbstractHandlerExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        try{
            if(e instanceof AccessDeniedException)
            {
                System.out.println("try-doResolveException()-MyHandlerExceptionResolve.class");
            }else {
                e.printStackTrace();
            }
        }
        catch (Exception ex)
        {
            System.out.println("catch-doResolveException()-MyHandlerExceptionResolve.class");
            ex.printStackTrace();
        }
        return new ModelAndView("/403");
    }
}
