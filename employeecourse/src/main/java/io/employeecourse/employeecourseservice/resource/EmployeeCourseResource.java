package io.employeecourse.employeecourseservice.resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.employeecourse.employeecourseservice.model.EmpCourse;
import io.employeecourse.employeecourseservice.model.Employee;
import io.employeecourse.employeecourseservice.model.EmpStudent;
import io.employeecourse.employeecourseservice.model.Student;

@RestController
@RequestMapping("/catalog")
public class EmployeeCourseResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@RequestMapping("/{userId}")
	public List<EmpCourse> getCatalog(@PathVariable String userId) {
		
		WebClient.Builder builder=WebClient.builder();
		
		/*List<Rating> ratings=Arrays.asList(
				new Rating("1234",4),
				new Rating("5878",3)
				);*/
		EmpStudent empStudent=restTemplate.getForObject("http://employee-data-service/company/employees/" + userId, EmpStudent.class);
		
		
		return empStudent.getEmpc().stream().map(rating -> {
			Student movie= restTemplate.getForObject("http://student-data-service/student/" + rating.getId(), Student.class);
			//Movie movie=webClientBuilder.build().get().uri("http://localhost:8082/movies/" + rating.getMovieId()).retrieve().bodyToMono(Movie.class).block();
			return new EmpCourse(movie.getId(),movie.getName(),"Maths");}
		).collect(Collectors.toList());
				
		
		/*return Arrays.asList(
				new CatalogItem("Transformers","Test",4)
				);*/

}
}
