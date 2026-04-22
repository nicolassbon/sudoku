package com.nicolas.ui.swing.events;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.nicolas.ui.swing.events.EventType.CLEAR_SPACE;

public class NotifierService {

    private final Map<EventType, List<EventListener>> listeners;

    public NotifierService() {
        listeners = new EnumMap<>(EventType.class);
        listeners.put(CLEAR_SPACE, new ArrayList<>());
    }

    public void subscribe(final EventType eventType, final EventListener listener) {
        listeners.get(eventType).add(listener);
    }

    public void notify(final EventType eventType) {
        listeners.get(eventType).forEach(listener -> listener.update(eventType));
    }
}
