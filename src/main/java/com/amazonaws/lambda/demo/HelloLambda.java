package com.amazonaws.lambda.demo;

import com.amazonaws.lambda.domain.Request;
import com.amazonaws.lambda.domain.Response;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class HelloLambda implements RequestHandler<Request, Response> {

	@Override
	public Response handleRequest(Request request, Context context) {
		
		String greetingString = String.format("Hello %s %s.", request.getFirstName(), request.getLastName());
		context.getLogger().log("The greeting is: " + greetingString);		
		
		return new Response(greetingString);
	}

}
