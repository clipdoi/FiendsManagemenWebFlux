package com.demo.FriendManagementWebFlux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelationId implements Serializable {

    private Long emailId;

    private Long friendId;

    private String status;

}
