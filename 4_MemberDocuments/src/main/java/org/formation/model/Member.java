package org.formation.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.formation.rest.views.RestViews;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
@Data
@ToString(of = {"id","email","nom","prenom","age"})
public class Member implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(RestViews.List.class)
	private long id;

	@NotNull
	@JsonView(RestViews.List.class)
	private String email;
	
	@JsonView(RestViews.Creation.class)
	private String password;

	@JsonView(RestViews.List.class)
	private String nom, prenom;

	@JsonView(RestViews.List.class)
	private int age;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonView(RestViews.List.class)
	private Date registeredDate;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonView(RestViews.Detail.class)
	private Set<Document> documents = new HashSet<Document>();

	public void addDocument(Document doc) {
		documents.add(doc);
	}
	public Member merge(Member targetMember) {
		if (targetMember.getNom() == null) {
			targetMember.setNom(getNom());
		}
		if (targetMember.getPrenom() == null) {
			targetMember.setPrenom(getPrenom());
		}
		if (targetMember.getEmail() == null) {
			targetMember.setEmail(getEmail());
		}
		if (targetMember.getPassword() == null) {
			targetMember.setPassword(getPassword());
		}
		if (targetMember.getAge() == -1) {
			targetMember.setAge(getAge());
		}
		return targetMember;
	}

	@Transient
	@JsonView(RestViews.List.class)
	public String getNomComplet() {
		return getPrenom() + " " + getNom();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Transient 
	Set<SimpleGrantedAuthority> grantedAuthorities;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if ( grantedAuthorities == null ) {
			grantedAuthorities = new HashSet<>();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			if ( age > 18 ) {
				grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			}
		}
		return grantedAuthorities;

	}
	@Override
	public String getUsername() {		
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return "{noop}" + password;
	}
}
