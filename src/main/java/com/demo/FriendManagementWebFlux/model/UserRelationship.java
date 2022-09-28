package com.demo.FriendManagementWebFlux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RelationId.class)
@Table(name = "user_relationship", schema = "public")
public class UserRelationship {

    @Id
    @Column(name = "email_id")
    private Long emailId;

    @Id
    @Column(name = "friend_id")
    private Long friendId;

    @Id
    @Column(name = "status")
    private String status;

}
