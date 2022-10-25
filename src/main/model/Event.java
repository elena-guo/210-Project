package model;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

// Code referenced from AlarmSystem

// Represents a cookbook event
public class Event {
    private Date dateLogged;
    private String description;

    // EFFECTS: creates an event with the given description with the current date and time stamp
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    // EFFECTS: returns the date and time of the event
    public Date getDate() {
        return dateLogged;
    }

    // EFFECTS: returns the description of the event
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateLogged, description);
    }

    // EFFECTS: returns concatenated string of event date, time and description
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
