package com.amazonaws.lambda.async.client;

import java.nio.ByteBuffer;
import java.util.concurrent.Future;

import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.lambda.AWSLambdaAsync;
import com.amazonaws.services.lambda.AWSLambdaAsyncClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;

public class InvokeLambdaFunctionCallback {

	private static class AsyncLambdaHandler implements AsyncHandler<InvokeRequest, InvokeResult> {

		@Override
		public void onError(Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}

		@Override
		public void onSuccess(InvokeRequest req, InvokeResult res) {
			System.out.println("\nLambda function returned:");
			ByteBuffer response_payload = res.getPayload();
			System.out.println(new String(response_payload.array()));
			System.exit(0);
		}

	}

	public static void main(String[] args) {
		
		String functionName = "HelloFunction";
        String functionInput = "{\"who\":\"AWS SDK for Java\"}";		
		
		AWSLambdaAsync lambda = AWSLambdaAsyncClientBuilder.defaultClient();
		InvokeRequest req = new InvokeRequest()
	            .withFunctionName(functionName)
	            .withPayload(ByteBuffer.wrap(functionInput.getBytes()));
		
		Future<InvokeResult> future_res = lambda.invokeAsync(req, new AsyncLambdaHandler());

		System.out.print("Waiting for async callback");
        while (!future_res.isDone() && !future_res.isCancelled()) {
            // perform some other tasks...
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                System.err.println("Thread.sleep() was interrupted!");
                System.exit(0);
            }
            System.out.print(".");
        }
    }
}
