package com.amazonaws.lambda.async.client;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.AWSLambdaAsync;
import com.amazonaws.services.lambda.AWSLambdaAsyncClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;

public class InvokeLambdaFunctionAsync {
	
	private static final Logger logger =   Logger.getLogger(InvokeLambdaFunctionAsync.class);

	public static void main(String[] args) {
		
		String functionName = "HelloFunction";
        String functionInput = "{\"who\":\"AWS SDK for Java\"}";
        
        AWSLambdaAsync lambda = AWSLambdaAsyncClientBuilder.defaultClient();
        InvokeRequest req = new InvokeRequest()
            .withFunctionName(functionName)
            .withPayload(ByteBuffer.wrap(functionInput.getBytes()));
        
        Future<InvokeResult> futureResponse = lambda.invokeAsync(req);
        
        logger.info("Waiting for future");
        while (futureResponse.isDone() == false) {
            logger.info(".");
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                logger.error("\nThread.sleep() was interrupted!");
                System.exit(1);
            }
        }
        
        try {
            InvokeResult res = futureResponse.get();
            if (res.getStatusCode() == 200) {
                System.out.println("\nLambda function returned:");
                ByteBuffer response_payload = res.getPayload();
                logger.info(new String(response_payload.array()));
            }
            else {
                logger.info("Received a non-OK response from AWS: %d\n " +
                        res.getStatusCode());
            }
        }
        catch (InterruptedException | ExecutionException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.exit(0);

	}

}
