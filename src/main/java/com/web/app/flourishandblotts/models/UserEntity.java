package com.web.app.flourishandblotts.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@Getter @Setter @ToString
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dni_nie", nullable = false, unique = true)
    private String dniNie;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Size(min = 3, max = 30)
    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    private String name;


    @NotBlank
    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    @Size(min = 3, max = 30)
    private String surname1;

    @Size(min = 3, max = 30)
    private String surname2;

    @Email
    @NotBlank
    @Size(max = 45)
    @Column(nullable = false, unique = true)
    private String mail;

    @NotBlank
    private boolean status; // Active o Inactive

    @Column(name = "avatar_file")
    private String avatarFile;


    @LastModifiedDate
    @Column(name ="last_modified_date")
    private Date lastModifiedDate;

    @NotBlank
    private String course;
    

    @NotBlank
    @Column(name = "user_group") // WARNING FOR POSSIBLE ERROR: the name "group" it's  a MySQL reserved word, should not be used this name.
    private String group;


    @OneToOne(
            cascade = CascadeType.ALL,
            targetEntity = Penalization.class,
            mappedBy = "user"
    )
    private Penalization penalization;


    @ManyToOne(targetEntity = Study.class)
    private Study study;


    private String profFamilyName;

    private String teacherType;

    /*
    * Obtaining the role of the user.
    * */
    @ManyToMany(
            fetch = FetchType.EAGER,
            targetEntity = RoleEntity.class,
            cascade = CascadeType.PERSIST
    )
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<RoleEntity> roles;

    @OneToMany(targetEntity = Comment.class,mappedBy = "user")
    private Set<Comment> comments;

}
