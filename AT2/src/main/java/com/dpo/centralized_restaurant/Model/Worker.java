package com.dpo.centralized_restaurant.Model;

import com.dpo.centralized_restaurant.Model.Configuration.Configuration;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Worker {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean connected;


    @OneToMany(mappedBy = "worker")
    private Set<Configuration> configuration;


    public Worker(){}

    public Worker(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.connected = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Configuration> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Set<Configuration> configuration) {
        this.configuration = configuration;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            Worker other = (Worker)obj;
            return id == other.id;
        }
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
