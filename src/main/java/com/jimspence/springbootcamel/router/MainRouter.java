package com.jimspence.springbootcamel.router;

import com.jimspence.springbootcamel.model.EmployeeIn;
import com.jimspence.springbootcamel.model.EmployeeOut;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component("mainRouter")
public class MainRouter extends RouteBuilder {

	@Override
	public void configure() {

		// this can also be configured in application.properties
//		restConfiguration()
//				.component("servlet")
//				.bindingMode(RestBindingMode.auto)
//				.dataFormatProperty("prettyPrint", "true")
//				.enableCORS(true)
//				.port("8081")
//				.contextPath(contextPath.substring(0, contextPath.length() - 2))
//				// turn on swagger api-doc
//				.apiContextPath("/api-doc")
//				.apiProperty("api.title", "User API")
//				.apiProperty("api.version", "1.0.0");

//		from("timer://timer1?period=1000")
//		.setBody(simple("select * from Brand"))
//		.to("jdbc:dataSource")
//		.split().simple("${body}")
//		.log("process row ${body}")
//		.process(new Processor(){
//
//			public void process(Exchange xchg) throws Exception {
//
//				Map<String, Object> row = xchg.getIn().getBody(Map.class);
//				System.out.println("Processing....."+row);
//				EmployeeIn emp = new EmployeeIn();
//
//				emp.setId(row.get("ID").toString());
//				emp.setName(row.get("NAME").toString());
//				emp.setDob(row.get("DOB").toString());
//				emp.setSalary((Integer)row.get("SALARY"));
//
//				System.out.println("EmployeeIn: "+ emp);
//			}
//
//		})
//		.to("mock:result");

//		rest("/").description("REST service")
//				.consumes("application/json")
//				.produces("application/json")
//
//				.get("/mssqlstuff3").description("Post to Student Service Program")
//				.responseMessage().code(200).message("All Students successfully returned").endResponseMessage()
//				.to("direct:mssqlstuff3");f9
//
//		from("direct:mssqlstuff3")
//				.log(INFO, "*** beforeSetBoby ***")
//				.setBody(simple("select * from EmployeeIn"))
//				.log(INFO, "*** beforeToJdbc ***")
//				.to("jdbc:dataSource")
////				.log(INFO, "*** beforeTestProcessor ***")
////				.process("testProcessor")
//				.process("jsonProcessor")
//		;
		onException(Exception.class)
				.handled(true)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.BAD_REQUEST.value()))
				.setBody(simple("${exception.message}"));


		JacksonDataFormat format = new ListJacksonDataFormat(EmployeeIn.class);
		JacksonDataFormat formatOut = new ListJacksonDataFormat(EmployeeOut.class);

		rest("/").description("REST service")
				.consumes(MediaType.APPLICATION_JSON_VALUE)
				.produces(MediaType.APPLICATION_JSON_VALUE)

				.post("/incoming").description("Post to Spring Boot Camel Application")

				.route().from("direct:start")
				.to("json-validator:employee-in.json")
				.convertBodyTo(String.class)
				.unmarshal(format)
				.to("employeeTransformer")
				.marshal(formatOut)
				.to("json-validator:employee-out.json")
				.to("mock:endpoint")
		;

	}

}
