package xyz.hkdev.mc.fiesta.event;

import xyz.hkdev.mc.fiesta.event.render.RenderEvent;
import xyz.hkdev.mc.fiesta.event.world.TickEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

public class EventManager {

    static class ParamFind implements Predicate<Class<? extends Event>> {

        public Class<? extends Event> event;

        public ParamFind(Class<? extends Event> event) {
            this.event = event;
        }

        @Override
        public boolean test(Class<? extends Event> event) {
            return this.event.getTypeName().equals(event.getTypeName());
        }
    }

    private static ArrayList<Object> listeners = new ArrayList<>();

    private static ArrayList<Class<? extends Event>> events = new ArrayList<>();

    public static void registerListener(Object listener) {
        listeners.add(listener);
    }

    public static void unregisterListener(Object listener) {
        listeners.remove(listener);
    }

    static {
        events.add(RenderEvent.class);
        events.add(TickEvent.class);
        events.add(ClientStartEvent.class);
    }

    public static void invokeEvent(Event event) throws InvocationTargetException, IllegalAccessException {
        for(Object t : listeners) {
            for (Method declaredMethod : t.getClass().getDeclaredMethods()) {
                if(declaredMethod.getDeclaredAnnotations()[0].annotationType() == EventHandler.class) {
                    Class<? extends Event> param = (Class<? extends Event>) declaredMethod.getParameterTypes()[0];
                    Optional<Class<? extends Event>> a = events.stream().filter(new ParamFind(param)).findFirst();
                    if(a.isPresent()) {
                        if (param.getTypeName().equals(a.get().getTypeName())) {

                            declaredMethod.invoke(t, event);

                        }
                    }
                }
            }
        }
    }

}
