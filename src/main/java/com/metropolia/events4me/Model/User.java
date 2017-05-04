package com.metropolia.events4me.Model;

import com.metropolia.events4me.Model.security.Role;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer userId;
  private String firstName;
  private String lastName;
  private String username;
  private String email;

  @Transient
  private String password;
  private String encryptedPassword;

  @OneToMany(cascade = CascadeType.ALL)
  private Set<Event> adminingEvents;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  @JoinTable
  private List<Role> roles = new ArrayList<>();
  private String birthday;
  private String country;
  private Boolean enabled = true;

  @Enumerated(EnumType.STRING)
  @ElementCollection(targetClass = Interest.class, fetch = FetchType.EAGER)
  private Set<Interest> interests = new HashSet<>();

  @ManyToMany(cascade = CascadeType.ALL)
  private List<Event> attendingEvents;
  private byte[] photo;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<User> friends = new HashSet<User>();

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<User> pendingFriendRequests = new HashSet<User>();

    @OneToOne
    private TimeSetting timeAvailability;

  public User() {
    interests = new HashSet<>();
    friends = new HashSet<>();
    this.adminingEvents = new HashSet<>();
    //TODO: make this hashset
    this.attendingEvents = new ArrayList<>();
  }
  public User(String username, String firstname, String lastname, String password, String email) {
    interests = new HashSet<>();
    friends = new HashSet<>();
    this.username = username;
    this.firstName = firstname;
    this.lastName = lastname;
    this.password = password;
    this.email = email;
    this.adminingEvents = new HashSet<>();
    this.attendingEvents = new ArrayList<>();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    if (userId != null ? !userId.equals(user.userId) : user.userId != null) {
      return false;
    }
    if (username != null ? !username.equals(user.username) : user.username != null) {
      return false;
    }
    return email != null ? email.equals(user.email) : user.email == null;
  }

  @Override
  public int hashCode() {
    int result = userId != null ? userId.hashCode() : 0;
    result = 31 * result + (username != null ? username.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    return result;
  }

  public List<Event> getAttendingEvents() {
    return attendingEvents;
  }

  public void setAttendingEvents(List<Event> attendingEvents) {
    this.attendingEvents = attendingEvents;
  }


  public void organizeNewEvent(Event event) {
    event.setOrganizer(this);
    this.adminingEvents.add(event);
  }

  public Set<Event> getAdminingEvents() {
    return adminingEvents;
  }

  public void setAdminingEvents(Set<Event> adminingEvents) {
    this.adminingEvents = adminingEvents;
  }

  public void enrolEvent(Event event) {
    event.acceptEnrollment(this);
  }

  public void attendEvent(Event event) {
    this.attendingEvents.add(event);
  }

    public TimeSetting getTimeAvailability() {
        return timeAvailability;
    }

    public void setTimeAvailability(TimeSetting timeAvailability) {
        this.timeAvailability = timeAvailability;
    }

    public void acceptFriend(User sender) {
        if ((sender != null) && pendingFriendRequests.contains(sender)) {
            pendingFriendRequests.remove(sender);
            this.friends.add(sender);
        }
    }


  public void recieveFriendRequestFrom(User user) {
    this.pendingFriendRequests.add(user);
  }


  public Set<User> getPendingFriendRequests() {
    return pendingFriendRequests;
  }

  public void sendFriendRequestTo(User user) {
    user.recieveFriendRequestFrom(this);
  }


  public Integer getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public void addRole(Role role) {
    if (!this.roles.contains(role)) {
      this.roles.add(role);
    }

    if (!role.getUsers().contains(this)) {
      role.getUsers().add(this);
    }

  }

  public void removeRole(Role role) {
    this.roles.remove(role);
    role.getUsers().remove(this);
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Set<Interest> getInterests() {
    return interests;
  }

  public void setInterests(Set<Interest> interests) {
    this.interests = interests;
  }

  public byte[] getPhoto() {
    return photo;
  }

  public void setPhoto(byte[] photo) {
    this.photo = photo;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<Event> getEvents() {
    return events;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }


  public Set<User> getFriends() {
    return friends;
  }

  public void setFriends(Set<User> friends) {
    this.friends = friends;
  }

}
