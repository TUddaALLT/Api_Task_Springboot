package com.my.task.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Account {
    @Id
    @Column(unique = true)
    private String username;
    private String password;
    private int role;
    // o trong list cac nhom
    @ManyToMany
    @JoinTable(name = "account_group_task", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "groupId"))
    private List<GroupTask> grouptasks;

    @OneToMany(mappedBy = "accountMakeGroup", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<GroupTask> group_maked;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Task> tasks;

}
