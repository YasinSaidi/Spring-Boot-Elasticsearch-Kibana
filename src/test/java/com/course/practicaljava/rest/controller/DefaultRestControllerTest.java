package com.course.practicaljava.rest.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.time.LocalTime;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DefaultRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    Logger log = LoggerFactory.getLogger(DefaultRestControllerTest.class);

    @Test
    void welcome() {
        String endpoint = "/api/welcome";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint);

        try {

            mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(content().string(Matchers
                            .equalToIgnoringCase("welcome to spring boot")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void time() {
        String endpoint = "/api/time";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint);

        //It returns the result of executed request for direct access to the results.
        MvcResult mockResult = null;
        try {
            mockResult = mockMvc.perform(requestBuilder).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Return the resulting response.
        try {
            String content = mockResult.getResponse().getContentAsString();
            log.info("content = {}", content);

            LocalTime contentLocalTime = LocalTime.parse(content);
            LocalTime currentLocalTime = LocalTime.now().minusSeconds(40);

            assertTrue(contentLocalTime.isAfter(currentLocalTime)
                    && currentLocalTime.isBefore(contentLocalTime));


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}