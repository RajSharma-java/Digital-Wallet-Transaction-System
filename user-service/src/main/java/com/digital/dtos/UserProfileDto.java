package com.digital.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private Long id;


    @NotBlank(message = " name should be required!!")
    private String fullName;

    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Invalid email format"
    )
    @Column(unique = true)
    private String email;


    @Column(unique = true)
    private Long phone;

    private String referralCode;

    private String referredBy;

    private  boolean status= true;

    private boolean isDeleted=false;



    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
