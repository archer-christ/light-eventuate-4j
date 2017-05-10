package com.networknt.eventuate.event;

import com.networknt.eventuate.common.Event;
import com.networknt.eventuate.common.EventHandlerMethod;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * General utility methods for EventHandler Processor
 *
 */
public class EventHandlerProcessorUtil {

  static boolean isVoidMethodWithOneParameterOfType(Method method, Class<?> parameterClass) {
    return method.getAnnotation(EventHandlerMethod.class) != null &&
            method.getParameterCount() == 1 &&
            parameterClass.isAssignableFrom(method.getParameterTypes()[0]) &&
            (method.getReturnType() == void.class || method.getReturnType() == Void.class);
  }

  static boolean isMethodWithOneParameterOfTypeReturning(Method method, Class<?> parameterClass, Class<?> resultType) {
    return method.getAnnotation(EventHandlerMethod.class) != null &&
            method.getParameterCount() == 1 &&
            parameterClass.isAssignableFrom(method.getParameterTypes()[0]) &&
            method.getReturnType() == resultType;
  }

  public static Class<Event> getEventClass(Method method) {
    return (Class<Event>) ((ParameterizedType) method.getGenericParameterTypes()[0]).getActualTypeArguments()[0];
  }
}
