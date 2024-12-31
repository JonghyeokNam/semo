package com.semoi.semo.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semoi.semo.campus.enums.CampusName;
import com.semoi.semo.user.dto.request.UserRequestDto;
import com.semoi.semo.applyForm.entity.Position;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("유저 컨트롤러 테스트")
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("유저 생성")
    void createUser() throws Exception {
        // given
        UserRequestDto userRequestDto = new UserRequestDto(
                "홍길동",
                "hong@gmail.com",
                "hong@gmail.com",
                Position.BACKEND.getPosition(),
                CampusName.DONGDAEMUN.getName()
        );

        String requestBody = objectMapper.writeValueAsString(userRequestDto);

        // when
        ResultActions perform = mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));


        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.username").value("홍길동"))
                .andDo(print());
    }
}