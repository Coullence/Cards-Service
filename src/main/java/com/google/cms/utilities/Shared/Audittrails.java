package com.google.cms.utilities.Shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@MappedSuperclass
public class Audittrails extends DataPK {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character postedFlag = 'Y';
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String postedBy;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime postedTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character modifiedFlag  = 'N';
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String modifiedBy;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime modifiedTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character verifiedFlag  = 'N';
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String verifiedBy;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime verifiedTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character deletedFlag = 'N';
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String deletedBy;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime deletedTime;
}
