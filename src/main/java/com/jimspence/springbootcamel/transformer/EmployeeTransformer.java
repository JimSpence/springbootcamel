package com.jimspence.springbootcamel.transformer;

import com.jimspence.springbootcamel.model.EmployeeIn;
import com.jimspence.springbootcamel.model.EmployeeOut;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("employeeTransformer")
public class EmployeeTransformer {

    private static Logger log = Logger.getLogger(EmployeeTransformer.class);

    public void process(Exchange exchange, @Body List<EmployeeIn> employeeInList) {

         List<EmployeeOut> employeeOutList = employeeInList.stream()
                .map(EmployeeIn::transform)
                .collect(Collectors.toList());

        exchange.getMessage().setBody(employeeOutList);
    }
}
