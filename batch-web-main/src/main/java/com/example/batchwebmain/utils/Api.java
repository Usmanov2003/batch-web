package com.example.batchwebmain.utils;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import static com.example.batchwebmain.utils.ApiConstants.INTERNAL_SERVER_ERROR_EXAMPLE_RESPONSE;
import static com.example.batchwebmain.utils.ApiConstants.SC_500;

@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = SC_500,
                        description = "Internal Server error",
                        content = @Content(examples = @ExampleObject(INTERNAL_SERVER_ERROR_EXAMPLE_RESPONSE)))
        })
public interface Api {}
