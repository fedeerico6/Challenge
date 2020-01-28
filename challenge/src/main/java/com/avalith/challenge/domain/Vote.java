package com.avalith.challenge.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "votes")
@ApiModel
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @ApiModelProperty(value = "Employee that I cast the vote")
    private Employee fromEmployee;

    @ManyToOne
    @ApiModelProperty(value = "Voted employee")
    private Employee voteEmployee;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "Vote date")
    private Date date;

    @Column(length = 175)
    private String commentary;
}
