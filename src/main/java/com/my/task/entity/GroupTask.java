package com.my.task.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "group_task")
public class GroupTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private int groupId;

    // nguoi so huu nhom
    @ManyToOne
    @JoinColumn(name = "accountMakeGroup")
    @ToString.Exclude
    private Account accountMakeGroup;

    // list nguoi tham gia
    @Column(name = "name_group")
    private String groupName;

    @ManyToMany(mappedBy = "grouptasks")
    List<Account> accounts;
}
