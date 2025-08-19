package project_2.aop;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import project_2.annotations.LogBE;
import project_2.controller.TaskController;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
@Slf4j
public class TaskControllerHandler implements InvocationHandler {

    private TaskController taskController;

    @Override
    @SneakyThrows
    public Object invoke(Object proxy, Method method, Object[] args) {
        if (method.isAnnotationPresent(LogBE.class)) {
            String message = method.getAnnotation(LogBE.class).message();
            log.info("Задача \"{}\" началась", message);
            Object invoke = method.invoke(taskController, args);
            log.info("Задача \"{}\" закончилась", message);
            return invoke;
        }
        return method.invoke(taskController, args);
    }

    public static TaskController createProxy(TaskController target, Class<TaskController> interfaceClass) {
        return (TaskController) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new TaskControllerHandler(target)
        );
    }

}
