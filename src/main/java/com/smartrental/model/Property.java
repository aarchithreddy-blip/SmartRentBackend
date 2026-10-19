package com.smartrental.model;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="properties")
public class Property {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=150) private String title;
 @Column(nullable=false,length=200) private String location;
 @Column(length=100) private String state;
 @Column(length=100) private String city;
 @Column(length=150) private String locality;
 @Column(length=50) private String block;
 @Column(length=50) private String flatNumber;
 @Column(nullable=false) private Integer rent;
 @Column(length=30) private String furnishing;
 @Column(length=30) private String tenantPreference;
 private Integer members; private Integer bedrooms; private Integer bathrooms;
 @Column(length=1000) private String imageUrl;
 @Column(length=2000) private String description;
 @Column(length=100) private String ownerName;
 @Column(length=40) private String ownerPhone;
 @Column(nullable=false) private Long ownerId;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public String getTitle(){return title;} public void setTitle(String v){title=v;}
 public String getLocation(){return location;} public void setLocation(String v){location=v;}
 public String getState(){return state;} public void setState(String v){state=v;}
 public String getCity(){return city;} public void setCity(String v){city=v;}
 public String getLocality(){return locality;} public void setLocality(String v){locality=v;}
 public String getBlock(){return block;} public void setBlock(String v){block=v;}
 public String getFlatNumber(){return flatNumber;} public void setFlatNumber(String v){flatNumber=v;}
 public Integer getRent(){return rent;} public void setRent(Integer v){rent=v;}
 public String getFurnishing(){return furnishing;} public void setFurnishing(String v){furnishing=v;}
 public String getTenantPreference(){return tenantPreference;} public void setTenantPreference(String v){tenantPreference=v;}
 public Integer getMembers(){return members;} public void setMembers(Integer v){members=v;}
 public Integer getBedrooms(){return bedrooms;} public void setBedrooms(Integer v){bedrooms=v;}
 public Integer getBathrooms(){return bathrooms;} public void setBathrooms(Integer v){bathrooms=v;}
 public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getOwnerName(){return ownerName;} public void setOwnerName(String v){ownerName=v;}
 public String getOwnerPhone(){return ownerPhone;} public void setOwnerPhone(String v){ownerPhone=v;}
 public Long getOwnerId(){return ownerId;} public void setOwnerId(Long v){ownerId=v;}
 public Instant getCreatedAt(){return createdAt;}
}
