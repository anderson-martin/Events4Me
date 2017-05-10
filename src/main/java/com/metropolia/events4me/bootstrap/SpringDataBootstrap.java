package com.metropolia.events4me.bootstrap;

import com.metropolia.events4me.Model.*;
import com.metropolia.events4me.Model.security.Role;
import com.metropolia.events4me.Service.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class SpringDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private EventService eventService;
    private RoleService roleService;
    private TimeSettingService timeSettingService;
    private LocationService locationService;
    private EventUserService eventUserService;

    @Autowired
    public void setTimeSettingService(TimeSettingService timeSettingService) {
        this.timeSettingService = timeSettingService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Autowired
    public void setEventUserService(EventUserService eventUserService) {
        this.eventUserService = eventUserService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadLocations();
        loadEvents();
        loadRoles();
        loadUsers();
//        assignUserRole();
        assignAdminRole();
        loadTimeSettings();

    }

    private void loadTimeSettings() {
//        TimeSetting  test = new TimeSetting();
//        LocalTime s = LocalTime.of(0, 0);
//        LocalTime e = LocalTime.of(23, 59);
//        String i = s + ";" + e;
//        test.getTimeMap().put(Days.MONDAY, i);
//        test.getTimeMap().put(Days.TUESDAY, i);
//        test.getTimeMap().put(Days.WEDNESDAY, i);
//        test.getTimeMap().put(Days.THURSDAY, i);
//        test.getTimeMap().put(Days.FRIDAY, i);
//        test.getTimeMap().put(Days.SATURDAY, i);
//        test.getTimeMap().put(Days.SUNDAY, i);
//        TimeSettingConverter converter = TimeSettingConverter.convertForTemplate(test);
//        TimeSetting timeSetting = TimeSettingConverter.convertForDatabase(converter);

        for (User u : userService.listUsers()) {
            TimeSetting timeSetting = new TimeSetting();
            LocalTime start = LocalTime.of(12, 10);
            LocalTime end = LocalTime.of(16, 30);
            String interval = start + ";" + end;
            timeSetting.getTimeMap().put(Days.MONDAY, interval);
            timeSetting.getTimeMap().put(Days.TUESDAY, interval);
            timeSetting.getTimeMap().put(Days.WEDNESDAY, interval);
            timeSetting.getTimeMap().put(Days.THURSDAY, interval);
            timeSetting.getTimeMap().put(Days.FRIDAY, interval);
            timeSetting.getTimeMap().put(Days.SATURDAY, interval);
            timeSetting.getTimeMap().put(Days.SUNDAY, interval);
            u.setTimeAvailability(timeSetting);
            timeSettingService.saveOrUpdate(timeSetting);
            userService.saveOrUpdateUser(u);
        }
    }

    private void assignAdminRole() {
        List<Role> roles = roleService.listRoles();
        List<User> users = userService.listUsers();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ADMIN")) {
                users.forEach(user -> {
                    if (user.getUsername().equals("dima")) {
                        user.addRole(role);
                        userService.saveOrUpdateUser(user);
                    }
                });
            }
        });

    }

    private void assignUserRole() {
        List<Role> roles = roleService.listRoles();
        List<User> users = userService.listUsers();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("USER")) {
                users.forEach(user -> {
                    user.addRole(role);
                    userService.saveOrUpdateUser(user);
                });
            }
        });
    }

    private void loadRoles() {
        Role role = new Role();
        role.setRole("USER");
        roleService.saveOrUpdateRole(role);

        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        roleService.saveOrUpdateRole(adminRole);
    }

    private void loadUsers() {

        User dmitry = new User();
        dmitry.setUsername("dima");
        dmitry.setFirstName("dmitry");
        dmitry.setPassword("admin");
        dmitry.getInterests().add(Interest.BUSINESS);
        dmitry.getInterests().add(Interest.SPORT);
        dmitry.getInterests().add(Interest.DANCE);
        userService.saveOrUpdateUser(dmitry);

        User martin = new User();
        martin.setUsername("martin");
        martin.setFirstName("martin");
        martin.setPassword("user");
        martin.getInterests().add(Interest.PARTY);
        martin.getInterests().add(Interest.ART);
        userService.saveOrUpdateUser(martin);


        User niklas = new User();
        niklas.setUsername("nilas");
        niklas.setFirstName("niklas");
        niklas.setPassword("user");
        niklas.getInterests().add(Interest.BUSINESS);
        niklas.getInterests().add(Interest.SPORT);
        niklas.getInterests().add(Interest.DANCE);
        userService.saveOrUpdateUser(niklas);

        User user4 = new User();
        user4.setUsername("user4");
        user4.setFirstName("firstname4");
        user4.setPassword("user");
        user4.getInterests().add(Interest.BUSINESS);
        user4.getInterests().add(Interest.NATURE);
        userService.saveOrUpdateUser(user4);


        User test5 = new User();
        test5.setUsername("test5");
        test5.setFirstName("firstname5");
        test5.setLastName("lastname5");
        test5.setEmail("test5@email.com");
        test5.setPassword("admin");
        test5.getInterests().add(Interest.BUSINESS);
        test5.getInterests().add(Interest.SPORT);
        test5.getInterests().add(Interest.DANCE);

        User test6 = new User();
        test6.setUsername("test6");
        test6.setFirstName("firstname6");
        test6.setLastName("lastname6");
        test6.setPassword("user");
        test6.setEmail("test6@email.com");
        test6.getInterests().add(Interest.PARTY);
        test6.getInterests().add(Interest.ART);


        User test7 = new User();
        test7.setUsername("test7");
        test7.setFirstName("firstname7");
        test7.setLastName("lastname7");
        test7.setPassword("user");
        test7.setEmail("test7@email.com");
        test7.getInterests().add(Interest.BUSINESS);
        test7.getInterests().add(Interest.SPORT);
        test7.getInterests().add(Interest.DANCE);

        User test8 = new User();
        test8.setUsername("test8");
        test8.setFirstName("firstname8");
        test8.setLastName("lastname8");
        test8.setPassword("user");
        test8.setEmail("test8@email.com");
        test8.getInterests().add(Interest.BUSINESS);
        test8.getInterests().add(Interest.NATURE);

        //TODO: ensure that location can be persisted and retrieved from DB

        Location cafe_mascot = new Location();
        cafe_mascot.setAddress("Neljäs linja 2, 00530 Helsinki");
        cafe_mascot.setCalendarID("qo8nro2mtp67dn78qk36b60vqg@group.calendar.google.com");
        cafe_mascot.setName("Cafe Mascot");

        Location maxine = new Location();
        maxine.setAddress("Urho Kekkosen katu 1A, 00100 Helsinki");
        maxine.setCalendarID("3n4jiu1vp1hma8459b71jbmh8g@group.calendar.google.com");
        maxine.setName("Maxine");



//TODO: migrate following to loadlocations
        Event sportEvent = new Event();
        sportEvent.setTitle("Event in Cafe Mascot");
        sportEvent.setStartTime(LocalDateTime.of(2017, 5, 8, 13, 0));
        sportEvent.setEndTime(LocalDateTime.of(2017, 5, 8, 18, 0));
        sportEvent.setLocation(cafe_mascot);
        System.out.println("result of event creation:" + eventUserService.createEvent(test6, sportEvent));

        Event sportEvent2 = new Event();
        sportEvent2.setTitle("Event in Maxine");
        sportEvent2.setStartTime(LocalDateTime.of(2017, 5, 9, 13, 0));
        sportEvent2.setEndTime(LocalDateTime.of(2017, 5, 9, 18, 0));
        sportEvent2.setLocation(maxine);
        System.out.println("result of event creation:" + eventUserService.createEvent(test6, sportEvent2));




/* change following to use serviceimplementations

        //TODO: make the event have the ablity for automatic acceptance
        test5.organizeNewEvent(sportEvent);
        test6.enrolEvent(sportEvent);
        sportEvent.acceptAttendee(test6);
        test7.enrolEvent(sportEvent);
        test8.enrolEvent(sportEvent);
        sportEvent.acceptAttendee(test8);


*/

        userService.saveOrUpdateUser(test5);
        userService.saveOrUpdateUser(test6);
        userService.saveOrUpdateUser(test7);
        userService.saveOrUpdateUser(test8);
        eventService.saveOrUpdateEvent(sportEvent);
        locationService.saveOrUpdateLocation(cafe_mascot);
        locationService.saveOrUpdateLocation(maxine);

        userService.sendFriendRequestTo("test5", "test6");
        userService.sendFriendRequestTo("test7", "test6");
        userService.sendFriendRequestTo("test8", "test6");
        userService.acceptFriend("test5", "test6");
        userService.acceptFriend("test7", "test6");


    }

    private void loadEvents() {
        Event sportEvent = new Event();
        sportEvent.setTitle("Sport event");
        sportEvent.setStartTime(LocalDateTime.of(2017, 5, 22, 13, 0));
        sportEvent.setEndTime(LocalDateTime.of(2017, 5, 22, 15, 0));
        sportEvent.setCategory(Interest.SPORT);
        eventService.saveOrUpdateEvent(sportEvent);

        Event partyEvent = new Event();
        partyEvent.setTitle("Party event");
        partyEvent.setStartTime(LocalDateTime.of(2017, 6, 10, 13, 0));
        partyEvent.setEndTime(LocalDateTime.of(2017, 6, 10, 13, 0));
        partyEvent.setCategory(Interest.PARTY);
        eventService.saveOrUpdateEvent(partyEvent);

        Event businessEvent = new Event();
        businessEvent.setTitle("Business event");
        businessEvent.setStartTime(LocalDateTime.of(2017, 5, 22, 18, 0));
        businessEvent.setEndTime(LocalDateTime.of(2017, 5, 22, 22, 0));
        businessEvent.setCategory(Interest.BUSINESS);
        eventService.saveOrUpdateEvent(businessEvent);

        Event businessEventPast = new Event();
        businessEventPast.setTitle("Business event past");
        businessEventPast.setEndTime(LocalDateTime.of(2017, 2, 2, 13, 0));
        businessEventPast.setCategory(Interest.BUSINESS);
        eventService.saveOrUpdateEvent(businessEventPast);

        Event sportEventPast = new Event();
        sportEventPast.setTitle("Sport event past");
        sportEventPast.setEndTime(LocalDateTime.of(2017, 2, 3, 13, 0));
        sportEventPast.setCategory(Interest.SPORT);
        eventService.saveOrUpdateEvent(sportEventPast);


    }

    public void loadLocations() {



    }
}
