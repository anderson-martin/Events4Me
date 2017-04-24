package com.metropolia.events4me.Service;

import com.metropolia.events4me.Model.Event;

import java.util.List;

/**
 * Created by Dmitry on 13.04.2017.
 */

public interface EventService {

    List<Event> listAllEvents();

    List<Event> listPastEvents();

    List<Event> listFutureEvents();

    Event saveOrUpdateEvent(Event event);

    Event getEventById(Integer id);

    void delete(Integer id);
}
