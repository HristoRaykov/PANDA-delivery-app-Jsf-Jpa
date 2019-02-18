package panda.util;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidatorUtil {

	<T> boolean isValid(T dto);
	
	<T> Set<ConstraintViolation<T>> getViolations(T dto);
	
//	<T> boolean validateDto(T dto);

}
