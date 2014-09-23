package com.rootls.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Document(collection="usercollection")
@Validated
public class User implements Serializable, UserDetails, CredentialsContainer {
  
  private static final long serialVersionUID = -9214271474279476483L;

  public User() {
  }
  
  public User(String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.enabled = true;
    this.accountNonExpired = true;
    this.credentialsNonExpired = true;
    this.accountNonLocked = true;
    this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
  }
  
  public User(String id, String firstName, String lastName, String email) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = email;
    this.email = email;
  }
  
  public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
      this(username, password, true, true, true, true, authorities);
  }

  public User(String username, String password, boolean enabled, boolean accountNonExpired,
          boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {

      if (((username == null) || "".equals(username)) || (password == null)) {
          throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
      }

      this.username = username;
      this.password = password;
      this.enabled = enabled;
      this.accountNonExpired = accountNonExpired;
      this.credentialsNonExpired = credentialsNonExpired;
      this.accountNonLocked = accountNonLocked;
      this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
  }
  
  @Id
  private String id;
  private String firstName;
  private String lastName;
  
  @NotBlank(message = "Password should not be empty")
  @Size(min = 5, max = 30, message = "Password should be between 5 and 30 chacters")
  private String password;
  
  @Email
  @NotBlank
  private String email;

  @NotBlank(message = "Username should not be empty")
  @Size(min = 5, max = 30, message = "Username should be between 5 and 30 chacters")
  private String username;
  private Set<GrantedAuthority> authorities;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLastName() {
    return lastName;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", userName=" + username + ", firstName="
        + firstName + ", lastName=" + lastName + ", password=" + password
        + ", email=" + email + "]";
  }

  @Override
  public void eraseCredentials() {
    password = null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public void setAccountNonExpired(boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
  
  private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
    Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
    // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
    SortedSet<GrantedAuthority> sortedAuthorities =
        new TreeSet<GrantedAuthority>(new AuthorityComparator());

    for (GrantedAuthority grantedAuthority : authorities) {
        Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
        sortedAuthorities.add(grantedAuthority);
    }

    return sortedAuthorities;
  }

  private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
      private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
  
      public int compare(GrantedAuthority g1, GrantedAuthority g2) {
          // Neither should ever be null as each entry is checked before adding it to the set.
          // If the authority is null, it is a custom authority and should precede others.
          if (g2.getAuthority() == null) {
              return -1;
          }
  
          if (g1.getAuthority() == null) {
              return 1;
          }
  
          return g1.getAuthority().compareTo(g2.getAuthority());
      }
  }
  
  @Override
  public boolean equals(Object rhs) {
      if (rhs instanceof User) {
          return username.equals(((User) rhs).username);
      }
      return false;
  }

  @Override
  public int hashCode() {
      return username.hashCode();
  }
}