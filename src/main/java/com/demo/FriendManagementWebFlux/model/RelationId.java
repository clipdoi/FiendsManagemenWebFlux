package com.demo.FriendManagementWebFlux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationId implements Serializable {
    Long emailId;
    Long friendId;
    String status;
}
