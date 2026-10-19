package com.smartrental.controller;
import com.smartrental.model.*;
import com.smartrental.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/properties")
public class PropertyController {
 private final PropertyRepository properties;private final UserRepository users;
 public PropertyController(PropertyRepository properties,UserRepository users){this.properties=properties;this.users=users;}
 @GetMapping public List<Property> all(@RequestParam(required=false) String location,@RequestParam(required=false) String state,@RequestParam(required=false) String city,@RequestParam(required=false) String locality,@RequestParam(required=false) Integer maxRent,@RequestParam(required=false) String furnishing,@RequestParam(required=false) String tenantPreference){
  return properties.findAllByOrderByCreatedAtDesc().stream().filter(p->location==null||p.getLocation().toLowerCase().contains(location.toLowerCase())).filter(p->state==null||(p.getState()!=null&&p.getState().equalsIgnoreCase(state))).filter(p->city==null||(p.getCity()!=null&&p.getCity().toLowerCase().contains(city.toLowerCase()))).filter(p->locality==null||(p.getLocality()!=null&&p.getLocality().toLowerCase().contains(locality.toLowerCase()))).filter(p->maxRent==null||p.getRent()<=maxRent).filter(p->furnishing==null||furnishing.equalsIgnoreCase(p.getFurnishing())).filter(p->tenantPreference==null||tenantPreference.equalsIgnoreCase(p.getTenantPreference())).toList();
 }
 @GetMapping("/{id}") public ResponseEntity<?> one(@PathVariable Long id){return properties.findById(id).<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());}
 @GetMapping("/mine") public ResponseEntity<?> mine(Authentication auth){if(auth==null)return ResponseEntity.status(401).body(Map.of("message","Please log in."));AppUser u=users.findByEmail(auth.getName()).orElse(null);if(u==null)return ResponseEntity.status(401).build();return ResponseEntity.ok(properties.findByOwnerIdOrderByCreatedAtDesc(u.getId()));}
 @PostMapping public ResponseEntity<?> create(@RequestBody Property p,Authentication auth){
  if(auth==null)return ResponseEntity.status(401).body(Map.of("message","Please log in to add a property."));
  AppUser u=users.findByEmail(auth.getName()).orElse(null);if(u==null)return ResponseEntity.status(401).build();
  if(p.getTitle()==null||p.getTitle().isBlank()||p.getLocation()==null||p.getLocation().isBlank()||p.getRent()==null||p.getRent()<0)return ResponseEntity.badRequest().body(Map.of("message","Title, location and a valid rent are required."));
  p.setId(null);p.setOwnerId(u.getId());if(p.getOwnerName()==null||p.getOwnerName().isBlank())p.setOwnerName(u.getName());return ResponseEntity.status(HttpStatus.CREATED).body(properties.save(p));
 }
 @PutMapping("/{id}") public ResponseEntity<?> update(@PathVariable Long id,@RequestBody Property body,Authentication auth){
  AppUser u=current(auth);if(u==null)return ResponseEntity.status(401).build();Property p=properties.findById(id).orElse(null);if(p==null)return ResponseEntity.notFound().build();if(!p.getOwnerId().equals(u.getId()))return ResponseEntity.status(403).body(Map.of("message","You can only edit your own listing."));
  p.setTitle(body.getTitle());p.setLocation(body.getLocation());p.setState(body.getState());p.setCity(body.getCity());p.setLocality(body.getLocality());p.setBlock(body.getBlock());p.setFlatNumber(body.getFlatNumber());p.setRent(body.getRent());p.setFurnishing(body.getFurnishing());p.setTenantPreference(body.getTenantPreference());p.setMembers(body.getMembers());p.setBedrooms(body.getBedrooms());p.setBathrooms(body.getBathrooms());p.setImageUrl(body.getImageUrl());p.setDescription(body.getDescription());p.setOwnerName(body.getOwnerName());p.setOwnerPhone(body.getOwnerPhone());return ResponseEntity.ok(properties.save(p));
 }
 @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable Long id,Authentication auth){AppUser u=current(auth);if(u==null)return ResponseEntity.status(401).build();Property p=properties.findById(id).orElse(null);if(p==null)return ResponseEntity.notFound().build();if(!p.getOwnerId().equals(u.getId()))return ResponseEntity.status(403).body(Map.of("message","You can only delete your own listing."));properties.delete(p);return ResponseEntity.noContent().build();}
 private AppUser current(Authentication a){return a==null?null:users.findByEmail(a.getName()).orElse(null);}
}
