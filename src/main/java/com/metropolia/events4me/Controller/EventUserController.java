package com.metropolia.events4me.Controller;

import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.EventUserService;
import com.metropolia.events4me.Service.LocationService;
import com.metropolia.events4me.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
public class EventUserController {

    private EventUserService eventUserService;
    private UserService userService;
    private LocationService locationService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEventUserService(EventUserService eventUserService) {
        this.eventUserService = eventUserService;
    }

    @Autowired
    public void setLocationService(LocationService locationService){
        this.locationService = locationService;
    }


    @ResponseBody
    @RequestMapping("preferedevents")
    public List<Event> getMatchedEventsForUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return eventUserService.matchEventsForUser(user);
    }

    @RequestMapping(value = "join/event/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> joinEventSubmit(Principal principal, @PathVariable Integer id) {
        User user = userService.findByUsername(principal.getName());
        eventUserService.joinEvent(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/newEvent", method = RequestMethod.GET)
    public String newEventForm(Model model) {
        model.addAttribute("locations", locationService.listLocations());

        model.addAttribute("event",new Event());
        return "event/newEvent";
    }


    @RequestMapping(value = "/newEvent", method = RequestMethod.POST)
    public String newEventSubmit(@ModelAttribute Event event, @ModelAttribute String locationID, Principal principal, Model model) {
        User organizer = userService.findByUsername(principal.getName());

        System.out.println("event created");
        Event newEvent = eventUserService.createEvent(organizer, event);
        userService.joinEvent(organizer,newEvent);
        return "redirect:/event/show/" + newEvent.getEventId();
    }
}
