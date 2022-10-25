package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Code referenced from AlarmSystem

// Represents a log of cookbook events
public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private Collection<Event> events;

    // EFFECTS: constructs new instance of event log as an empty list and a private event log;
    //          prevents external construction, adhering to the Singleton Design Pattern
    private EventLog() {
        events = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: returns instance of event log, or creates it if it doesn't already exist
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: adds an event to the event log
    public void logEvent(Event e) {
        events.add(e);
    }

    // EFFECTS: returns list of all events that occurred in the event log
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
