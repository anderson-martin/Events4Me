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
        setLocationForEvents();
        usersOrganizeAndJoinEvents();
        makeUsersConnect();


    }

    private void loadTimeSettings() {

        for (User u : userService.listUsers()) {
            TimeSetting timeSetting = new TimeSetting();
            LocalTime start = LocalTime.of(17, 0);
            LocalTime end = LocalTime.of(21, 0);
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
        dmitry.setFirstName("Dmitry");
        dmitry.setLastName("Khramov");
        dmitry.setEmail("dk@metropolia.fi");
        dmitry.setPassword("admin");
        dmitry.setCountry("Finland");
        dmitry.setBirthday("02.05.1992");
        dmitry.getInterests().add(Interest.BUSINESS);
        dmitry.getInterests().add(Interest.SPORT);
        dmitry.getInterests().add(Interest.DANCE);
        userService.saveOrUpdateUser(dmitry);

        User martin = new User();
        martin.setUsername("martin");
        martin.setFirstName("Martin");
        martin.setPassword("martin");
        martin.getInterests().add(Interest.PARTY);
        martin.getInterests().add(Interest.ART);
        martin.getInterests().add(Interest.MOVIES);
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


        userService.saveOrUpdateUser(test5);
        userService.saveOrUpdateUser(test6);
        userService.saveOrUpdateUser(test7);
        userService.saveOrUpdateUser(test8);



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
        businessEventPast.setEndTime(LocalDateTime.of(2017, 2, 2, 14, 0));
        businessEventPast.setCategory(Interest.BUSINESS);
        eventService.saveOrUpdateEvent(businessEventPast);

        Event sportEventPast = new Event();
        sportEventPast.setTitle("Sport event past");
        sportEventPast.setEndTime(LocalDateTime.of(2017, 2, 3, 13, 0));
        sportEventPast.setEndTime(LocalDateTime.of(2017, 2, 3, 14, 0));
        sportEventPast.setCategory(Interest.SPORT);
        eventService.saveOrUpdateEvent(sportEventPast);


    }

    public void loadLocations() {

        Location cafeMascot = new Location();
        cafeMascot.setAddress("Neljäs linja 2, 00530 Helsinki");
        cafeMascot.setCalendarID("qo8nro2mtp67dn78qk36b60vqg@group.calendar.google.com");
        cafeMascot.setName("Cafe Mascot");

        Location maxine = new Location();
        maxine.setAddress("Urho Kekkosen katu 1A, 00100 Helsinki");
        maxine.setCalendarID("3n4jiu1vp1hma8459b71jbmh8g@group.calendar.google.com");
        maxine.setName("Maxine");

        locationService.saveOrUpdateLocation(cafeMascot);
        locationService.saveOrUpdateLocation(maxine);
    }

    public void setLocationForEvents(){
        eventService.setLocationForEvent(1,2);
        eventService.setLocationForEvent(2, 1);
    }

    public void usersOrganizeAndJoinEvents(){
        /* change following to use serviceimplementations


        test5.organizeNewEvent(sportEvent);
        test6.enrolEvent(sportEvent);
        sportEvent.acceptAttendee(test6);
        test7.enrolEvent(sportEvent);
        test8.enrolEvent(sportEvent);
        sportEvent.acceptAttendee(test8);


*/

        User test6 = userService.findByUsername("test6");
        User test7 = userService.findByUsername("test7");
        User test8 = userService.findByUsername("test8");

        Event sportEvent = eventService.findByTitle("Sport event");
        eventUserService.createEvent(test6, sportEvent);

        /*
        eventUserService.joinEvent(test7, 1);
        eventUserService.joinEvent(test8, 1);
        */
    }

    public void makeUsersConnect(){
        userService.sendFriendRequestTo("test5", "test6");
        userService.sendFriendRequestTo("test7", "test6");
        userService.sendFriendRequestTo("test8", "test6");
        userService.acceptFriend("test5", "test6");
        userService.acceptFriend("test7", "test6");

    }
}

/*
?? quesitons ot ask dima:
hat is pro way, having arhuments as id or as object?
how many layers have we been using in our app?
 */