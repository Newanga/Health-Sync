package com.team98.healthsync.models;


import com.team98.healthsync.enums.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;


@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Enter First Name")
    private String firstName;

    @NotEmpty(message = "Enter Last Name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotEmpty(message = "Enter Address")
    private String address;


    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Temporal(TemporalType.DATE)
    @Past(message = "Birth date cant be in the future")
    @NotNull(message = "Select a birth date")
    private Date dateOfBirth;

    @Pattern(regexp="^([0-9]{9}[x|X|v|V]|[0-9]{12})$",message="Invalid NIC ")
    private String nic;

    @Pattern(regexp="^\\d{3}-\\d{7}$",message="Invalid contact No ")
    private String contactNo;

    @OneToOne()
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    @Valid
    private Account account;




    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
