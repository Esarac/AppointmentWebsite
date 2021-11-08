package co.edu.icesi.ci.thymeval.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import co.edu.icesi.ci.thymeval.validation.Add1;
import co.edu.icesi.ci.thymeval.validation.Add2;
import co.edu.icesi.ci.thymeval.validation.Edit;
import lombok.Data;

@Entity
@Data
public class UserApp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank(groups = {Edit.class, Add1.class})
	@Size(min = 3, groups = {Edit.class, Add1.class})
	private String username;
	
	@NotBlank(groups = {Add1.class})
	@Size(min = 8, groups = {Add1.class})
	@Pattern(regexp="(^$|.{8,2147483647})", groups = {Edit.class}, message = "size must be between 8 and 2147483647")
	private String password;
	
	@NotBlank(groups = {Edit.class, Add2.class})
	@Size(min = 2, groups = {Edit.class, Add2.class})
	private String name;

	@NotBlank(groups = {Edit.class, Add2.class})
	@Email(groups = {Edit.class, Add2.class})
	private String email;

	@NotNull(groups = {Edit.class, Add2.class})
	private UserType type;

	@NotNull(groups = {Edit.class, Add1.class})
	@Past(groups = {Edit.class, Add1.class})
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@NotNull(groups = {Edit.class, Add2.class})
	private UserGender gender;
}
