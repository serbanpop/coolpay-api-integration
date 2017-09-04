package com.currencycloud.rest;

import okhttp3.ResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit2.Response;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static retrofit2.Response.error;
import static retrofit2.Response.success;

@RunWith(MockitoJUnitRunner.class)
public class EntityExtractorTest {

    private static final int ANY_STATUS_CODE = 400;
    private static final String NO_BODY = null;

    private static final String ANY_RESPONSE_BODY = "abc";
    private static final String EXTRACTED_ENTITY = "qed";

    private EntityExtractor testInstance = new EntityExtractor();

    private Response<String> response;

    @Mock
    private Function<String, String> mapper;

    @Test(expected = RuntimeException.class)
    public void entityFromThrowsRuntimeExceptionWhenResponseIsNotSuccessful() {
        response = error(ANY_STATUS_CODE, mock(ResponseBody.class));

        testInstance.entityFrom(response, mapper);
    }

    @Test(expected = IllegalStateException.class)
    public void entityFromThrowsExceptionWhenResponseIsSuccessfulAndBodyIsNull() {
        response = success(NO_BODY);

        testInstance.entityFrom(response, mapper);
    }

    @Test
    public void entityFromReturnsExtractedEntityWhenResponseIsSuccessfulAndContainsBody() throws Exception {
        response = success(ANY_RESPONSE_BODY);
        given(mapper.apply(anyString())).willReturn(EXTRACTED_ENTITY);

        final String result = testInstance.entityFrom(response, mapper);

        assertThat(result).isEqualTo(EXTRACTED_ENTITY);
    }
}