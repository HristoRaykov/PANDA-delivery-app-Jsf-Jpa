package panda.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import panda.domain.entities.User;
import panda.domain.models.service.UserServiceModel;

public class ModelMapperUtil {
	
	private static ModelMapper mapper;
	
	public ModelMapperUtil() {
		if (mapper == null) {
			mapper = new ModelMapper();
		}
		initMappings();
	}
	
	private static void initMappings() {
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		
		createUserToUserServiceModelMapping();
		
		
		
	}
	

	

	private static void createUserToUserServiceModelMapping() {
		mapper.createTypeMap(User.class, UserServiceModel.class)
				.addMappings(mapping -> mapping.map(User::getPackages, UserServiceModel::setPackages));
	}
	

}



//		Converter<String, LocalDate> stringToDate = context -> {
//		String dateString = context.getSource();
//		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
//		return LocalDate.parse(dateString, dateTimeFormatter);
//		};