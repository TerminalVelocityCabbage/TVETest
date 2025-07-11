package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.engine.scheduler.Scheduler;
import com.terminalvelocitycabbage.engine.scheduler.Task;
import com.terminalvelocitycabbage.engine.scheduler.TaskBuilder;
import com.terminalvelocitycabbage.engine.util.TickManager;

import java.util.concurrent.TimeUnit;

import static com.terminalvelocitycabbage.game.client.GameClient.ID;

public class SchedulerTest {

    public static void main(String[] args) {

        //Create scheduler
        Scheduler scheduler = new Scheduler();

        //Create a task and add it to the scheduler
        Task printHelloWorldTask = TaskBuilder
                .builder()
                .identifier(ID, "hello-world-task")
                .executes(taskContext -> {
                    System.out.println("Hello World from: " + taskContext.task().identifier().toString());
                })
                .build();

        //Create a repeating task and add it to the scheduler
        Task printFooTask = TaskBuilder
                .builder()
                .identifier(ID, "print-foo-task")
                .executes(taskContext -> {
                    System.out.println("Foo: " + taskContext.task().identifier().toString());
                })
                .repeat(1, TimeUnit.SECONDS)
                .build();

        //Create a task depending on the return value of another task
        Task dependentTask = TaskBuilder
                .builder()
                .identifier(ID, "then-task")
                .executes(taskContext -> {
                    if (taskContext.hasPrevious()) {
                        Integer thing = (Integer) taskContext.previousReturn();
                        System.out.println("THEEENNNNN " + thing);
                    } else {
                        System.out.println("NO PREVIOUS VALUE");
                    }
                }).build();

        //Create a delayed task with a return value and add it to the scheduler
        Task printBarTask = TaskBuilder
                .builder()
                .identifier(ID, "print-bar-task")
                .executes(taskContext -> {
                    System.out.println("Bar Bar Bar: " + taskContext.task().identifier().toString());
                    taskContext.setReturn(15);
                })
                .delay(3, TimeUnit.SECONDS)
                .then(dependentTask) //After this task completes run this task
                .build();

        //Create an async task and add it to the scheduler
        Task delayPrintTask = TaskBuilder
                .builder()
                .identifier(ID, "async-task")
                .executes(taskContext -> {
                    try {
                        Thread.sleep(10000);
                        taskContext.setReturn(42);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Async: " + taskContext.task().identifier().toString());
                })
                .async()
                .then(dependentTask) //Run a dependent task after an async task
                .build();

        //Schedule the above tasks
        scheduler.scheduleTask(printHelloWorldTask);
        scheduler.scheduleTask(printFooTask);
        scheduler.scheduleTask(printBarTask);
        scheduler.scheduleTask(delayPrintTask);

        //Run a tick once per second
        TickManager tickManager = new TickManager(10);
        //Test the scheduler on a tick-by-tick basis and track time
        while (true) {
            tickManager.update();
            if (tickManager.hasTick()) {
                scheduler.update();
            }
        }
    }

}
