package panda.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtilImpl implements ValidatorUtil {
	
    private Validator validator;

    public ValidatorUtilImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

	
	@Override
	public <T> boolean isValid(T object) {
		return validator.validate(object).size()==0;
	}
	
	@Override
	public <T> Set<ConstraintViolation<T>> getViolations(T object) {
		return validator.validate(object);
	}
	
//	@Override
//	public <T> boolean validateDto(T dto) {
//		if (!isValid(dto)){
//			getViolations(dto).forEach(v-> System.out.println(v.getMessage()));
//			return false;
//		}else {
//			return true;
//		}
//	}
	
	
}
