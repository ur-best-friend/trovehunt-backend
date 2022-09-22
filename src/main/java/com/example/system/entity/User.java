package com.example.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@Entity
public class User extends org.springframework.security.core.userdetails.User {
    public User(String username, String password, List<GrantedAuthority> authorities, String imageUrl, String integrationId){
        super(username,password, authorities);
        this.username = username;
        this.password = password;
        this.integrationId = integrationId;
//        this.imageUrl = imageUrl == null ? GenericUtils.base64ToImage(TrovehuntApplication.BASE64_1440x1440) : imageUrl;
        this.imageUrl = imageUrl;
    }
    public User(){
        super("testUser","", AuthorityUtils.NO_AUTHORITIES);
    }

    @Id
    @Getter
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_seq")
    @SequenceGenerator(name="user_seq", sequenceName="SEQ_USER", allocationSize=10)
    private int id;

    @Getter
    @Column(name = "username", unique = true)
    private String username;

    @Getter @Setter
    private String department;

    @Getter
    @JsonIgnore
    private String password;

    @Getter
    @JsonIgnore
    @Column(name = "integration_id", unique = true)
    private String integrationId;

    // TODO: Add URL validation
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String imageUrl;

    // TODO: Remove toString & make sure nothing breaks
    @Override
    public String toString() {
        return new JSONObject()
                .put("id",id)
                .put("username",username)
                .put("img url", imageUrl)
                .put("integration id", integrationId)
                .toString();
    }

    @JsonIgnore @Override public Collection<GrantedAuthority> getAuthorities() { return super.getAuthorities(); }
    @JsonIgnore @Override public boolean isEnabled() { return super.isEnabled(); }
    @JsonIgnore @Override public boolean isAccountNonExpired() { return super.isAccountNonExpired(); }
    @JsonIgnore @Override public boolean isAccountNonLocked() { return super.isAccountNonLocked(); }
    @JsonIgnore @Override public boolean isCredentialsNonExpired() { return super.isCredentialsNonExpired(); }
}
