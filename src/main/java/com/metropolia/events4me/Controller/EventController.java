package com.metropolia.events4me.Controller;

import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Service.EventService;
import java.security.Principal;

import com.metropolia.events4me.Service.EventUserService;
import com.metropolia.events4me.Service.LocationService;
import com.metropolia.events4me.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class EventController {

    private EventService eventService;
    private UserService userService;
    private LocationService locationService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setLocationService(LocationService locationService){
        this.locationService = locationService;
    }


    @RequestMapping("/event/list")
    public String listEvents(Model model){
        model.addAttribute("events", eventService.listAllEvents());
        return "event/list";
    }

    @RequestMapping("/event/listPast")
    public @ResponseBody
    List<Event> listPastEvents(){
        return eventService.listPastEvents();
    }

    @RequestMapping("/event/listFuture")
    public @ResponseBody
    List<Event> listFutureEvents(){
        return eventService.listFutureEvents();
    }

    @RequestMapping("/event/show/{id}")
    public String getEvent(Principal principal,@PathVariable Integer id, Model model){
        model.addAttribute("product", eventService.getEventById(id));
        model.addAttribute("user",userService.findByUsername(principal.getName()));
        return "event/show";
    }

    @RequestMapping("product/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Event product = eventService.getEventById(id);
        model.addAttribute("event", product);
        model.addAttribute("locations", locationService.listLocations());
        return "event/eventform";
    }

    @RequestMapping(value = "/event/new")
    public String newProduct(Model model) {
        model.addAttribute("event", new Event());
        return "event/newEvent";
    }

    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public String saveOrUpdateEvent(Event event){
        Event savedEvent = eventService.saveOrUpdateEvent(event);

        return "redirect:/event/show/" + savedEvent.getEventId();
    }

    @RequestMapping(value = "/event/update", method = RequestMethod.POST)
    public String UpdateEvent(Event event){
        Event eventToUpdate = eventService.getEventById(event.getEventId());
        eventToUpdate.setCategory(event.getCategory());
        eventToUpdate.setEndTime(event.getEndTime());
        eventToUpdate.setDescription(event.getDescription());
        eventToUpdate.setTitle(event.getTitle());
        eventToUpdate.setConfirmedAttendees(event.getConfirmedAttendees());
        eventToUpdate.setLocation(event.getLocation());
        eventService.saveOrUpdateEvent(eventToUpdate);

        return "redirect:/event/show/" + eventToUpdate.getEventId();
    }

    @RequestMapping("/event/delete/{id}")
    public String delete(@PathVariable Integer id){
        eventService.delete(id);
        return "redirect:/event/list";
    }
}
